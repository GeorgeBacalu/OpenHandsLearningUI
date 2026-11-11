package com.project.ohl.presentation.general.new_category;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.project.ohl.R;
import com.project.ohl.presentation.MainActivity;
import java.util.Objects;

import static android.R.id.home;

public class NewCategoryActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
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

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final NewCategoryFormAdapter adapter = new NewCategoryFormAdapter(this, 2);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(position == 0 ? "Subcategorie" : "Categorie")).attach();

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
        if (item.getItemId() == home) getOnBackPressedDispatcher().onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
