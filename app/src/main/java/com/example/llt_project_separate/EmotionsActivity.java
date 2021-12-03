package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EmotionsActivity extends AppCompatActivity {
    private RecyclerView emotionsRecyclerView;
    private EmotionsRecyclerViewAdapter emotionsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotions);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emotionsRecyclerView = findViewById(R.id.emotionsRecyclerView);
        emotionsAdapter = new EmotionsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

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
        emotions.add(new Category(151, "VESEL", R.drawable.caine));
        emotions.add(new Category(152, "SĂTURAT", R.drawable.caine));
        emotions.add(new Category(153, "ÎNCRUNTAT", R.drawable.caine));
        emotions.add(new Category(154, "TRIST", R.drawable.caine));
        emotions.add(new Category(155, "DEZAMĂGIT", R.drawable.caine));
        emotions.add(new Category(156, "NERVOS", R.drawable.caine));
        emotions.add(new Category(157, "FERICIT", R.drawable.caine));
        emotions.add(new Category(158, "SUPĂRAT", R.drawable.caine));
        emotions.add(new Category(159, "ÎNDURERAT", R.drawable.caine));
        emotions.add(new Category(160, "ENTUZIASMAT", R.drawable.caine));
        emotions.add(new Category(161, "ÎNDRĂGOSTIT", R.drawable.caine));
        emotions.add(new Category(162, "LINIŞTIT", R.drawable.caine));
        emotions.add(new Category(163, "CUMINTE", R.drawable.caine));
        emotionsAdapter.setEmotions(emotions);
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