package com.example.llt_project_separate.voice_to_sign_section;

import android.widget.Toast;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llt_project_separate.MainActivity;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.databinding.ActivityVoiceToSignSectionBinding;
import com.example.llt_project_separate.general.standard_classes.TextSignPair;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VoiceToSignSectionActivity extends AppCompatActivity {

    private ActivityVoiceToSignSectionBinding binding;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;

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

    // @BindView(R.id.tabs) PagerSlidingTabStrip tabs;
    // @BindView(R.id.pager) ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 100);
            } else {
                Toast.makeText(this, "Speech recognition is not supported on this device", Toast.LENGTH_SHORT).show();
            }

            startActivityForResult(intent, 100);
            /*
            try {
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setOutputFile(getRecordingFilePath());
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.prepare();
                mediaRecorder.start();
                Toast.makeText(this, "Se inregistreaza...", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            */
        });

        /*
        voiceStopRecordingButton.setOnClickListener(v -> {
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                Toast.makeText(this, "Inregistrarea a fost intrerupta", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        voiceReplayRecordingButton.setOnClickListener(v -> {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(getRecordingFilePath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                Toast.makeText(this, "Se reda inregistrarea", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
         */

        voiceToSignRecyclerView.setAdapter(voiceToSignSectionRecyclerViewAdapter);
        voiceToSignRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<TextSignPair> textSignPairs = new ArrayList<>();
        textSignPairs.add(new TextSignPair(getStringResource(R.string.a), R.drawable.dactilema_a));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.ă), R.drawable.dactilema_a));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.â), R.drawable.dactilema_a));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.b), R.drawable.dactilema_b));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.c), R.drawable.dactilema_c));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.d), R.drawable.dactilema_d));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.e), R.drawable.dactilema_e));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.f), R.drawable.dactilema_f));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.g), R.drawable.dactilema_g));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.h), R.drawable.dactilema_h));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.i), R.drawable.dactilema_i));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.î), R.drawable.dactilema_i));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.j), R.drawable.dactilema_j));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.k), R.drawable.dactilema_k));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.l), R.drawable.dactilema_l));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.m), R.drawable.dactilema_m));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.n), R.drawable.dactilema_n));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.o), R.drawable.dactilema_o));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.p), R.drawable.dactilema_p));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.q), R.drawable.dactilema_q));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.r), R.drawable.dactilema_r));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.s), R.drawable.dactilema_s));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.ş), R.drawable.dactilema_s));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.t), R.drawable.dactilema_t));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.ţ), R.drawable.dactilema_t));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.u), R.drawable.dactilema_u));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.v), R.drawable.dactilema_v));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.w), R.drawable.dactilema_w));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.x), R.drawable.dactilema_x));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.y), R.drawable.dactilema_y));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.z), R.drawable.dactilema_z));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.A), R.drawable.dactilema_a));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.A), R.drawable.dactilema_a));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.Ă), R.drawable.dactilema_a));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.Â), R.drawable.dactilema_a));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.B), R.drawable.dactilema_b));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.C), R.drawable.dactilema_c));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.D), R.drawable.dactilema_d));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.E), R.drawable.dactilema_e));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.F), R.drawable.dactilema_f));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.G), R.drawable.dactilema_g));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.H), R.drawable.dactilema_h));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.I), R.drawable.dactilema_i));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.Î), R.drawable.dactilema_i));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.J), R.drawable.dactilema_j));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.K), R.drawable.dactilema_k));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.L), R.drawable.dactilema_l));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.M), R.drawable.dactilema_m));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.N), R.drawable.dactilema_n));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.O), R.drawable.dactilema_o));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.P), R.drawable.dactilema_p));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.Q), R.drawable.dactilema_q));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.R), R.drawable.dactilema_r));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.S), R.drawable.dactilema_s));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.Ş), R.drawable.dactilema_s));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.T), R.drawable.dactilema_t));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.Ţ), R.drawable.dactilema_t));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.U), R.drawable.dactilema_u));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.V), R.drawable.dactilema_v));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.W), R.drawable.dactilema_w));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.X), R.drawable.dactilema_x));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.Y), R.drawable.dactilema_y));
        textSignPairs.add(new TextSignPair(getStringResource(R.string.Z), R.drawable.dactilema_z));
        textSignPairs.add(new TextSignPair(" ", R.drawable.space));
        // voiceToSignSectionRecyclerViewAdapter.setTextSignPairs(textSignPairs);

        outputContent.setVisibility(View.GONE);
        categoryNameCardText.setText("");

        confirmButton.setOnClickListener(v -> {
            outputContent.setVisibility(View.VISIBLE);
            String inputTextUnformatted = textInput.getText().toString();
            String inputText = inputTextUnformatted.substring(0, 1).toUpperCase() + inputTextUnformatted.substring(1).toLowerCase();
            if (inputText.equals("")) {
                throw new RuntimeException("Introdu un text");
            }
            char[] inputTextCharArray = inputText.toCharArray();
            List<TextSignPair> textSignPairsList = new ArrayList<>();
            for (char value : inputTextCharArray) {
                for (TextSignPair textSignPair : textSignPairs) {
                    if (textSignPair.getLetter().charAt(0) == value) {
                        textSignPairsList.add(new TextSignPair(textSignPair.getLetter(), textSignPair.getImageSource()));
                        break;
                    }
                }
            }
            categoryNameCardText.setText(inputText.substring(0, 1).toUpperCase() + inputText.substring(1).toLowerCase());
            textInput.setText("");
            voiceToSignSectionRecyclerViewAdapter.setTextSignPairs(textSignPairsList);
            voiceToSignSectionRecyclerViewAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        voiceToSignRecyclerView = findViewById(R.id.voiceToSignRecyclerView);
        voiceToSignSectionRecyclerViewAdapter = new VoiceToSignSectionRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        textInput = findViewById(R.id.textInput);
        confirmButton = findViewById(R.id.confirmButton);
        voiceRecordingButton = findViewById(R.id.voiceRecordingButton);
        // voiceStopRecordingButton = findViewById(R.id.voiceStopRecordingButton);
        // voiceReplayRecordingButton = findViewById(R.id.voiceReplayRecordingButton);
        categoryNameCardText = findViewById(R.id.categoryNameCardText);
        outputContent = findViewById(R.id.outputContent);
    }

    String getStringResource(int intResource) {
        return getResources().getString(intResource);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            textInput.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
        }
    }

    private boolean isMicrophonePresent() {
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            return true;
        }
        return false;
    }

    private void getMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MICROPHONE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(this, "Microphone permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(this, "Microphone permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
    private String getRecordingFilePath() {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory, "textRecordingFile" + ".mp3");
        return file.getPath();
    }
     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null; // Prevent memory leaks
    }
}