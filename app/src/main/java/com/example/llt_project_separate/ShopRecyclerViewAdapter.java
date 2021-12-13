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

public class ShopRecyclerViewAdapter extends RecyclerView.Adapter<ShopRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ShopRecyclerViewAdapter";
    private List<Category> shopObjects = new ArrayList<>();
    private final Context shopContext;

    public ShopRecyclerViewAdapter(Context shopContext) {
        this.shopContext = shopContext;
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
        holder.categoryName.setText(shopObjects.get(position).getName());
        Glide.with(shopContext).asBitmap().load(shopObjects.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(v -> {
            Intent intent;
            switch(shopObjects.get(position).getId()) {
                case 189: intent = new Intent(shopContext, MoneyActivity.class); break;
                case 190: intent = new Intent(shopContext, ProductsActivity.class); break;
                default: throw new NullPointerException("Invalid Selection");
            }
            shopContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return shopObjects.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setShopObjects(List<Category> shopObjects) {
        this.shopObjects = shopObjects;
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
