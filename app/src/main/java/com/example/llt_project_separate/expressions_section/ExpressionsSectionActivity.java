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

import com.example.llt_project_separate.MainActivity;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.general.standard_classes.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExpressionsSectionActivity extends AppCompatActivity {
    private RecyclerView expressionsSectionRecyclerView;
    private ExpressionsSectionRecyclerViewAdapter expressionsSectionRecyclerViewAdapter;
    private FloatingActionButton toHomePageButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expressions_section);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExpressionsSectionActivity.this, MainActivity.class);
            startActivity(intent);
        });

        expressionsSectionRecyclerView.setAdapter(expressionsSectionRecyclerViewAdapter);
        expressionsSectionRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> expressions = new ArrayList<>();
        expressions.add(new Category(191, getStringResource(R.string.aceasta_este_prietena_mea), R.drawable.aceasta_este_prietena_mea));
        expressions.add(new Category(192, getStringResource(R.string.acesta_este_colegul_meu), R.drawable.acesta_este_colegul_meu));
        expressions.add(new Category(193, getStringResource(R.string.acesta_este_sotul_meu), R.drawable.acesta_este_sotul_meu));
        expressions.add(new Category(194, getStringResource(R.string.am_inteles), R.drawable.am_inteles));
        expressions.add(new Category(195, getStringResource(R.string.am_sa_va_fac_cunostinta), R.drawable.am_sa_va_fac_cunostinta));
        expressions.add(new Category(196, getStringResource(R.string.as_dori_sa_iti_cer_sfatul), R.drawable.as_dori_sa_iti_cer_sfatul));
        expressionsSectionRecyclerViewAdapter.setExpressions(expressions);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredExpressions = expressions.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            expressionsSectionRecyclerViewAdapter.setExpressions(filteredExpressions);
            expressionsSectionRecyclerViewAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        expressionsSectionRecyclerView = findViewById(R.id.expressionsSectionRecyclerView);
        expressionsSectionRecyclerViewAdapter = new ExpressionsSectionRecyclerViewAdapter(this);
        toHomePageButton = findViewById(R.id.toHomePageButton);
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