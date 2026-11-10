package com.example.llt_project_separate.voice_to_sign_section;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.llt_project_separate.databinding.FragmentFileViewerBinding;

import java.util.ArrayList;

public class FileViewerFragment extends Fragment {

    private FragmentFileViewerBinding binding;
    DBHelperRecordings dbHelperRecordings;
    private FileViewerAdapter fileViewerAdapter;
    ArrayList<RecordingItem> recordingItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFileViewerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelperRecordings = new DBHelperRecordings(getContext());
        binding.recordingsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        binding.recordingsRecyclerView.setLayoutManager(linearLayoutManager);

        recordingItems = dbHelperRecordings.getAllAudios();
        if (recordingItems == null || recordingItems.isEmpty()) {
            Toast.makeText(getContext(), "Nu exista fisiere audio", Toast.LENGTH_SHORT).show();
        } else {
            fileViewerAdapter = new FileViewerAdapter(requireActivity(), recordingItems, linearLayoutManager);
            binding.recordingsRecyclerView.setAdapter(fileViewerAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Release the binding reference to avoid memory leaks
        binding = null;
    }
}
