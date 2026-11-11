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
import android.widget.Toast;
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
import com.project.ohl.domain.model.TextSignPair;
import com.project.ohl.presentation.LearningViewModel;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.presentation.MediaResources;
import java.util.ArrayList;
import java.util.List;
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
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");
            speechRecognitionLauncher.launch(intent);
        });

        voiceToSignRecyclerView.setAdapter(voiceToSignSectionRecyclerViewAdapter);
        voiceToSignRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        LearningViewModel.of(this).<List<TextSignPair>>load(this, "getAllTextSignPairs",
                repository -> repository.getAllTextSignPairs(), result -> {
            if (result.error() != null) {
                Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
                return;
            }
            List<TextSignPair> textSignPairs = result.value();
            MediaResources.bindSigns(this, textSignPairs);

            outputContent.setVisibility(View.GONE);
            categoryNameCardText.setText("");

            confirmButton.setOnClickListener(v -> {
                outputContent.setVisibility(View.VISIBLE);
                String inputText = textInput.getText().toString();
                if (inputText.isEmpty()) return;
                List<TextSignPair> textSignPairsList = SignText.translate(inputText, textSignPairs);
                categoryNameCardText.setText(inputText);
                textInput.setText("");
                voiceToSignSectionRecyclerViewAdapter.setTextSignPairs(textSignPairsList);
            });
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
