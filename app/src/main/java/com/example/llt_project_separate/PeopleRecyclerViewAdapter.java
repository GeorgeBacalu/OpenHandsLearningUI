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

public class PeopleRecyclerViewAdapter extends RecyclerView.Adapter<PeopleRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "PeopleAdapter";
    private ArrayList<Category> people = new ArrayList<>();
    private Context peopleContext;

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
        holder.categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch(people.get(position).getId()) {
                    case 1: intent = new Intent(peopleContext, FamilyMembersActivity.class); break;
                    case 2: intent = new Intent(peopleContext, PronounsActivity.class); break;
                    default: throw new NullPointerException("Invalid Selection");
                }
                peopleContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public void setPeople(ArrayList<Category> people) {
        this.people = people;
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
