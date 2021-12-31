package com.example.llt_project_separate.expressions_section;

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
import android.widget.TextView;

import com.example.llt_project_separate.MainActivity;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.general.standard_classes.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpressionsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExpressionsRecyclerViewAdapter adapter;
    private FloatingActionButton homePageButton;
    private EditText searchBar;
    private ImageView searchIcon;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expressions_section);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initializeViews();

        homePageButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExpressionsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> expressions = new ArrayList<>();
        expressions.add(new Category(191, getStringResource(R.string.aceasta_este_prietena_mea), R.drawable.aceasta_este_prietena_mea));
        expressions.add(new Category(192, getStringResource(R.string.acesta_este_colegul_meu), R.drawable.acesta_este_colegul_meu));
        expressions.add(new Category(193, getStringResource(R.string.acesta_este_sotul_meu), R.drawable.acesta_este_sotul_meu));
        expressions.add(new Category(194, getStringResource(R.string.am_inteles), R.drawable.am_inteles));
        expressions.add(new Category(195, getStringResource(R.string.am_sa_va_fac_cunostinta), R.drawable.am_sa_va_fac_cunostinta));
        expressions.add(new Category(196, getStringResource(R.string.as_dori_sa_iti_cer_sfatul), R.drawable.as_dori_sa_iti_cer_sfatul));
        adapter.setExpressions(expressions);

        searchIcon.setOnClickListener(v -> {
            String searchBarText = searchBar.getText().toString().toLowerCase();
            List<Category> filteredExpressions = expressions.stream().filter(expression-> expression.getName().toLowerCase().startsWith(searchBarText)).collect(Collectors.toList());
            searchBar.setText("");
            adapter.setExpressions(filteredExpressions);
            adapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new ExpressionsRecyclerViewAdapter(this);
        homePageButton = findViewById(R.id.home_page_button);
        searchBar = findViewById(R.id.search_bar);
        searchIcon = findViewById(R.id.search_icon);
    }

    String getStringResource(int intResource) { return getResources().getString(intResource); }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}