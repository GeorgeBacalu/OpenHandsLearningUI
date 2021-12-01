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

public class OutsideRecyclerViewAdapter extends RecyclerView.Adapter<OutsideRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "OutsideAdapter";
    private ArrayList<Category> outsideObjects = new ArrayList<>();
    private Context outsideContext;

    public OutsideRecyclerViewAdapter(Context outsideContext) {
        this.outsideContext = outsideContext;
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
        holder.categoryName.setText(outsideObjects.get(position).getName());
        Glide.with(outsideContext).asBitmap().load(outsideObjects.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(outsideContext, VideoPlayerActivity.class);
                if (outsideObjects.get(position).getId() < 0) {
                    throw new NullPointerException("Invalid Selection");
                }
                intent.putExtra("name", outsideObjects.get(position).getName());
                intent.putExtra("activity", "OutsideActivity");
                outsideContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return outsideObjects.size();
    }

    public void setOutsideObjects(ArrayList<Category> outsideObjects) {
        this.outsideObjects = outsideObjects;
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
