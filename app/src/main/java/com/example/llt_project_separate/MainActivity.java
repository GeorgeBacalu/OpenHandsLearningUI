package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.llt_project_separate.expressions_section.ExpressionsSectionActivity;
import com.example.llt_project_separate.general.standard_classes.Section;
import com.example.llt_project_separate.text_to_sign_section.TextToSignSectionActivity;
import com.example.llt_project_separate.video_section.VideoSectionActivity;
import com.example.llt_project_separate.voice_to_sign_section.VoiceToSignSectionActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView sectionsRecyclerView;
    private MainRecyclerViewAdapter sectionsAdapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;

    //private MenuItem navHome, navVideo, navTextToSign, navVoiceToSign, navExpressions, navSettings;

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
        //NavigationUI.setupWithNavController();


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initializeViews() {
        sectionsRecyclerView = findViewById(R.id.sectionsRecyclerView);
        sectionsAdapter = new MainRecyclerViewAdapter(this);
        /*
        navHome = findViewById(R.id.navHome);
        navVideo = findViewById(R.id.navVideo);
        navTextToSign = findViewById(R.id.navTextToSign);
        navVoiceToSign = findViewById(R.id.navVoiceToSign);
        navExpressions = findViewById(R.id.navExpressions);
        navSettings = findViewById(R.id.navSettings)*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.navSettingsActionBar) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.navHome: intent = new Intent(MainActivity.this, MainActivity.class); break;
            case R.id.navVideo: intent = new Intent(MainActivity.this, VideoSectionActivity.class); break;
            case R.id.navTextToSign: intent = new Intent(MainActivity.this, TextToSignSectionActivity.class); break;
            case R.id.navVoiceToSign: intent = new Intent(MainActivity.this, VoiceToSignSectionActivity.class); break;
            case R.id.navExpressions: intent = new Intent(MainActivity.this, ExpressionsSectionActivity.class); break;
            case R.id.navSettings: intent = new Intent(MainActivity.this, SettingsActivity.class); break;
            default: throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        startActivity(intent);
        return true;
    }

    String getStringResource(int intResource) { return getResources().getString(intResource); }
}