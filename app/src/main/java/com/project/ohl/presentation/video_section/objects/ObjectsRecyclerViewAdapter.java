package com.project.ohl.presentation.video_section.objects;

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
import com.bumptech.glide.Glide;
import com.project.ohl.R;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.CategoryNavigation;
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
            int current = holder.getBindingAdapterPosition();
            if (current == RecyclerView.NO_POSITION) return;
            objectsContext.startActivity(CategoryNavigation.intent(objectsContext, objects.get(current)));
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
