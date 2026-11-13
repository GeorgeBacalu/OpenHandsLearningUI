package com.project.ohl.presentation.video_section.animals.household;

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
import com.project.ohl.R;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.video_section.VideoPlayerActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.project.ohl.presentation.video_section.VideoPlayerActivity.CATEGORY_ID;
import static com.project.ohl.presentation.video_section.VideoPlayerActivity.CATEGORY_IMAGE;
import static com.project.ohl.presentation.video_section.VideoPlayerActivity.CATEGORY_NAME;

public class HouseholdAnimalsRecyclerViewAdapter extends RecyclerView.Adapter<HouseholdAnimalsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "HouseholdAnimalsAdapter";
    private List<Category> householdAnimals = new ArrayList<>();
    private final Context householdAnimalsContext;

    public HouseholdAnimalsRecyclerViewAdapter(Context householdAnimalsContext) { this.householdAnimalsContext = householdAnimalsContext; }

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
        holder.categoryName.setText(householdAnimals.get(position).getName());
        Glide.with(householdAnimalsContext).asBitmap().load(householdAnimals.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(v -> {
            Intent intent = new Intent(householdAnimalsContext, VideoPlayerActivity.class);
            if (householdAnimals.get(position).getId() == null) throw new NullPointerException("Invalid Selection");

            UUID id = householdAnimals.get(position).getId();
            intent.putExtra(CATEGORY_ID, id.toString());
            String name = householdAnimals.get(position).getName();
            intent.putExtra(CATEGORY_NAME, name);
            Integer imageSource = householdAnimals.get(position).getImageSource();
            intent.putExtra(CATEGORY_IMAGE, imageSource);
            householdAnimalsContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return householdAnimals.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setHouseholdAnimals(List<Category> householdAnimals) {
        this.householdAnimals = householdAnimals;
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
