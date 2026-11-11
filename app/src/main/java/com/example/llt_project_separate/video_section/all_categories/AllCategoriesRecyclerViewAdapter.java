package com.example.llt_project_separate.video_section.all_categories;

import static com.example.llt_project_separate.video_section.single_category.CategoryActivity.CATEGORY_ID;
import static com.example.llt_project_separate.video_section.single_category.CategoryActivity.CATEGORY_NAME;
import static com.example.llt_project_separate.video_section.VideoPlayerActivity.CATEGORY_IMAGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.video_section.VideoPlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class AllCategoriesRecyclerViewAdapter extends RecyclerView.Adapter<AllCategoriesRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "AllCategoriesAdapter";
    private List<Category> categories = new ArrayList<>();
    private final Context categoriesContext;

    public AllCategoriesRecyclerViewAdapter(Context categoriesContext) { this.categoriesContext = categoriesContext; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.categoryName.setText(categories.get(position).getName());
        Glide.with(categoriesContext).asBitmap().load(categories.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(v -> {
            Intent intent = new Intent(categoriesContext, VideoPlayerActivity.class);
            if (categories.get(position).getId() < 0) throw new NullPointerException("Invalid Selection");
            intent.putExtra(CATEGORY_ID, categories.get(position).getId());
            intent.putExtra(CATEGORY_NAME, categories.get(position).getName());
            intent.putExtra(CATEGORY_IMAGE, categories.get(position).getImageSource());
            categoriesContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView categoryCard;
        private final ImageView categoryImage;
        private final TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryCard = itemView.findViewById(R.id.categoryCard);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
        }
    }
}
