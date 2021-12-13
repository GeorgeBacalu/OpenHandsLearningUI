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

public class AnimalsRecyclerViewAdapter extends RecyclerView.Adapter<AnimalsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "AnimalsAdapter";
    private List<Category> animals = new ArrayList<>();
    private final Context animalsContext;

    public AnimalsRecyclerViewAdapter(Context animalsContext) { this.animalsContext = animalsContext; }

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
        holder.categoryName.setText(animals.get(position).getName());
        Glide.with(animalsContext).asBitmap().load(animals.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(v -> {
            Intent intent;
            switch(animals.get(position).getId()) {
                case 180: intent = new Intent(animalsContext, HouseholdAnimalsActivity.class); break;
                case 181: intent = new Intent(animalsContext, WildAnimalsActivity.class); break;
                default: throw new NullPointerException("Invalid Selection");
            }
            animalsContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAnimals(List<Category> animals) {
        this.animals = animals;
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
