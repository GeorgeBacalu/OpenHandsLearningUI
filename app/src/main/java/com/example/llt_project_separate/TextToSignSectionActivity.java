package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class TextToSignSectionActivity extends AppCompatActivity {
    /*
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton toHomePageButton;
    float v = 0;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_sign_section);
        /*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        toHomePageButton = findViewById(R.id.toHomePageButton);

        toHomePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextToSignSectionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText(""));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final TextToSignSectionAdapter adapter = new TextToSignSectionAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        toHomePageButton.setTranslationY(300);
        tabLayout.setTranslationY(300);

        toHomePageButton.setAlpha(v);
        tabLayout.setAlpha(v);

        toHomePageButton.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        */
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}