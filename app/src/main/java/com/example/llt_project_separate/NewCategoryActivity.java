package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class NewCategoryActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton toHomePageButton;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageButton.setOnClickListener(v -> {
            Intent intent = new Intent(NewCategoryActivity.this, MainActivity.class);
            startActivity(intent);
        });

        tabLayout.addTab(tabLayout.newTab().setText("Categorie"));
        tabLayout.addTab(tabLayout.newTab().setText("Subcategorie"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final NewCategoryFormAdapter adapter = new NewCategoryFormAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        toHomePageButton.setTranslationY(300);
        tabLayout.setTranslationY(300);

        toHomePageButton.setAlpha(v);
        tabLayout.setAlpha(v);

        toHomePageButton.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }

    private void initializeViews() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        toHomePageButton = findViewById(R.id.toHomePageButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}