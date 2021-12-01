package com.example.llt_project_separate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EmotionsActivity extends AppCompatActivity {
    private RecyclerView emotionsRecyclerView;
    private EmotionsRecyclerViewAdapter emotionsAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotions);

        emotionsRecyclerView = findViewById(R.id.emotionsRecyclerView);
        emotionsAdapter = new EmotionsRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionsActivity.this, VideoSectionActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        emotionsRecyclerView.setAdapter(emotionsAdapter);
        emotionsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> emotions = new ArrayList<>();
        emotions.add(new Category(1, "VESEL", R.drawable.caine));
        emotions.add(new Category(2, "SĂTURAT", R.drawable.caine));
        emotions.add(new Category(3, "ÎNCRUNTAT", R.drawable.caine));
        emotions.add(new Category(4, "TRIST", R.drawable.caine));
        emotions.add(new Category(5, "DEZAMĂGIT", R.drawable.caine));
        emotions.add(new Category(6, "NERVOS", R.drawable.caine));
        emotions.add(new Category(7, "FERICIT", R.drawable.caine));
        emotions.add(new Category(8, "SUPĂRAT", R.drawable.caine));
        emotions.add(new Category(9, "ÎNDURERAT", R.drawable.caine));
        emotions.add(new Category(10, "ENTUZIASMAT", R.drawable.caine));
        emotions.add(new Category(11, "ÎNDRĂGOSTIT", R.drawable.caine));
        emotions.add(new Category(12, "LINIŞTIT", R.drawable.caine));
        emotions.add(new Category(13, "CUMINTE", R.drawable.caine));
        emotionsAdapter.setEmotions(emotions);
    }
}