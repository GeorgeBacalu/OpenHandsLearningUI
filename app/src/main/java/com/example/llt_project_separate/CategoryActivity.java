package com.example.llt_project_separate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    public static final String CATEGORY_ID_KEY = "categoryId";
    public static final String CATEGORY_NAME_KEY = "categoryName";

    private TextView categoryName;
    private ImageView categoryImage, toVideoSectionPageButton;
    private Button addToFavoriteButton;
    private CardView categoryCard;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initializeViews();

        Intent intent = getIntent();
        if(intent != null) {
            int categoryId = intent.getIntExtra(CATEGORY_ID_KEY, -1);
            if(categoryId != -1) {
                Category incomingCategory = Utils.getInstance(this).getCategoryById(categoryId);
                if(incomingCategory != null) {
                    setData(incomingCategory);
                    handleAddToFavorite(incomingCategory);
                }
            }
        }
    }

    /**
     * Add the category to favorite categories ArrayList
     * @param incomingCategory
     */

    private void handleAddToFavorite(final Category incomingCategory) {
        ArrayList<Category> favoriteCategories = Utils.getInstance(this).getFavoriteCategories();

        boolean existInFavoriteCategories = false;

        for(Category category : favoriteCategories) {
            if(category.getId() == incomingCategory.getId()) {
                existInFavoriteCategories = true;
            }
        }

        if(existInFavoriteCategories) {
            addToFavoriteButton.setText("Elimina favorit");
        } else {
            addToFavoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(CategoryActivity.this).addedToFavorite(incomingCategory)) {
                        Toast.makeText(CategoryActivity.this, incomingCategory.getName() + " adăugat la favorite", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CategoryActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(Category category) {
        categoryName.setText(category.getName());
        Glide.with(this).asBitmap().load(category.getImageSource()).into(categoryImage);
    }

    private void initializeViews() {
        categoryName = findViewById(R.id.categoryName);
        categoryImage = findViewById(R.id.categoryImage);
        categoryCard = findViewById(R.id.categoryCard);

        addToFavoriteButton = findViewById(R.id.addToFavoriteButton);
        // videoView = findViewById(R.id.videoView);
    }
}