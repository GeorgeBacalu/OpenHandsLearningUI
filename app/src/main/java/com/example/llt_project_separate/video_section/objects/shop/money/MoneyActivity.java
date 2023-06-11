package com.example.llt_project_separate.video_section.objects.shop.money;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.MainActivity;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.retrofit.UtilsRetrofit;
import com.example.llt_project_separate.video_section.VideoSectionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoneyActivity extends AppCompatActivity {
    private RecyclerView moneyRecyclerView;
    private MoneyRecyclerViewAdapter moneyAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(MoneyActivity.this, MainActivity.class);
            startActivity(intent);
        });

        moneyRecyclerView.setAdapter(moneyAdapter);
        moneyRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        UtilsRetrofit.getInstance(this).getCategoriesByParentIdAndSectionIdAndName(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                if(response.isSuccessful()) {
                    List<Category> moneyCategories = response.body();
                    for(Category category : moneyCategories) {
                        String name = category.getName();
                        int drawableId = getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", getPackageName());
                        category.setImageSource(drawableId);
                    }
                    moneyAdapter.setMoneyUnits(moneyCategories);
                } else {
                    Toast.makeText(MoneyActivity.this, "Obtinerea subcategoriilor bani a esuat!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable throwable) {
                Toast.makeText(MoneyActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
            }
        }, 127, 1, "");

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            UtilsRetrofit.getInstance(this).getCategoriesByParentIdAndSectionIdAndName(new Callback<List<Category>>() {
                @Override
                public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                    if(response.isSuccessful()) {
                        List<Category> filteredMoneyCategories = response.body();
                        for(Category category : filteredMoneyCategories) {
                            String name = category.getName();
                            int drawableId = getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", getPackageName());
                            category.setImageSource(drawableId);
                        }
                        searchBarInput.setText("");
                        moneyAdapter.setMoneyUnits(filteredMoneyCategories);
                        moneyAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MoneyActivity.this, "Obtinerea subcategoriilor bani filtrate a esuat!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable throwable) {
                    Toast.makeText(MoneyActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
                }
            }, 127, 1, searchBarInputText);
        });
    }

    private void initializeViews() {
        moneyRecyclerView = findViewById(R.id.moneyRecyclerView);
        moneyAdapter = new MoneyRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        searchBarInput = findViewById(R.id.searchBarInput);
        searchBarIcon = findViewById(R.id.searchBarIcon);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}