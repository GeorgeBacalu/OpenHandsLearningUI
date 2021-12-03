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

public class ObjectsRecyclerViewAdapter extends RecyclerView.Adapter<ObjectsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ObjectsAdapter";
    private ArrayList<Category> objects = new ArrayList<>();
    private Context objectsContext;

    public ObjectsRecyclerViewAdapter(Context objectsContext) {
        this.objectsContext = objectsContext;
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
        holder.categoryName.setText(objects.get(position).getName());
        Glide.with(objectsContext).asBitmap().load(objects.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch(objects.get(position).getId()) {
                    case 182: intent = new Intent(objectsContext, HomeActivity.class); break;
                    case 183: intent = new Intent(objectsContext, OutsideActivity.class); break;
                    case 184: intent = new Intent(objectsContext, ClassActivity.class); break;
                    case 185: intent = new Intent(objectsContext, ShopActivity.class); break;
                    case 186: intent = new Intent(objectsContext, CityActivity.class); break;
                    default: throw new NullPointerException("Invalid Selection");
                }
                objectsContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void setObjects(ArrayList<Category> objects) {
        this.objects = objects;
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
