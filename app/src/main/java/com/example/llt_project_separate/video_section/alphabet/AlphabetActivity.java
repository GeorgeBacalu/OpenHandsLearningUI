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
        letters.add(new Category(1, getStringResource(R.string.A), R.drawable.a));
        // letters.add(new Category(2, getStringResource(R.string.A), R.drawable.a));
        // letters.add(new Category(3, getStringResource(R.string.A), R.drawable.a));
        letters.add(new Category(4, getStringResource(R.string.B), R.drawable.b));
        letters.add(new Category(5, getStringResource(R.string.C), R.drawable.c));
        letters.add(new Category(6, getStringResource(R.string.D), R.drawable.d));
        letters.add(new Category(7, getStringResource(R.string.E), R.drawable.e));
        letters.add(new Category(8, getStringResource(R.string.F), R.drawable.f));
        letters.add(new Category(9, getStringResource(R.string.G), R.drawable.g));
        letters.add(new Category(10, getStringResource(R.string.H), R.drawable.h));
        letters.add(new Category(11, getStringResource(R.string.I), R.drawable.i));
        // letters.add(new Category(12, getStringResource(R.string.I), R.drawable.i));
        letters.add(new Category(13, getStringResource(R.string.J), R.drawable.j));
        letters.add(new Category(14, getStringResource(R.string.K), R.drawable.k));
        letters.add(new Category(15, getStringResource(R.string.L), R.drawable.l));
        letters.add(new Category(16, getStringResource(R.string.M), R.drawable.m));
        letters.add(new Category(17, getStringResource(R.string.N), R.drawable.n));
        letters.add(new Category(18, getStringResource(R.string.O), R.drawable.o));
        letters.add(new Category(19, getStringResource(R.string.P), R.drawable.p));
        letters.add(new Category(20, getStringResource(R.string.Q), R.drawable.q));
        letters.add(new Category(21, getStringResource(R.string.R), R.drawable.r));
        letters.add(new Category(22, getStringResource(R.string.S), R.drawable.s));
        // letters.add(new Category(23, getStringResource(R.string.S), R.drawable.s));
        letters.add(new Category(24, getStringResource(R.string.T), R.drawable.t));
        // letters.add(new Category(25, getStringResource(R.string.T), R.drawable.t));
        letters.add(new Category(26, getStringResource(R.string.U), R.drawable.u));
        letters.add(new Category(27, getStringResource(R.string.V), R.drawable.v));
        letters.add(new Category(28, getStringResource(R.string.W), R.drawable.w));
        letters.add(new Category(29, getStringResource(R.string.X), R.drawable.x));
        letters.add(new Category(30, getStringResource(R.string.Y), R.drawable.y));
        letters.add(new Category(31, getStringResource(R.string.Z), R.drawable.z));
        alphabetAdapter.setLetters(letters);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredLetters = letters.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            searchBarInput.setText("");
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