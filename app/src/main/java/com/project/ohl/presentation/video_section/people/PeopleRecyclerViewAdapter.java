package com.project.ohl.presentation.video_section.people;

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
import com.project.ohl.presentation.video_section.people.family_members.FamilyMembersActivity;
import com.project.ohl.presentation.video_section.people.pronouns.PronounsActivity;

import java.util.ArrayList;
import java.util.List;

public class PeopleRecyclerViewAdapter extends RecyclerView.Adapter<PeopleRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "PeopleAdapter";
    private List<Category> people = new ArrayList<>();
    private final Context peopleContext;

    public PeopleRecyclerViewAdapter(Context peopleContext) {
        this.peopleContext = peopleContext;
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
        holder.categoryName.setText(people.get(position).getName());
        Glide.with(peopleContext).asBitmap().load(people.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(v -> {
            Intent intent;
            if (CatalogIds.FAMILY_MEMBERS.equals(people.get(position).getId())) intent = new Intent(peopleContext, FamilyMembersActivity.class);
            else if (CatalogIds.PRONOUNS.equals(people.get(position).getId())) intent = new Intent(peopleContext, PronounsActivity.class);
            else throw new IllegalArgumentException("Unknown catalog entry");
            peopleContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPeople(List<Category> people) {
        this.people = people;
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
