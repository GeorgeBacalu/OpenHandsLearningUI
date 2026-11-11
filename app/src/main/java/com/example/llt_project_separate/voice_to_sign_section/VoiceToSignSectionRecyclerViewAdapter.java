package com.example.llt_project_separate.voice_to_sign_section;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.general.standard_classes.TextSignPair;

import java.util.ArrayList;
import java.util.List;

public class VoiceToSignSectionRecyclerViewAdapter extends RecyclerView.Adapter<VoiceToSignSectionRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "VoiceToSignAdapter";
    private List<TextSignPair> textSignPairs = new ArrayList<>();
    private final Context voiceToSignContext;

    public VoiceToSignSectionRecyclerViewAdapter(Context voiceToSignContext) { this.voiceToSignContext = voiceToSignContext; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_to_sign_matching_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.letter.setText(textSignPairs.get(position).getLetter());
        if(textSignPairs.get(position).getLetter().equals(" ")) {
            holder.separatingLine.setVisibility(View.GONE);
        } else {
            holder.separatingLine.setVisibility(View.VISIBLE);
        }
        Glide.with(voiceToSignContext).asBitmap().load(textSignPairs.get(position).getImageSource()).into(holder.signImage);
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
        private final View separatingLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            signImage = itemView.findViewById(R.id.signImage);
            letter = itemView.findViewById(R.id.letter);
            separatingLine = itemView.findViewById(R.id.separatingLine);
        }
    }
}
