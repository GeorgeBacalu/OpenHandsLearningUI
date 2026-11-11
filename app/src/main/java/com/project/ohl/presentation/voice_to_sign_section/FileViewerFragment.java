package com.project.ohl.presentation.voice_to_sign_section;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.ohl.databinding.FragmentFileViewerBinding;

import java.util.ArrayList;

public class FileViewerFragment extends Fragment {

    private FragmentFileViewerBinding binding;
    private FileViewerAdapter fileViewerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFileViewerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recordingsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        binding.recordingsRecyclerView.setLayoutManager(linearLayoutManager);

        fileViewerAdapter = new FileViewerAdapter(requireActivity(), new ArrayList<>());
        binding.recordingsRecyclerView.setAdapter(fileViewerAdapter);
        new androidx.lifecycle.ViewModelProvider(this).get(com.project.ohl.presentation.RecordingsViewModel.class)
                .recordings().observe(getViewLifecycleOwner(), items -> fileViewerAdapter.setRecordings(items));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.recordingsRecyclerView.setAdapter(null);
        fileViewerAdapter = null;
        binding = null;
    }
}
