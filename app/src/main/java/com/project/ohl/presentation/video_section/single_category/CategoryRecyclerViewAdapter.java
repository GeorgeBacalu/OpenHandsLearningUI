package com.project.ohl.presentation.video_section.single_category;

import static com.project.ohl.presentation.video_section.VideoPlayerActivity.CATEGORY_ID;
import static com.project.ohl.presentation.video_section.VideoPlayerActivity.CATEGORY_IMAGE;
import static com.project.ohl.presentation.video_section.VideoPlayerActivity.CATEGORY_NAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.ohl.domain.model.Category;
import com.project.ohl.R;
import com.project.ohl.presentation.video_section.VideoPlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "CategoryAdapter";
    private List<Category> categories = new ArrayList<>();
    private final Context categoryContext;
    private final java.util.function.Consumer<Category> onRemove;

    public CategoryRecyclerViewAdapter(Context categoryContext, java.util.function.Consumer<Category> onRemove) {
        this.categoryContext = categoryContext;
        this.onRemove = onRemove;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_expandable, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.categoryName.setText(categories.get(position).getName());
        Glide.with(categoryContext).asBitmap().load(categories.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(v -> {
            Intent intent = new Intent(categoryContext, VideoPlayerActivity.class);
            int current = holder.getBindingAdapterPosition();
            if (current == RecyclerView.NO_POSITION) return;
            intent.putExtra(CATEGORY_ID, categories.get(current).getId().toString());
            intent.putExtra(CATEGORY_NAME, categories.get(current).getName());
            intent.putExtra(CATEGORY_IMAGE, categories.get(current).getImageSource());
            categoryContext.startActivity(intent);
        });

        if (categories.get(position).isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.categoryCard, new AutoTransition());
            holder.expendedRelativeLayout.setVisibility(View.VISIBLE);
            holder.arrowButton.setBackgroundResource(R.drawable.ic_arrow_up);
            if (onRemove != null) {
                holder.removeFromFavoriteButton.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(categoryContext);
                    builder.setMessage("Eşti sigur că vrei să elimini " + categories.get(position).getName() + " de la favorite?");
                    builder.setPositiveButton("DA", (dialog, which) -> {
                        int current = holder.getBindingAdapterPosition();
                        if (current != RecyclerView.NO_POSITION) onRemove.accept(categories.get(current));
                    });
                    builder.setNegativeButton("NU", (dialog, which) -> {
                    });
                    builder.create().show();
                });
            }
        } else {
            TransitionManager.beginDelayedTransition(holder.categoryCard, new AutoTransition());
            holder.expendedRelativeLayout.setVisibility(View.GONE);
            holder.arrowButton.setBackgroundResource(R.drawable.ic_arrow_down);
        }
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView categoryCard;
        private final TextView categoryName;
        private final ImageView categoryImage;
        private final Button arrowButton, removeFromFavoriteButton;
        private final RelativeLayout expendedRelativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryCard = itemView.findViewById(R.id.categoryCard);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
            arrowButton = itemView.findViewById(R.id.arrowButton);
            removeFromFavoriteButton = itemView.findViewById(R.id.removeFromFavoriteButton);
            expendedRelativeLayout = itemView.findViewById(R.id.expendedRelativeLayout);

            arrowButton.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position == RecyclerView.NO_POSITION) return;
                Category category = categories.get(position);
                category.setExpanded(!category.isExpanded());
                notifyItemChanged(getBindingAdapterPosition());
            });
        }
    }
}
