package com.example.llt_project_separate.video_section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.llt_project_separate.general.favorites.FavoritesActivity;
import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.general.shared_preferences.Utils;

import com.example.llt_project_separate.retrofit.UtilsRetrofit;
import java.util.ArrayList;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlayerActivity extends AppCompatActivity {
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_NAME = "categoryName";
    public static final String CATEGORY_IMAGE = "categoryImage";

    private TextView categoryName;
    private ImageView categoryImage;
    private Button favoriteButton;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initializeViews();

        Intent intent = getIntent();
        if(intent != null) {
            int categoryId = intent.getIntExtra(CATEGORY_ID, -1);
            int imageSubject = intent.getIntExtra(CATEGORY_IMAGE, -1);
            String categoryName = intent.getStringExtra(CATEGORY_NAME);

            if(categoryId != -1) {
                Category incomingCategory = Utils.getInstance(this).getCategoryById(categoryId);

//                final Category[] incomingCategory = {null};
//                UtilsRetrofit.getInstance(this).getCategoryById(new Callback<Category>() {
//                    @Override
//                    public void onResponse(Call<Category> call, Response<Category> response) {
//                        if(response.isSuccessful()) {
//                            incomingCategory[0] = response.body();
//                        } else {
//                            Toast.makeText(VideoPlayerActivity.this, "Ceva nu e bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Category> call, Throwable t) {
//                        Toast.makeText(VideoPlayerActivity.this, "Ceva nu e bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
//                    }
//                }, categoryId);
                incomingCategory.setName(categoryName);
                if(incomingCategory != null) {
                    setData(incomingCategory, imageSubject);
                    handleFavorite(incomingCategory);
                }
            }
        }

        assert intent != null;

        String videoSubject = intent.getStringExtra(CATEGORY_NAME).toLowerCase();
        String videoSubjectFormatted = replaceDiacriticsAndSpaces(videoSubject);

        String uriPath = "android.resource://" + getPackageName() + "/raw/" + videoSubjectFormatted;
        videoView.setVideoPath(uriPath);
        // String dynamicImageResource = "drawable/" + videoSubjectFormatted;
        // int imageKey = getResources().getIdentifier(dynamicImageResource, "drawable", getPackageName());

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }

    private void initializeViews() {
        categoryName = findViewById(R.id.categoryName);
        categoryImage = findViewById(R.id.categoryImage);
        favoriteButton = findViewById(R.id.favoriteButton);
        videoView = findViewById(R.id.videoView);
    }

    private void setData(Category category, int imageSubject) {
        categoryName.setText(category.getName());
        Glide.with(this).asBitmap().load(imageSubject).into(categoryImage);
    }

    @SuppressLint("SetTextI18n")
    private void handleFavorite(Category incomingCategory) {
        ArrayList<Category> favoriteCategories = Utils.getInstance(this).getFavoriteCategories();

        boolean existInFavoriteCategories = false;
        for(Category category : favoriteCategories) {
            if (category.getId() == incomingCategory.getId()) {
                existInFavoriteCategories = true;
                break;
            }
        }
        if(existInFavoriteCategories) {
            favoriteButton.setText("Elimină favorit");
            favoriteButton.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerActivity.this);
                builder.setMessage("Eşti sigur că vrei să elimini " + incomingCategory.getName() + " de la favorite?");
                builder.setPositiveButton("DA", (dialog, which) -> {
                    String chosenToBeRemoved = incomingCategory.getName();
                    if(Utils.getInstance(VideoPlayerActivity.this).removedFromFavorite(incomingCategory)) {
                        Toast.makeText(VideoPlayerActivity.this,  chosenToBeRemoved + " eliminat", Toast.LENGTH_SHORT).show();
                        favoriteButton.setText("Adaugă la favorite");
                        Intent intent = new Intent(VideoPlayerActivity.this, FavoritesActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        VideoPlayerActivity.this.startActivity(intent);
                        favoriteCategories.remove(incomingCategory);
                        handleFavorite(incomingCategory);
                    } else {
                        Toast.makeText(VideoPlayerActivity.this, "Ceva nu e bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("NU", (dialog, which) -> {});
                builder.create().show();
            });
        } else {
//            favoriteButton.setOnClickListener(v -> {
//                if (Utils.getInstance(VideoPlayerActivity.this).addedToFavorite(incomingCategory)) {
//                    Toast.makeText(VideoPlayerActivity.this, incomingCategory.getName() + " adăugat la favorite", Toast.LENGTH_SHORT).show();
//                    handleFavorite(incomingCategory);
//                } else {
//                    Toast.makeText(VideoPlayerActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
//                }
//            });

           favoriteButton.setOnClickListener(v -> {
              UtilsRetrofit.getInstance(this).saveFavorite(new Callback<Category>() {
                 @Override
                 public void onResponse(@NonNull Call<Category> call, @NonNull Response<Category> response) {
                    if(response.isSuccessful()) {
                       Category savedFavorite = response.body();
                       Toast.makeText(VideoPlayerActivity.this, savedFavorite.getName() + " adăugat la favorite", Toast.LENGTH_SHORT).show();
                       handleFavorite(incomingCategory);
                    } else {
                       Toast.makeText(VideoPlayerActivity.this, "Salvarea a esuat!", Toast.LENGTH_SHORT).show();
                    }
                 }

                 @Override
                 public void onFailure(@NonNull Call<Category> call, @NonNull Throwable throwable) {
                    Toast.makeText(VideoPlayerActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
                 }
              }, incomingCategory.getId());
           });
        }
    }

    private static String replaceDiacriticsAndSpaces(String str) {
        char[] charArray = str.toCharArray();
        for(int i = 0; i < charArray.length; i++) {
            switch(charArray[i]) {
                case '/':
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}