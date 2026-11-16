package com.project.ohl.presentation.video_section;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project.ohl.R;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.CategoryNavigation;
import com.project.ohl.presentation.MediaResources;
import java.util.List;
import java.util.function.Function;

public final class CatalogRecyclerViewAdapter extends RecyclerView.Adapter<CatalogRecyclerViewAdapter.ViewHolder> {
    private List<Category> categories = List.of();
    private final Context context;
    private final Function<Category, Intent> destination;

    private CatalogRecyclerViewAdapter(Context context, Function<Category, Intent> destination) {
        this.context = context;
        this.destination = destination;
    }

    public static CatalogRecyclerViewAdapter forCategories(Context context) {
        return new CatalogRecyclerViewAdapter(context, category -> CategoryNavigation.intent(context, category));
    }

    public static CatalogRecyclerViewAdapter forVideos(Context context) {
        return new CatalogRecyclerViewAdapter(context, category -> new Intent(context, VideoPlayerActivity.class)
                .putExtra(VideoPlayerActivity.CATEGORY_ID, category.getId().toString())
                .putExtra(VideoPlayerActivity.CATEGORY_NAME, category.getName())
                .putExtra(VideoPlayerActivity.CATEGORY_IMAGE, category.getImageUrl()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.name.setText(category.getName());
        MediaResources.loadImage(context, category, holder.image);
        holder.itemView.setOnClickListener(view -> {
            int current = holder.getBindingAdapterPosition();
            if (current != RecyclerView.NO_POSITION) {
                context.startActivity(destination.apply(categories.get(current)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView name;

        private ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.categoryImage);
            name = itemView.findViewById(R.id.categoryName);
        }
    }
}
