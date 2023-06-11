package com.example.llt_project_separate.video_section.objects;

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
import com.example.llt_project_separate.video_section.objects.city.CityActivity;
import com.example.llt_project_separate.video_section.objects.classroom.ClassActivity;
import com.example.llt_project_separate.video_section.objects.home.HomeActivity;
import com.example.llt_project_separate.video_section.objects.outside.OutsideActivity;
import com.example.llt_project_separate.video_section.objects.shop.ShopActivity;

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
            switch(objects.get(position).getId()) {
                case 88: intent = new Intent(objectsContext, HomeActivity.class); break;
                case 89: intent = new Intent(objectsContext, OutsideActivity.class); break;
                case 90: intent = new Intent(objectsContext, ClassActivity.class); break;
                case 91: intent = new Intent(objectsContext, ShopActivity.class); break;
                case 92: intent = new Intent(objectsContext, CityActivity.class); break;
                default: throw new NullPointerException("Invalid Selection");
            }
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
