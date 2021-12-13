package com.example.llt_project_separate;

import static com.example.llt_project_separate.VideoPlayerActivity.CATEGORY_ID;
import static com.example.llt_project_separate.VideoPlayerActivity.CATEGORY_IMAGE;
import static com.example.llt_project_separate.VideoPlayerActivity.CATEGORY_NAME;

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

public class AlphabetRecyclerViewAdapter extends RecyclerView.Adapter<AlphabetRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "AlphabetAdapter";
    private List<Category> letters = new ArrayList<>();
    private final Context alphabetContext;

    public AlphabetRecyclerViewAdapter(Context alphabetContext) { this.alphabetContext = alphabetContext; }

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
        holder.categoryName.setText(letters.get(position).getName());
        Glide.with(alphabetContext).asBitmap().load(letters.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(v -> {
            Intent intent = new Intent(alphabetContext, VideoPlayerActivity.class);
            if (letters.get(position).getId() < 0) throw new NullPointerException("Invalid Selection");
            intent.putExtra(CATEGORY_ID, letters.get(position).getId());
            intent.putExtra(CATEGORY_NAME, letters.get(position).getName());
            intent.putExtra(CATEGORY_IMAGE, letters.get(position).getImageSource());
            alphabetContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return letters.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setLetters(List<Category> letters) {
        this.letters = letters;
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
