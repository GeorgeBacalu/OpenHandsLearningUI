package com.example.llt_project_separate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView sectionsRecyclerView;
    private SectionsRecyclerViewAdapter sectionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionsRecyclerView = findViewById(R.id.sectionsRecyclerView);
        sectionsAdapter = new SectionsRecyclerViewAdapter(this);

        sectionsRecyclerView.setAdapter(sectionsAdapter);
        sectionsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Section> sections = new ArrayList<>();
        sections.add(new Section(1, "Secţiune Video", R.drawable.ic_video_section, R.drawable.video_section));
        sections.add(new Section(2, "Secţiune Text to Sign", R.drawable.ic_text_to_sign_section, R.drawable.text_to_sign_section));
        sections.add(new Section(3, "Secţiune Voice to Sign", R.drawable.ic_voice_to_sign_section, R.drawable.voice_to_sign_section));
        sections.add(new Section(4, "Secţiune Expresii", R.drawable.ic_expressions_section, R.drawable.expressions_section));
        sectionsAdapter.setSections(sections);
    }
}