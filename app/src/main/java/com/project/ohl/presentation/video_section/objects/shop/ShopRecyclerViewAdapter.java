package com.project.ohl.presentation.video_section.objects.shop;

import android.annotation.SuppressLint;
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
import com.project.ohl.R;
import com.project.ohl.presentation.MediaResources;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.CategoryNavigation;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.categoryName.setText(shopObjects.get(position).getName());
        MediaResources.loadImage(shopContext, shopObjects.get(position).getImageSource(), holder.categoryImage);
        holder.categoryCard.setOnClickListener(v -> {
            int current = holder.getBindingAdapterPosition();
            if (current == RecyclerView.NO_POSITION) return;
            shopContext.startActivity(CategoryNavigation.intent(shopContext, shopObjects.get(current)));
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
