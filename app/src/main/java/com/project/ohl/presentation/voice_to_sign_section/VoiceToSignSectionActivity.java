package com.project.ohl.presentation.voice_to_sign_section;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.ohl.R;
import com.project.ohl.databinding.ActivityVoiceToSignSectionBinding;
import com.project.ohl.domain.model.SignText;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.presentation.MediaResources;
import java.util.ArrayList;
import java.util.Objects;

import static android.R.id.home;

public class VoiceToSignSectionActivity extends AppCompatActivity {

    private ActivityVoiceToSignSectionBinding binding;
    private ActivityResultLauncher<Intent> speechRecognitionLauncher;

    private static int MICROPHONE_PERMISSION_CODE = 200;

    private RecyclerView voiceToSignRecyclerView;
    private VoiceToSignSectionRecyclerViewAdapter voiceToSignSectionRecyclerViewAdapter;
    private FloatingActionButton toHomePageFabButton;
    private FloatingActionButton voiceRecordingButton;
    private FloatingActionButton voiceStopRecordingButton, voiceReplayRecordingButton;
    private EditText textInput;
    private Button confirmButton;
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
                        if (recognizedText != null && !recognizedText.isEmpty()) {
                            textInput.setText(recognizedText.get(0));
                        }
                    }
                });
        binding = ActivityVoiceToSignSectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        if (isMicrophonePresent()) {
            getMicrophonePermission();
        }

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(VoiceToSignSectionActivity.this, MainActivity.class);
            startActivity(intent);
        });

        voiceRecordingButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ro-RO");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
            speechRecognitionLauncher.launch(intent);
        });

        voiceToSignRecyclerView.setAdapter(voiceToSignSectionRecyclerViewAdapter);
        voiceToSignRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        outputContent.setVisibility(View.GONE);
        categoryNameCardText.setText("");
        confirmButton.setOnClickListener(view -> {
            String input = textInput.getText().toString();
            var signs = SignText.translate(input);
            MediaResources.bindSigns(this, signs);
            voiceToSignSectionRecyclerViewAdapter.setTextSignPairs(signs);
            categoryNameCardText.setText(input);
            outputContent.setVisibility(signs.isEmpty() ? View.GONE : View.VISIBLE);
            textInput.setText("");
        });
    }

    private void initializeViews() {
        voiceToSignRecyclerView = findViewById(R.id.voiceToSignRecyclerView);
        voiceToSignSectionRecyclerViewAdapter = new VoiceToSignSectionRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        textInput = findViewById(R.id.textInput);
        confirmButton = findViewById(R.id.confirmButton);
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

    private void getMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION_CODE);
        }
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
