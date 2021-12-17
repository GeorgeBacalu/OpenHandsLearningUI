package com.example.llt_project_separate.video_section.alphabet;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.MainActivity;
import com.example.llt_project_separate.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AlphabetActivity extends AppCompatActivity {
    private RecyclerView alphabetRecyclerView;
    private AlphabetRecyclerViewAdapter alphabetAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(AlphabetActivity.this, MainActivity.class);
            startActivity(intent);
        });

        alphabetRecyclerView.setAdapter(alphabetAdapter);
        alphabetRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> letters = new ArrayList<>();
        letters.add(new Category(1, getStringResource(R.string.A), R.drawable.dactilema_a));
        // letters.add(new Category(2, getStringResource(R.string.A), R.drawable.dactilema_a));
        // letters.add(new Category(3, getStringResource(R.string.A), R.drawable.dactilema_a));
        letters.add(new Category(4, getStringResource(R.string.B), R.drawable.dactilema_b));
        letters.add(new Category(5, getStringResource(R.string.C), R.drawable.dactilema_c));
        letters.add(new Category(6, getStringResource(R.string.D), R.drawable.dactilema_d));
        letters.add(new Category(7, getStringResource(R.string.E), R.drawable.dactilema_e));
        letters.add(new Category(8, getStringResource(R.string.F), R.drawable.dactilema_f));
        letters.add(new Category(9, getStringResource(R.string.G), R.drawable.dactilema_g));
        letters.add(new Category(10, getStringResource(R.string.H), R.drawable.dactilema_h));
        letters.add(new Category(11, getStringResource(R.string.I), R.drawable.dactilema_i));
        // letters.add(new Category(12, getStringResource(R.string.I), R.drawable.dactilema_i));
        letters.add(new Category(13, getStringResource(R.string.J), R.drawable.dactilema_j));
        letters.add(new Category(14, getStringResource(R.string.K), R.drawable.dactilema_k));
        letters.add(new Category(15, getStringResource(R.string.L), R.drawable.dactilema_l));
        letters.add(new Category(16, getStringResource(R.string.M), R.drawable.dactilema_m));
        letters.add(new Category(17, getStringResource(R.string.N), R.drawable.dactilema_n));
        letters.add(new Category(18, getStringResource(R.string.O), R.drawable.dactilema_o));
        letters.add(new Category(19, getStringResource(R.string.P), R.drawable.dactilema_p));
        letters.add(new Category(20, getStringResource(R.string.Q), R.drawable.dactilema_q));
        letters.add(new Category(21, getStringResource(R.string.R), R.drawable.dactilema_r));
        letters.add(new Category(22, getStringResource(R.string.S), R.drawable.dactilema_s));
        // letters.add(new Category(23, getStringResource(R.string.S), R.drawable.dactilema_s));
        letters.add(new Category(24, getStringResource(R.string.T), R.drawable.dactilema_t));
        // letters.add(new Category(25, getStringResource(R.string.T), R.drawable.dactilema_t));
        letters.add(new Category(26, getStringResource(R.string.U), R.drawable.dactilema_u));
        letters.add(new Category(27, getStringResource(R.string.V), R.drawable.dactilema_v));
        letters.add(new Category(28, getStringResource(R.string.W), R.drawable.dactilema_w));
        letters.add(new Category(29, getStringResource(R.string.X), R.drawable.dactilema_x));
        letters.add(new Category(30, getStringResource(R.string.Y), R.drawable.dactilema_y));
        letters.add(new Category(31, getStringResource(R.string.Z), R.drawable.dactilema_z));
        alphabetAdapter.setLetters(letters);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredLetters = letters.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            alphabetAdapter.setLetters(filteredLetters);
            alphabetAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        alphabetRecyclerView = findViewById(R.id.alphabetRecyclerView);
        alphabetAdapter = new AlphabetRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        searchBarInput = findViewById(R.id.searchBarInput);
        searchBarIcon = findViewById(R.id.searchBarIcon);
    }

    String getStringResource(int intResource) { return getResources().getString(intResource); }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}