package com.example.llt_project_separate;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.llt_project_separate.general.standard_classes.Section;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView sectionsRecyclerView;
    private MainRecyclerViewAdapter sectionsAdapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        sectionsRecyclerView.setAdapter(sectionsAdapter);
        sectionsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Section> sections = new ArrayList<>();
        sections.add(new Section(10001, getStringResource(R.string.video_section), R.drawable.ic_video_section, R.drawable.video_section));
        sections.add(new Section(10002, getStringResource(R.string.text_to_sign_section), R.drawable.ic_text_to_sign_section, R.drawable.text_to_sign_section));
        sections.add(new Section(10003, getStringResource(R.string.voice_to_sign_section), R.drawable.ic_voice_to_sign_section, R.drawable.voice_to_sign_section));
        sections.add(new Section(10004, getStringResource(R.string.expressions_section), R.drawable.ic_expressions_section, R.drawable.expressions_section));
        sectionsAdapter.setSections(sections);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initializeViews() {
        sectionsRecyclerView = findViewById(R.id.sectionsRecyclerView);
        sectionsAdapter = new MainRecyclerViewAdapter(this);
    }

    String getStringResource(int intResource) { return getResources().getString(intResource); }
}