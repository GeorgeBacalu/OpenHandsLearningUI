package com.example.llt_project_separate;

import android.content.Context;
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

public class VideoPlayerRecyclerViewAdapter extends RecyclerView.Adapter<VideoPlayerRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "VideoPlayerAdapter";

    private Category selectedCategory = new Category();
    private Context videoContext;

    public VideoPlayerRecyclerViewAdapter(Context videoContext) {
        this.videoContext = videoContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_dark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.selectedItemName.setText(selectedCategory.getName());

        Glide.with(videoContext).asBitmap().load(selectedCategory.getImageSource()).into(holder.selectedItemImage);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView selectedItemCard;
        private ImageView selectedItemImage;
        private TextView selectedItemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            selectedItemCard = itemView.findViewById(R.id.selectedItemCard);
            selectedItemImage = itemView.findViewById(R.id.selectedItemImage);
            selectedItemName = itemView.findViewById(R.id.selectedItemName);
        }
    }
}
