package com.example.llt_project_separate;

import static com.example.llt_project_separate.CategoryActivity.CATEGORY_ID;
import static com.example.llt_project_separate.CategoryActivity.CATEGORY_NAME;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "CategoryAdapter";
    private ArrayList<Category> categories = new ArrayList<>();
    private Context categoryContext;
    private String parentActivity;

    public CategoryRecyclerViewAdapter(Context categoryContext, String parentActivity) {
        this.categoryContext = categoryContext;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_expandable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.categoryName.setText(categories.get(position).getName());
        Glide.with(categoryContext).asBitmap().load(categories.get(position).getImageSource()).into(holder.categoryImage);
        holder.categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(categoryContext, VideoPlayerActivity.class);
                intent.putExtra(CATEGORY_ID, categories.get(position).getId());
                intent.putExtra(CATEGORY_NAME, categories.get(position).getName());
                categoryContext.startActivity(intent);
            }
        });

        if(categories.get(position).isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.categoryCard, new AutoTransition());
            holder.expendedRelativeLayout.setVisibility(View.VISIBLE);
            holder.arrowButton.setBackgroundResource(R.drawable.ic_arrow_up);
            if(parentActivity.equals("favoriteCategories")) {
                holder.removeFromFavoriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         AlertDialog.Builder builder = new AlertDialog.Builder(categoryContext);
                         builder.setMessage("Eşti sigur că vrei să elimini " + categories.get(position).getName() + " de la favorite?");
                         builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 String chosenToBeRemoved = categories.get(position).getName();
                                 if(Utils.getInstance(categoryContext).removedFromFavorite(categories.get(position))) {
                                     Toast.makeText(categoryContext,  chosenToBeRemoved + " eliminat", Toast.LENGTH_SHORT).show();
                                     notifyDataSetChanged();
                                 } else {
                                     Toast.makeText(categoryContext, "Ceva nu e bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });
                         builder.setNegativeButton("NU", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {

                             }
                         });
                         builder.create().show();
                    }
                });
            }
        } else {
            TransitionManager.beginDelayedTransition(holder.categoryCard, new AutoTransition());
            holder.expendedRelativeLayout.setVisibility(View.GONE);
            holder.arrowButton.setBackgroundResource(R.drawable.ic_arrow_down);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView categoryCard;
        private TextView categoryName;
        private ImageView categoryImage;
        private Button arrowButton, removeFromFavoriteButton;
        private RelativeLayout expendedRelativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryCard = itemView.findViewById(R.id.categoryCard);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);

            arrowButton = itemView.findViewById(R.id.arrowButton);
            removeFromFavoriteButton = itemView.findViewById(R.id.removeFromFavoriteButton);
            expendedRelativeLayout = itemView.findViewById(R.id.expendedRelativeLayout);

            arrowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Category category = categories.get(getBindingAdapterPosition());
                    category.setExpanded(!category.isExpanded());
                    notifyItemChanged(getBindingAdapterPosition());
                }
            });
        }
    }
}
