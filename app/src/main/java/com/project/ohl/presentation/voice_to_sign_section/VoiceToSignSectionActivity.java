package com.project.ohl.presentation.voice_to_sign_section;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import com.project.ohl.presentation.AuthenticatedActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.ohl.R;
import com.project.ohl.databinding.ActivityVoiceToSignSectionBinding;
import com.project.ohl.domain.model.SignText;
import com.project.ohl.presentation.MediaResources;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.R.id.home;

public class VoiceToSignSectionActivity extends AuthenticatedActivity {

    private ActivityVoiceToSignSectionBinding binding;
    private ActivityResultLauncher<Intent> speechRecognitionLauncher;
    private ActivityResultLauncher<String> microphonePermissionLauncher;

    private RecyclerView voiceToSignRecyclerView;
    private VoiceToSignSectionRecyclerViewAdapter voiceToSignSectionRecyclerViewAdapter;
    private Button voiceRecordingButton;
    private TextView categoryNameCardText;
    private RelativeLayout outputContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speechRecognitionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent resultData = result.getData();
                    if (result.getResultCode() == RESULT_OK && resultData != null) {
                        ArrayList<String> recognizedText = resultData.getStringArrayListExtra(
                                RecognizerIntent.EXTRA_RESULTS);
                        if (hasMicrophonePermission() && recognizedText != null && !recognizedText.isEmpty()) {
                            showTranslation(recognizedText.get(0));
                        }
                    }
                });
        microphonePermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> {
                    if (granted) startSpeechRecognition();
                    else clearTranslation();
                });
        binding = ActivityVoiceToSignSectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        voiceRecordingButton.setOnClickListener(v -> {
            if (!isMicrophonePresent()) return;
            if (hasMicrophonePermission()) {
                startSpeechRecognition();
            } else {
                microphonePermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
            }
        });

        voiceToSignRecyclerView.setAdapter(voiceToSignSectionRecyclerViewAdapter);
        var grid = new GridLayoutManager(this, 1);
        grid.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                return voiceToSignSectionRecyclerViewAdapter.getItemViewType(position)
                        == VoiceToSignSectionRecyclerViewAdapter.WORD_BREAK ? grid.getSpanCount() : 1;
            }
        });
        voiceToSignRecyclerView.setLayoutManager(grid);
        voiceToSignRecyclerView.addOnLayoutChangeListener((view, left, top, right, bottom,
                oldLeft, oldTop, oldRight, oldBottom) -> {
            int width = right - left - view.getPaddingLeft() - view.getPaddingRight();
            float minWidth = getResources().getDimension(R.dimen.sign_tile_min_width)
                    * Math.max(1f, getResources().getConfiguration().fontScale);
            int columns = Math.max(1, (int) (width / minWidth));
            if (grid.getSpanCount() != columns) grid.setSpanCount(columns);
        });

        clearTranslation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!hasMicrophonePermission()) clearTranslation();
    }

    private boolean hasMicrophonePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void showTranslation(String transcript) {
        if (!hasMicrophonePermission() || transcript == null || transcript.isBlank()) {
            clearTranslation();
            return;
        }
        var signs = SignText.translate(transcript);
        MediaResources.bindSigns(this, signs);
        voiceToSignSectionRecyclerViewAdapter.setTextSignPairs(signs);
        categoryNameCardText.setText(transcript);
        outputContent.setVisibility(signs.isEmpty() ? View.GONE : View.VISIBLE);
        if (!signs.isEmpty()) scrollToTranslation();
    }

    private void scrollToTranslation() {
        outputContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                outputContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (binding == null || outputContent.getVisibility() != View.VISIBLE) return;
                var scroll = binding.getRoot();
                int[] resultPosition = new int[2];
                int[] scrollPosition = new int[2];
                outputContent.getLocationOnScreen(resultPosition);
                scroll.getLocationOnScreen(scrollPosition);
                int inset = (int) (16 * getResources().getDisplayMetrics().density);
                scroll.smoothScrollTo(0, scroll.getScrollY() + resultPosition[1] - scrollPosition[1] - inset);
            }
        });
    }

    private void clearTranslation() {
        categoryNameCardText.setText("");
        outputContent.setVisibility(View.GONE);
        voiceToSignSectionRecyclerViewAdapter.setTextSignPairs(List.of());
    }

    private void initializeViews() {
        voiceToSignRecyclerView = findViewById(R.id.voiceToSignRecyclerView);
        voiceToSignSectionRecyclerViewAdapter = new VoiceToSignSectionRecyclerViewAdapter(this);
        voiceRecordingButton = findViewById(R.id.voiceRecordingButton);
        categoryNameCardText = findViewById(R.id.categoryNameCardText);
        outputContent = findViewById(R.id.outputContent);
    }

    private boolean isMicrophonePresent() {
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            return true;
        }
        return false;
    }

    private void startSpeechRecognition() {
        clearTranslation();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ro-RO");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        speechRecognitionLauncher.launch(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == home) getOnBackPressedDispatcher().onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
