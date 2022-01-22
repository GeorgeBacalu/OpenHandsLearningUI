package com.example.llt_project_separate.voice_to_sign_section;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llt_project_separate.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileViewerFragment extends Fragment {

    @BindView(R.id.recordingsRecyclerView) RecyclerView recyclerView;
    DBHelperRecordings dbHelperRecordings;
    private FileViewerAdapter fileViewerAdapter;
    ArrayList<RecordingItem> recordingItems = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_viewer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelperRecordings = new DBHelperRecordings(getContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        recordingItems = dbHelperRecordings.getAllAudios();
        if(recordingItems == null) {
            Toast.makeText(getContext(), "Nu exista fisiere audio", Toast.LENGTH_SHORT).show();
        } else {
            fileViewerAdapter = new FileViewerAdapter(getActivity(), recordingItems, linearLayoutManager);
            recyclerView.setAdapter(fileViewerAdapter);
        }
    }
}
