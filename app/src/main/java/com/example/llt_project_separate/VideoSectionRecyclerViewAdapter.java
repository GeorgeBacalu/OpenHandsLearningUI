package com.example.llt_project_separate;

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

import java.util.ArrayList;
import java.util.List;

public class VideoSectionRecyclerViewAdapter extends RecyclerView.Adapter<VideoSectionRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "VideoCategoriesAdapter";
    private List<Category> mainCategories = new ArrayList<>();
    private final Context mainContext;

    public VideoSectionRecyclerViewAdapter(Context mainContext) {
        this.mainContext = mainContext;
    }

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
        holder.categoryName.setText(mainCategories.get(position).getName());
        Glide.with(mainContext).asBitmap().load(mainCategories.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(v -> {
            Intent intent;
            switch(mainCategories.get(position).getId()) {
                case 1001: intent = new Intent(mainContext, AlphabetActivity.class); break;
                case 1002: intent = new Intent(mainContext, NumbersActivity.class); break;
                case 1003: intent = new Intent(mainContext, ColorsActivity.class); break;
                case 1004: intent = new Intent(mainContext, AnimalsActivity.class); break;
                case 1005: intent = new Intent(mainContext, ObjectsActivity.class); break;
                case 1006: intent = new Intent(mainContext, PeopleActivity.class); break;
                case 1007: intent = new Intent(mainContext, EmotionsActivity.class); break;
                case 1008: intent = new Intent(mainContext, VerbsActivity.class); break;
                case 1009: intent = new Intent(mainContext, FormsOfAddressActivity.class); break;
                default: throw new NullPointerException("Invalid Selection");
            }
            mainContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mainCategories.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMainCategories(List<Category> mainCategories) {
        this.mainCategories = mainCategories;
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
