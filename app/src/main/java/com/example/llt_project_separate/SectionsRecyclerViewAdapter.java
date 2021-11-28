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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SectionsRecyclerViewAdapter extends RecyclerView.Adapter<SectionsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "SectionsRecViewAdapter";

    private ArrayList<Section> sections = new ArrayList<>();
    private Context context;

    public SectionsRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_section, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.sectionName.setText(sections.get(position).getName());

        Glide.with(context).asBitmap().load(sections.get(position).getImageSource()).into(holder.sectionImage);
        Glide.with(context).asBitmap().load(sections.get(position).getIcon()).into(holder.sectionIcon);

        holder.sectionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch(sections.get(position).getId()) {
                    case 1: intent = new Intent(context, VideoSectionActivity.class); break;
                    case 2: intent = new Intent(context, TextToSignSectionActivity.class); break;
                    case 3: intent = new Intent(context, VoiceToSignSectionActivity.class); break;
                    case 4: intent = new Intent(context, ExpressionsSectionActivity.class); break;
                    default: throw new NullPointerException("Invalid Selection");
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView sectionCard;
        private ImageView sectionImage, sectionIcon;
        private TextView sectionName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionCard = itemView.findViewById(R.id.sectionCard);
            sectionImage = itemView.findViewById(R.id.sectionImage);
            sectionName = itemView.findViewById(R.id.sectionName);
            sectionIcon = itemView.findViewById(R.id.sectionIcon);
        }
    }
}
