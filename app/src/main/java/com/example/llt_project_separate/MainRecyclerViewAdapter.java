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
import com.example.llt_project_separate.expressions_section.ExpressionsActivity;
import com.example.llt_project_separate.general.standard_classes.Section;
import com.example.llt_project_separate.text_to_sign_section.TextToSignSectionActivity;
import com.example.llt_project_separate.video_section.VideoSectionActivity;
import com.example.llt_project_separate.voice_to_sign_section.VoiceToSignSectionActivity;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "SectionsAdapter";
    private List<Section> sections = new ArrayList<>();
    private final Context context;

    public MainRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.sectionName.setText(sections.get(position).getName());
        Glide.with(context).asBitmap().load(sections.get(position).getImageSource()).into(holder.sectionImage);
        Glide.with(context).asBitmap().load(sections.get(position).getIcon()).into(holder.sectionIcon);
        holder.sectionCard.setOnClickListener(v -> {
            Intent intent;
            switch(sections.get(position).getId()) {
                case 10001: intent = new Intent(context, VideoSectionActivity.class); break;
                case 10002: intent = new Intent(context, TextToSignSectionActivity.class); break;
                case 10003: intent = new Intent(context, VoiceToSignSectionActivity.class); break;
                case 10004: intent = new Intent(context, ExpressionsActivity.class); break;
                default: throw new NullPointerException("Invalid Selection");
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSections(List<Section> sections) {
        this.sections = sections;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView sectionCard;
        private final ImageView sectionImage, sectionIcon;
        private final TextView sectionName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionCard = itemView.findViewById(R.id.sectionCard);
            sectionImage = itemView.findViewById(R.id.sectionImage);
            sectionName = itemView.findViewById(R.id.sectionName);
            sectionIcon = itemView.findViewById(R.id.sectionIcon);
        }
    }
}
