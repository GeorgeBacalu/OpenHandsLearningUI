package com.project.ohl.presentation.video_section.objects;

import com.project.ohl.domain.model.CatalogIds;
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
import com.project.ohl.domain.model.Category;
import com.project.ohl.R;
import com.project.ohl.presentation.video_section.objects.city.CityActivity;
import com.project.ohl.presentation.video_section.objects.classroom.ClassActivity;
import com.project.ohl.presentation.video_section.objects.home.HomeActivity;
import com.project.ohl.presentation.video_section.objects.outside.OutsideActivity;
import com.project.ohl.presentation.video_section.objects.shop.ShopActivity;

import java.util.ArrayList;
import java.util.List;

public class ObjectsRecyclerViewAdapter extends RecyclerView.Adapter<ObjectsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ObjectsAdapter";
    private List<Category> objects = new ArrayList<>();
    private final Context objectsContext;

    public ObjectsRecyclerViewAdapter(Context objectsContext) { this.objectsContext = objectsContext; }

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
        holder.categoryName.setText(objects.get(position).getName());
        Glide.with(objectsContext).asBitmap().load(objects.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(v -> {
            Intent intent;
            if (CatalogIds.HOME.equals(objects.get(position).getId())) intent = new Intent(objectsContext, HomeActivity.class);
            else if (CatalogIds.OUTSIDE.equals(objects.get(position).getId())) intent = new Intent(objectsContext, OutsideActivity.class);
            else if (CatalogIds.CLASSROOM.equals(objects.get(position).getId())) intent = new Intent(objectsContext, ClassActivity.class);
            else if (CatalogIds.SHOP.equals(objects.get(position).getId())) intent = new Intent(objectsContext, ShopActivity.class);
            else if (CatalogIds.CITY.equals(objects.get(position).getId())) intent = new Intent(objectsContext, CityActivity.class);
            else throw new IllegalArgumentException("Unknown catalog entry");
            objectsContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setObjects(List<Category> objects) {
        this.objects = objects;
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
