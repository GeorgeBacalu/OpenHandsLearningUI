package com.project.ohl.presentation;

import com.project.ohl.domain.model.CatalogIds;
import com.project.ohl.R;

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
import com.project.ohl.presentation.expressions_section.ExpressionsActivity;
import com.project.ohl.domain.model.Section;
import com.project.ohl.presentation.text_to_sign_section.TextToSignSectionActivity;
import com.project.ohl.presentation.video_section.VideoSectionActivity;
import com.project.ohl.presentation.voice_to_sign_section.VoiceToSignSectionActivity;

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
            if (CatalogIds.SECTION_VIDEO.equals(sections.get(position).getId())) intent = new Intent(context, VideoSectionActivity.class);
            else if (CatalogIds.SECTION_TEXT_TO_SIGN.equals(sections.get(position).getId())) intent = new Intent(context, TextToSignSectionActivity.class);
            else if (CatalogIds.SECTION_VOICE_TO_SIGN.equals(sections.get(position).getId())) intent = new Intent(context, VoiceToSignSectionActivity.class);
            else if (CatalogIds.SECTION_EXPRESSIONS.equals(sections.get(position).getId())) intent = new Intent(context, ExpressionsActivity.class);
            else throw new IllegalArgumentException("Unknown catalog entry");
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
