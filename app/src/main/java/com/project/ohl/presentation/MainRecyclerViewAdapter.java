package com.project.ohl.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import com.google.android.material.card.MaterialCardView;
import androidx.recyclerview.widget.RecyclerView;
import com.project.ohl.R;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.model.SectionType;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        Section section = sections.get(position);
        boolean video = section.getType() == SectionType.VIDEO;
        holder.sectionName.setText(section.getName());
        holder.description.setText(video ? R.string.home_video_description : R.string.home_voice_description);
        holder.action.setText(video ? R.string.home_video_action : R.string.home_voice_action);
        int accent = context.getColor(video ? R.color.home_video_accent : R.color.home_voice_accent);
        holder.action.setTextColor(accent);
        holder.sectionIcon.setImageTintList(ColorStateList.valueOf(accent));
        holder.artwork.setCardBackgroundColor(context.getColor(video ? R.color.home_video_art : R.color.home_voice_art));
        holder.sectionCard.setContentDescription(context.getString(R.string.home_section_accessibility,
                section.getName(), holder.description.getText(), holder.action.getText()));
        MediaResources.loadImage(context, section.getImageUrl(), holder.sectionImage);
        holder.sectionCard.setOnClickListener(v -> {
            int current = holder.getBindingAdapterPosition();
            if (current == RecyclerView.NO_POSITION) return;
            context.startActivity(SectionNavigation.intent(context, sections.get(current)));
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
        private final MaterialCardView artwork;
        private final ImageView sectionImage, sectionIcon;
        private final TextView sectionName, description, action;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionCard = itemView.findViewById(R.id.sectionCard);
            artwork = itemView.findViewById(R.id.sectionArtwork);
            sectionImage = itemView.findViewById(R.id.sectionImage);
            sectionName = itemView.findViewById(R.id.sectionName);
            sectionIcon = itemView.findViewById(R.id.sectionIcon);
            description = itemView.findViewById(R.id.sectionDescription);
            action = itemView.findViewById(R.id.sectionAction);
        }
    }
}
