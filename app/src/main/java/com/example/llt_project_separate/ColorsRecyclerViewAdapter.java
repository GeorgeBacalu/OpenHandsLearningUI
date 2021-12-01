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

public class ColorsRecyclerViewAdapter extends RecyclerView.Adapter<ColorsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ColorsAdapter";
    private ArrayList<Category> colors = new ArrayList<>();
    private Context colorsContext;

    public ColorsRecyclerViewAdapter(Context colorsContext) {
        this.colorsContext = colorsContext;
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
        holder.categoryName.setText(colors.get(position).getName());
        Glide.with(colorsContext).asBitmap().load(colors.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(colorsContext, VideoPlayerActivity.class);
                if (colors.get(position).getId() < 0) {
                    throw new NullPointerException("Invalid Selection");
                }
                intent.putExtra("name", colors.get(position).getName());
                intent.putExtra("activity", "ColorsActivity");
                colorsContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    public void setColors(ArrayList<Category> colors) {
        this.colors = colors;
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
