package com.example.llt_project_separate.video_section.verbs;

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

public class VerbsActivity extends AppCompatActivity {
    private RecyclerView verbsRecyclerView;
    private VerbsRecyclerViewAdapter verbsAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbs);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(VerbsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        verbsRecyclerView.setAdapter(verbsAdapter);
        verbsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> verbs = new ArrayList<>();
        verbs.add(new Category(164, getStringResource(R.string.CONSTRUI), R.drawable.construi));
        verbs.add(new Category(165, getStringResource(R.string.SCRIE), R.drawable.scrie));
        verbs.add(new Category(166, getStringResource(R.string.TRAGE), R.drawable.trage));
        verbs.add(new Category(167, getStringResource(R.string.CITI), R.drawable.citi));
        verbs.add(new Category(168, getStringResource(R.string.STA_JOS), R.drawable.sta_jos));
        // verbs.add(new Category(169, getStringResource(R.string.LASA), R.drawable.lasa));
        verbs.add(new Category(170, getStringResource(R.string.APASA), R.drawable.apasa));
        // verbs.add(new Category(171, getStringResource(R.string.PUNE), R.drawable.pune));
        // verbs.add(new Category(172, getStringResource(R.string.STRANGE), R.drawable.strange));
        // verbs.add(new Category(173, getStringResource(R.string.SCOATE), R.drawable.scoate));
        verbs.add(new Category(174, getStringResource(R.string.SPUNE), R.drawable.spune));
        verbs.add(new Category(175, getStringResource(R.string.SE_UITA), R.drawable.se_uita));
        verbsAdapter.setVerbs(verbs);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredVerbs = verbs.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            searchBarInput.setText("");
            verbsAdapter.setVerbs(filteredVerbs);
            verbsAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        verbsRecyclerView = findViewById(R.id.verbsRecyclerView);
        verbsAdapter = new VerbsRecyclerViewAdapter(this);
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