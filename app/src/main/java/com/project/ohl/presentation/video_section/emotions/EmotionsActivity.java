package com.project.ohl.presentation.video_section.emotions;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.ohl.R;
import com.project.ohl.presentation.CategoryPageLayout;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.CategoryNavigation;
import com.project.ohl.presentation.LearningViewModel;
import com.project.ohl.presentation.MediaResources;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static android.R.id.home;

public class EmotionsActivity extends AppCompatActivity {
    private RecyclerView emotionsRecyclerView;
    private EmotionsRecyclerViewAdapter emotionsAdapter;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID parentId = CategoryNavigation.parentId(this);
        setContentView(R.layout.activity_emotions);
      CategoryPageLayout.configure(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();


        emotionsRecyclerView.setAdapter(emotionsAdapter);
        emotionsRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.video_category_columns)));

        LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndName(parentId, ""), result -> {
            if (result.error() != null) {
                Toast.makeText(EmotionsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                    List<Category> emotions = result.value();
                    MediaResources.bindImages(this, emotions);
                    emotionsAdapter.setEmotions(emotions);

            }
        });

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase(Locale.ROOT);
            LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndName(parentId, searchBarInputText), result -> {
            if (result.error() != null) {
                    Toast.makeText(EmotionsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                        List<Category> filteredEmotions = result.value();
                        MediaResources.bindImages(this, filteredEmotions);
                        searchBarInput.setText("");
                        emotionsAdapter.setEmotions(filteredEmotions);

            }
        });
        });
    }

    private void initializeViews() {
        emotionsRecyclerView = findViewById(R.id.emotionsRecyclerView);
        emotionsAdapter = new EmotionsRecyclerViewAdapter(this);
        searchBarInput = findViewById(R.id.searchBarInput);
        searchBarIcon = findViewById(R.id.searchBarIcon);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == home) getOnBackPressedDispatcher().onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
