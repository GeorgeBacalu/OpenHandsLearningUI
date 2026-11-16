package com.project.ohl.presentation.voice_to_sign_section;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.project.ohl.R;
import com.project.ohl.presentation.MediaResources;
import com.project.ohl.domain.model.TextSignPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VoiceToSignSectionRecyclerViewAdapter extends RecyclerView.Adapter<VoiceToSignSectionRecyclerViewAdapter.ViewHolder> {
    static final int WORD_BREAK = 1;
    private List<TextSignPair> textSignPairs = new ArrayList<>();
    private final Context voiceToSignContext;

    public VoiceToSignSectionRecyclerViewAdapter(Context voiceToSignContext) { this.voiceToSignContext = voiceToSignContext; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = viewType == WORD_BREAK ? R.layout.sign_word_break : R.layout.text_to_sign_matching_items;
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) == WORD_BREAK) return;
        var pair = textSignPairs.get(position);
        holder.letter.setText(pair.getLetter().toUpperCase(Locale.forLanguageTag("ro-RO")));
        holder.itemView.setContentDescription(voiceToSignContext.getString(R.string.sign_for_letter, pair.getLetter()));
        MediaResources.loadImage(voiceToSignContext, MediaResources.voiceUrl(pair.getMediaKey()), holder.signImage);
    }

    @Override
    public int getItemViewType(int position) {
        return "space".equals(textSignPairs.get(position).getMediaKey()) ? WORD_BREAK : 0;
    }

    @Override
    public int getItemCount() {
        return textSignPairs.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTextSignPairs(List<TextSignPair> textSignPairs) {
        this.textSignPairs = textSignPairs;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView signImage;
        private final TextView letter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            signImage = itemView.findViewById(R.id.signImage);
            letter = itemView.findViewById(R.id.letter);
        }
    }
}
