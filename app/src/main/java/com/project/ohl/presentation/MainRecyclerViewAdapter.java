package com.project.ohl.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.project.ohl.R;
import com.project.ohl.domain.model.Section;
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
        holder.sectionName.setText(sections.get(position).getName());
        Glide.with(context).asBitmap().load(sections.get(position).getImageSource()).into(holder.sectionImage);
        Glide.with(context).asBitmap().load(sections.get(position).getIcon()).into(holder.sectionIcon);
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
