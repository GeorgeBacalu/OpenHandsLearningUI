package com.project.ohl.presentation.voice_to_sign_section;

import com.project.ohl.domain.model.RecordingItem;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ohl.databinding.RecordingItemCardViewBinding;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FileViewerAdapter extends RecyclerView.Adapter<FileViewerAdapter.FileViewerViewHolder> {
    private Context context;
    private ArrayList<RecordingItem> recordingItems;


    public FileViewerAdapter(Context context, ArrayList<RecordingItem> recordingItems) {
        this.context = context;
        this.recordingItems = recordingItems;
    }

    @NonNull
    @Override
    public FileViewerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecordingItemCardViewBinding binding = RecordingItemCardViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FileViewerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewerViewHolder holder, int position) {
        RecordingItem recordingItem = recordingItems.get(position);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(recordingItem.getLength());
        long seconds = TimeUnit.MILLISECONDS.toSeconds(recordingItem.getLength()) - TimeUnit.MINUTES.toSeconds(minutes);
        holder.binding.fileNameText.setText(recordingItem.getName());
        holder.binding.fileLengthText.setText(String.format(java.util.Locale.getDefault(), "%02d:%02d", minutes, seconds));
        holder.binding.fileTimeAdded.setText(DateUtils.formatDateTime(context, recordingItem.getTimeAdded(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR));
    }

    @Override
    public int getItemCount() {
        return recordingItems.size();
    }

    @android.annotation.SuppressLint("NotifyDataSetChanged")
    public void setRecordings(java.util.List<RecordingItem> items) {
        recordingItems = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public class FileViewerViewHolder extends RecyclerView.ViewHolder {
        final RecordingItemCardViewBinding binding;

        public FileViewerViewHolder(@NonNull RecordingItemCardViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.cardView.setOnClickListener(view -> {
                int position = getBindingAdapterPosition();
                if (position == RecyclerView.NO_POSITION) return;

                PlaybackFragment playbackFragment = new PlaybackFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("recordingItem", recordingItems.get(position));
                playbackFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                playbackFragment.show(fragmentTransaction, "dialog_playback");
            });
        }
    }
}
