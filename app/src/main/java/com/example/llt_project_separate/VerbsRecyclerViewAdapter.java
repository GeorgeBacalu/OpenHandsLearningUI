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

public class VerbsRecyclerViewAdapter extends RecyclerView.Adapter<VerbsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "VerbsAdapter";
    private ArrayList<Category> verbs = new ArrayList<>();
    private Context verbsContext;

    public VerbsRecyclerViewAdapter(Context verbsContext) {
        this.verbsContext = verbsContext;
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
        holder.categoryName.setText(verbs.get(position).getName());
        Glide.with(verbsContext).asBitmap().load(verbs.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(verbsContext, VideoPlayerActivity.class);
                if (verbs.get(position).getId() < 0) {
                    throw new NullPointerException("Invalid Selection");
                }
                intent.putExtra("name", verbs.get(position).getName());
                intent.putExtra("activity", "VerbsActivity");
                verbsContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return verbs.size();
    }

    public void setVerbs(ArrayList<Category> verbs) {
        this.verbs = verbs;
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
