package com.example.llt_project_separate.voice_to_sign_section;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llt_project_separate.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileViewerAdapter extends RecyclerView.Adapter<FileViewerAdapter.FileViewerViewHolder> implements OnDatabaseChangedListener {
    private Context context;
    private ArrayList<RecordingItem> recordingItems;
    private LinearLayoutManager linearLayoutManager;

    DBHelperRecordings dbHelperRecordings;

    public FileViewerAdapter(Context context, ArrayList<RecordingItem> recordingItems, LinearLayoutManager linearLayoutManager) {
        this.context = context;
        this.recordingItems = recordingItems;
        this.linearLayoutManager= linearLayoutManager;
        dbHelperRecordings = new DBHelperRecordings(context);
        dbHelperRecordings.setOnDatabaseChangedListener(this);
    }

    @NonNull
    @Override
    public FileViewerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recording_item_card_view, parent, false);
        return new FileViewerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewerViewHolder holder, int position) {
        RecordingItem recordingItem = recordingItems.get(position);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(recordingItem.getLength());
        long seconds = TimeUnit.MILLISECONDS.toSeconds(recordingItem.getLength()) - TimeUnit.MINUTES.toSeconds(minutes);
        holder.fileName.setText(recordingItem.getName());
        holder.fileLength.setText(String.format("%02d:%02d", minutes, seconds));
        holder.fileTimeAdded.setText(DateUtils.formatDateTime(context, recordingItem.getTimeAdded(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR));
        holder.fileName.setText(recordingItem.getName());
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

        @BindView(R.id.fileNameText) TextView fileName;
        @BindView(R.id.fileLengthText) TextView fileLength;
        @BindView(R.id.fileTimeAdded) TextView fileTimeAdded;
        @BindView(R.id.cardView) View cardView;
        public FileViewerViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PlaybackFragment playbackFragment = new PlaybackFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("recordingItem", recordingItems.get(getAdapterPosition()));
                    playbackFragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    playbackFragment.show(fragmentTransaction, "dialog_playback");
                }
            });
        }
    }
}
