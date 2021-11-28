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

public class  WildAnimalsRecyclerViewAdapter extends RecyclerView.Adapter<WildAnimalsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "WildAnimalsAdapter";

    private ArrayList<Category> wildAnimals = new ArrayList<>();
    private Context wildAnimalsContext;

    public WildAnimalsRecyclerViewAdapter(Context wildAnimalsContext) {
        this.wildAnimalsContext = wildAnimalsContext;
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
        holder.categoryName.setText(wildAnimals.get(position).getName());

        Glide.with(wildAnimalsContext).asBitmap().load(wildAnimals.get(position).getImageSource()).into(holder.categoryImage);

        holder.categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(wildAnimalsContext, VideoPlayerActivity.class);
                if (wildAnimals.get(position).getId() < 0) {
                    throw new NullPointerException("Invalid Selection");
                }
                intent.putExtra("name", wildAnimals.get(position).getName());
                intent.putExtra("intent", "WildAnimalsActivity");
                wildAnimalsContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wildAnimals.size();
    }

    public void setWildAnimals(ArrayList<Category> wildAnimals) {
        this.wildAnimals = wildAnimals;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView categoryCard;
        private ImageView categoryImage;
        private TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryCard = itemView.findViewById(R.id.categoryCard);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
        }
    }
}
