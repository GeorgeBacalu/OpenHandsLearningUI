package com.project.ohl.presentation.video_section.single_category;

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
import com.project.ohl.presentation.MediaResources;
import com.project.ohl.presentation.video_section.VideoPlayerActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    private List<Category> categories = new ArrayList<>();
    private final Context context;
    private final Consumer<Category> onRemove;
    private boolean removalPending;

    public CategoryRecyclerViewAdapter(Context context, Consumer<Category> onRemove) {
        this.context = context;
        this.onRemove = onRemove;
    }

    @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.name.setText(category.getName());
        holder.name.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        holder.image.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        holder.itemView.setFocusable(true);
        MediaResources.loadImage(context, category, holder.image);
        holder.itemView.setContentDescription(context.getString(R.string.favorites_open_category, category.getName()));
        holder.remove.setContentDescription(context.getString(R.string.favorites_remove_category, category.getName()));
        bindRemovalState(holder);
        holder.remove.setVisibility(onRemove == null ? View.GONE : View.VISIBLE);
        holder.itemView.setOnClickListener(view -> {
            int current = holder.getBindingAdapterPosition();
            if (current == RecyclerView.NO_POSITION) return;
            Category selected = categories.get(current);
            context.startActivity(new Intent(context, VideoPlayerActivity.class)
                    .putExtra(VideoPlayerActivity.CATEGORY_ID, selected.getId().toString())
                    .putExtra(VideoPlayerActivity.CATEGORY_NAME, selected.getName())
                    .putExtra(VideoPlayerActivity.CATEGORY_IMAGE, selected.getImageSource() == null ? 0 : selected.getImageSource().intValue()));
        });
        holder.remove.setOnClickListener(view -> {
            int current = holder.getBindingAdapterPosition();
            if (!removalPending && current != RecyclerView.NO_POSITION && onRemove != null) onRemove.accept(categories.get(current));
        });
    }

    @Override public int getItemCount() { return categories.size(); }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) onBindViewHolder(holder, position);
        else bindRemovalState(holder);
    }

    private void bindRemovalState(ViewHolder holder) {
        holder.remove.setEnabled(!removalPending);
        holder.remove.setAlpha(removalPending ? 0.45f : 1f);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public void setRemovalPending(boolean pending) {
        removalPending = pending;
        notifyItemRangeChanged(0, categories.size(), "removal");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView image;
        private final View remove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.categoryName);
            image = itemView.findViewById(R.id.categoryImage);
            remove = itemView.findViewById(R.id.removeFromFavoriteButton);
        }
    }
}
