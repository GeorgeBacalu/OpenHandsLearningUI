package com.project.ohl.presentation.video_section;

import com.project.ohl.domain.model.CatalogIds;
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
import com.project.ohl.domain.model.Category;
import com.project.ohl.R;
import com.project.ohl.presentation.video_section.alphabet.AlphabetActivity;
import com.project.ohl.presentation.video_section.animals.AnimalsActivity;
import com.project.ohl.presentation.video_section.colors.ColorsActivity;
import com.project.ohl.presentation.video_section.emotions.EmotionsActivity;
import com.project.ohl.presentation.video_section.forms_of_address.FormsOfAddressActivity;
import com.project.ohl.presentation.video_section.numbers.NumbersActivity;
import com.project.ohl.presentation.video_section.objects.ObjectsActivity;
import com.project.ohl.presentation.video_section.people.PeopleActivity;
import com.project.ohl.presentation.video_section.verbs.VerbsActivity;

import java.util.ArrayList;
import java.util.List;

public class VideoSectionRecyclerViewAdapter extends RecyclerView.Adapter<VideoSectionRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "VideoCategoriesAdapter";
    private List<Category> mainCategories = new ArrayList<>();
    private final Context mainContext;

    public VideoSectionRecyclerViewAdapter(Context mainContext) {
        this.mainContext = mainContext;
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
        holder.categoryName.setText(mainCategories.get(position).getName());
        Glide.with(mainContext).asBitmap().load(mainCategories.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(v -> {
            Intent intent;
            if (CatalogIds.ALPHABET.equals(mainCategories.get(position).getId())) intent = new Intent(mainContext, AlphabetActivity.class);
            else if (CatalogIds.NUMBERS.equals(mainCategories.get(position).getId())) intent = new Intent(mainContext, NumbersActivity.class);
            else if (CatalogIds.COLORS.equals(mainCategories.get(position).getId())) intent = new Intent(mainContext, ColorsActivity.class);
            else if (CatalogIds.ANIMALS.equals(mainCategories.get(position).getId())) intent = new Intent(mainContext, AnimalsActivity.class);
            else if (CatalogIds.OBJECTS.equals(mainCategories.get(position).getId())) intent = new Intent(mainContext, ObjectsActivity.class);
            else if (CatalogIds.PEOPLE.equals(mainCategories.get(position).getId())) intent = new Intent(mainContext, PeopleActivity.class);
            else if (CatalogIds.EMOTIONS.equals(mainCategories.get(position).getId())) intent = new Intent(mainContext, EmotionsActivity.class);
            else if (CatalogIds.VERBS.equals(mainCategories.get(position).getId())) intent = new Intent(mainContext, VerbsActivity.class);
            else if (CatalogIds.FORMS_OF_ADDRESS.equals(mainCategories.get(position).getId())) intent = new Intent(mainContext, FormsOfAddressActivity.class);
            else throw new IllegalArgumentException("Unknown catalog entry");
            mainContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mainCategories.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMainCategories(List<Category> mainCategories) {
        this.mainCategories = mainCategories;
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
