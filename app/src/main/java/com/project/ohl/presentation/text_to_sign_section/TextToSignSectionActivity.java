package com.project.ohl.presentation.text_to_sign_section;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.ohl.R;
import com.project.ohl.domain.model.SignText;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.presentation.MediaResources;
import java.util.Objects;

import static android.R.id.home;

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

        outputContent.setVisibility(View.GONE);
        categoryNameCardText.setText("");
        confirmButton.setOnClickListener(view -> {
            String input = textInput.getText().toString();
            var signs = SignText.translate(input);
            MediaResources.bindSigns(this, signs);
            textToSignSectionRecyclerViewAdapter.setTextSignPairs(signs);
            categoryNameCardText.setText(input);
            outputContent.setVisibility(signs.isEmpty() ? View.GONE : View.VISIBLE);
            textInput.setText("");
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
        if (item.getItemId() == home) getOnBackPressedDispatcher().onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}