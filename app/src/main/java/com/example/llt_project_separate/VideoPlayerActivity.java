package com.example.llt_project_separate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.util.Locale;

public class VideoPlayerActivity extends AppCompatActivity {
    public static final String CATEGORY_ID_KEY = "id";

    private Intent intent;
    private TextView selectedCategoryName;
    private RecyclerView videoSubjectRecyclerView;
    private VideoPlayerRecyclerViewAdapter videoSubjectAdapter;
    private ImageView toVideoSectionPageButton, selectedCategoryImage;
    private VideoView videoView;
    private String videoSubject, videoSubjectFormatted, callingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        selectedCategoryName = findViewById(R.id.selectedCategoryName);
        selectedCategoryImage = findViewById(R.id.selectedCategoryImage);
        videoSubjectRecyclerView = findViewById(R.id.videoSubjectRecyclerView);
        videoSubjectAdapter = new VideoPlayerRecyclerViewAdapter(this);
        videoView = findViewById(R.id.videoView);

        intent = getIntent();
        if(intent != null) {
            int categoryId = intent.getIntExtra(CATEGORY_ID_KEY, -1);
            if(categoryId != -1) {
                Category incomingCategory = Utils.getInstance().getCategoryById(categoryId);
                if(incomingCategory != null) {
                    setData(incomingCategory);
                }
            }
        }

        callingActivity = intent.getStringExtra("activity");
        toVideoSectionPageButton = findViewById(R.id.toVideoSectionPageButton);

        toVideoSectionPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (callingActivity) {
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
                    case "FormsOfAddressActivity": intent = new Intent(VideoPlayerActivity.this, FormsOfAddressActivity.class); break;
                    default: throw new NullPointerException("Invalid Selection");
                }
                startActivity(intent);
            }
        });

        videoSubjectRecyclerView.setAdapter(videoSubjectAdapter);
        videoSubjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        videoSubject = getIntent().getStringExtra("name").toLowerCase();
        videoSubjectFormatted = replaceDiacriticsAndSpaces(videoSubject);

        String uriPath = "android.resource://" + getPackageName() + "/raw/" + videoSubjectFormatted;
        String dynamicImageResource = "drawable/" + videoSubjectFormatted;
        int imageKey = getResources().getIdentifier(dynamicImageResource, "drawable", getPackageName());
        videoView.setVideoPath(uriPath);

        Category selectedCategory = new Category(1, videoSubject.toUpperCase(), imageKey);
        videoSubjectAdapter.setSelectedCategory(selectedCategory);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }

    private void setData(Category category) {
            selectedCategoryName.setText(category.getName());
            Glide.with(this).asBitmap().load(category.getImageSource()).into(selectedCategoryImage);

    }

    private static String replaceDiacriticsAndSpaces(String str) {
        char[] charArray = str.toCharArray();
        for(int i = 0; i < charArray.length; i++) {
            switch(charArray[i]) {
                case ' ': charArray[i] = '_'; break;
                case 'ă':
                case 'â': charArray[i] = 'a'; break;
                case 'î': charArray[i] = 'i'; break;
                case 'ş': charArray[i] = 's'; break;
                case 'ţ': charArray[i] = 't'; break;
                default: break;
            }
        }
        return String.valueOf(charArray);
    }
}