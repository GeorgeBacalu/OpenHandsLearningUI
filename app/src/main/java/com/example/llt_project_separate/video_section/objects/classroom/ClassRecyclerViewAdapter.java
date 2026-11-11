package com.example.llt_project_separate.video_section.objects.classroom;

import static com.example.llt_project_separate.video_section.VideoPlayerActivity.CATEGORY_ID;
import static com.example.llt_project_separate.video_section.VideoPlayerActivity.CATEGORY_IMAGE;
import static com.example.llt_project_separate.video_section.VideoPlayerActivity.CATEGORY_NAME;

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

public class ClassRecyclerViewAdapter extends RecyclerView.Adapter<ClassRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ClassAdapter";
    private List<Category> classObjects = new ArrayList<>();
    private final Context classContext;

    public ClassRecyclerViewAdapter(Context classContext) {
        this.classContext = classContext;
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
        holder.categoryName.setText(classObjects.get(position).getName());
        Glide.with(classContext).asBitmap().load(classObjects.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(v -> {
            Intent intent = new Intent(classContext, VideoPlayerActivity.class);
            if (classObjects.get(position).getId() < 0) {
                throw new NullPointerException("Invalid Selection");
            }
            intent.putExtra(CATEGORY_ID, classObjects.get(position).getId());
            intent.putExtra(CATEGORY_NAME, classObjects.get(position).getName());
            intent.putExtra(CATEGORY_IMAGE, classObjects.get(position).getImageSource());
            classContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return classObjects.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setClassObjects(List<Category> classObjects) {
        this.classObjects = classObjects;
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
