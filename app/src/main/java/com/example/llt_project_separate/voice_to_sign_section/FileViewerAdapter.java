package com.example.llt_project_separate.voice_to_sign_section;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llt_project_separate.databinding.RecordingItemCardViewBinding;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FileViewerAdapter extends RecyclerView.Adapter<FileViewerAdapter.FileViewerViewHolder> implements OnDatabaseChangedListener {
    private Context context;
    private ArrayList<RecordingItem> recordingItems;
    private LinearLayoutManager linearLayoutManager;

    DBHelperRecordings dbHelperRecordings;

    public FileViewerAdapter(Context context, ArrayList<RecordingItem> recordingItems, LinearLayoutManager linearLayoutManager) {
        this.context = context;
        this.recordingItems = recordingItems;
        this.linearLayoutManager = linearLayoutManager;
        dbHelperRecordings = new DBHelperRecordings(context);
        dbHelperRecordings.setOnDatabaseChangedListener(this);
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
        holder.binding.fileLengthText.setText(String.format("%02d:%02d", minutes, seconds));
        holder.binding.fileTimeAdded.setText(DateUtils.formatDateTime(context, recordingItem.getTimeAdded(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR));
    }

    @Override
    public int getItemCount() {
        return recordingItems.size();
    }

    @Override
    public void onNewDatabaseEntryAdded(RecordingItem recordingItem) {
        recordingItems.add(recordingItem);
        notifyItemInserted(recordingItems.size() - 1);
    }

    public class FileViewerViewHolder extends RecyclerView.ViewHolder {
        final RecordingItemCardViewBinding binding;

        public FileViewerViewHolder(@NonNull RecordingItemCardViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // Set click listener for the card view
            binding.cardView.setOnClickListener(view -> {
                PlaybackFragment playbackFragment = new PlaybackFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("recordingItem", recordingItems.get(getAdapterPosition()));
                playbackFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                playbackFragment.show(fragmentTransaction, "dialog_playback");
            });
        }
    }
}