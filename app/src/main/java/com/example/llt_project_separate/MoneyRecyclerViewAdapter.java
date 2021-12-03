package com.example.llt_project_separate;

import static com.example.llt_project_separate.VideoPlayerActivity.CATEGORY_ID;
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

public class MoneyRecyclerViewAdapter extends RecyclerView.Adapter<MoneyRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "MoneyAdapter";
    private ArrayList<Category> moneyUnits = new ArrayList<>();
    private Context moneyContext;

    public MoneyRecyclerViewAdapter(Context moneyContext) {
        this.moneyContext = moneyContext;
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
        holder.categoryName.setText(moneyUnits.get(position).getName());
        Glide.with(moneyContext).asBitmap().load(moneyUnits.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(moneyContext, VideoPlayerActivity.class);
                if (moneyUnits.get(position).getId() < 0) {
                    throw new NullPointerException("Invalid Selection");
                }
                intent.putExtra(CATEGORY_ID, moneyUnits.get(position).getId());
                intent.putExtra(CATEGORY_NAME, moneyUnits.get(position).getName());
                moneyContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moneyUnits.size();
    }

    public void setMoneyUnits(ArrayList<Category> moneyUnits) {
        this.moneyUnits = moneyUnits;
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
