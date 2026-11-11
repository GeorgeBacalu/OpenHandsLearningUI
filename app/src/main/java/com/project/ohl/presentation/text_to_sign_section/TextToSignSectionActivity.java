package com.project.ohl.presentation.text_to_sign_section;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.ohl.presentation.MainActivity;
import com.project.ohl.R;
import com.project.ohl.domain.model.TextSignPair;
import com.project.ohl.presentation.LearningViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextToSignSectionActivity extends AppCompatActivity {
    private RecyclerView textToSignRecyclerView;
    private TextToSignSectionRecyclerViewAdapter textToSignSectionRecyclerViewAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText textInput;
    private Button confirmButton;
    private TextView categoryNameCardText;
    private RelativeLayout outputContent;

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_sign_section);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(TextToSignSectionActivity.this, MainActivity.class);
            startActivity(intent);
        });

        textToSignRecyclerView.setAdapter(textToSignSectionRecyclerViewAdapter);
        textToSignRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


       LearningViewModel.of(this).<List<TextSignPair>>load(this, "getAllTextSignPairs", repository -> repository.getAllTextSignPairs(), result -> {
            if (result.error() != null) {
             Toast.makeText(TextToSignSectionActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                List<TextSignPair> textSignPairs = result.value();
                for (TextSignPair textSignPair : textSignPairs) {
                   String letter = textSignPair.getLetter();
                   String name = letter == " " ? "space" : "dactilema_" + letter.replace(" ", "_").toLowerCase(java.util.Locale.ROOT);
                   int drawableId = getResources().getIdentifier(name, "drawable", getPackageName());
                   textSignPair.setImageSource(drawableId);
                }
                textToSignSectionRecyclerViewAdapter.setTextSignPairs(textSignPairs);

                outputContent.setVisibility(View.GONE);
                categoryNameCardText.setText("");

                confirmButton.setOnClickListener(v -> {
                   outputContent.setVisibility(View.VISIBLE);
                   String inputTextUnformatted = textInput.getText().toString();
                   String inputText = inputTextUnformatted.substring(0, 1).toUpperCase(java.util.Locale.ROOT) + inputTextUnformatted.substring(1).toLowerCase(java.util.Locale.ROOT);
                   if(inputText.equals("")) {
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
                   categoryNameCardText.setText(inputText.substring(0, 1).toUpperCase(java.util.Locale.ROOT) + inputText.substring(1).toLowerCase(java.util.Locale.ROOT));
                   textInput.setText("");
                   textToSignSectionRecyclerViewAdapter.setTextSignPairs(textSignPairsList);
                });

            }
        });
    }

    private void initializeViews() {
        textToSignRecyclerView = findViewById(R.id.textToSignRecyclerView);
        textToSignSectionRecyclerViewAdapter = new TextToSignSectionRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        textInput = findViewById(R.id.textInput);
        confirmButton = findViewById(R.id.confirmButton);
        categoryNameCardText = findViewById(R.id.categoryNameCardText);
        outputContent = findViewById(R.id.outputContent);
    }

    String getStringResource(int intResource) { return getResources().getString(intResource); }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) getOnBackPressedDispatcher().onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}