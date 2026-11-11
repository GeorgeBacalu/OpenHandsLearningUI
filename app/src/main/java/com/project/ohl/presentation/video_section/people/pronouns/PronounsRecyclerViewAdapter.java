package com.project.ohl.presentation.video_section.people.pronouns;

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
import com.project.ohl.R;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.video_section.VideoPlayerActivity;
import java.util.ArrayList;
import java.util.List;

import static com.project.ohl.presentation.video_section.VideoPlayerActivity.CATEGORY_ID;
import static com.project.ohl.presentation.video_section.VideoPlayerActivity.CATEGORY_NAME;

public class PronounsRecyclerViewAdapter extends RecyclerView.Adapter<PronounsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "PronounsAdapter";
    private List<Category> pronouns = new ArrayList<>();
    private final Context pronounsContext;

    public PronounsRecyclerViewAdapter(Context pronounsContext) { this.pronounsContext = pronounsContext; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_no_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.categoryName.setText(pronouns.get(position).getName());
        holder.categoryCard.setOnClickListener(v -> {
            Intent intent = new Intent(pronounsContext, VideoPlayerActivity.class);
            if (pronouns.get(position).getId() == null) {
                throw new NullPointerException("Invalid Selection");
            }
            intent.putExtra(CATEGORY_ID, pronouns.get(position).getId().toString());
            intent.putExtra(CATEGORY_NAME, pronouns.get(position).getName());
            pronounsContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pronouns.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPronouns(List<Category> pronouns) {
        this.pronouns = pronouns;
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
