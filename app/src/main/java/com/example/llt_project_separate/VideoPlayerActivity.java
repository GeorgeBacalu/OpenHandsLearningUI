package com.example.llt_project_separate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.Locale;

public class VideoPlayerActivity extends AppCompatActivity {
    private Intent intent;
    private RecyclerView videoSubjectRecyclerView;
    private VideoPlayerRecyclerViewAdapter videoSubjectAdapter;
    private ImageView toVideoSectionPageButton;
    private VideoView videoView;
    private String videoSubject, callingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoSubjectRecyclerView = findViewById(R.id.videoSubjectRecyclerView);
        videoSubjectAdapter = new VideoPlayerRecyclerViewAdapter(this);

        toVideoSectionPageButton = findViewById(R.id.toVideoSectionPageButton);

        callingIntent = getIntent().getExtras().get("intent").toString();

        toVideoSectionPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (callingIntent) {
                    case "AlphabetActivity": intent = new Intent(VideoPlayerActivity.this, AlphabetActivity.class); break;
                    case "NumbersActivity": intent = new Intent(VideoPlayerActivity.this, NumbersActivity.class); break;
                    case "ColorsActivity": intent = new Intent(VideoPlayerActivity.this, ColorsActivity.class); break;
                    case "HouseholdAnimalsActivity": intent = new Intent(VideoPlayerActivity.this, HouseholdAnimalsActivity.class); break;
                    case "WildAnimalsActivity": intent = new Intent(VideoPlayerActivity.this, WildAnimalsActivity.class); break;
                    case "HomeActivity": intent = new Intent(VideoPlayerActivity.this, HomeActivity.class); break;
                    case "OutsideActivity": intent = new Intent(VideoPlayerActivity.this, OutsideActivity.class); break;
                    case "ClassActivity": intent = new Intent(VideoPlayerActivity.this, ClassActivity.class); break;
                    case "MoneyActivity": intent = new Intent(VideoPlayerActivity.this, MoneyActivity.class); break;
                    case "ProductsActivity": intent = new Intent(VideoPlayerActivity.this, ProductsActivity.class); break;
                    case "CityActivity": intent = new Intent(VideoPlayerActivity.this, CityActivity.class); break;
                    case "FamilyMembersActivity": intent = new Intent(VideoPlayerActivity.this, FamilyMembersActivity.class); break;
                    case "PronounsActivity": intent = new Intent(VideoPlayerActivity.this, PronounsActivity.class); break;
                    case "EmotionsActivity": intent = new Intent(VideoPlayerActivity.this, EmotionsActivity.class); break;
                    case "VerbsActivity": intent = new Intent(VideoPlayerActivity.this, VerbsActivity.class); break;
                    default: throw new NullPointerException("Invalid Selection");
                }
                startActivity(intent);
            }
        });

        videoSubjectRecyclerView.setAdapter(videoSubjectAdapter);
        videoSubjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        videoView = findViewById(R.id.videoView);
        videoSubject = getIntent().getExtras().get("name").toString().toLowerCase().replace(" ", "_");

        String uriPath = "android.resource://" + getPackageName() + "/raw/" + videoSubject;
        String dynamicImageResource = "drawable/" + videoSubject;
        System.out.println(videoSubject);
        int imageKey = getResources().getIdentifier(dynamicImageResource, "drawable", getPackageName());
        videoView.setVideoPath(uriPath);

        Category selectedCategory = new Category(1, videoSubject.toUpperCase().replace("_", " "), imageKey);
        videoSubjectAdapter.setSelectedCategory(selectedCategory);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }
}