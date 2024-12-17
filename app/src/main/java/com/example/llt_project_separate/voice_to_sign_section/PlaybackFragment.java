package com.example.llt_project_separate.voice_to_sign_section;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.llt_project_separate.R;
import com.example.llt_project_separate.databinding.FragmentPlaybackBinding;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PlaybackFragment extends DialogFragment {

    private FragmentPlaybackBinding binding;
    private RecordingItem recordingItem;
    private Handler handler = new Handler();
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    long minutes = 0;
    long seconds = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recordingItem = (RecordingItem) getArguments().getSerializable("recordingItem");
        minutes = TimeUnit.MILLISECONDS.toMinutes(recordingItem.getLength());
        seconds = TimeUnit.MILLISECONDS.toSeconds(recordingItem.getLength()) - TimeUnit.MINUTES.toSeconds(minutes);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        binding = FragmentPlaybackBinding.inflate(getLayoutInflater());

        setSeekbarValues();

        binding.fabPlay.setOnClickListener(view -> {
            try {
                onPlay(isPlaying);
            } catch (IOException e) {
                e.printStackTrace();
            }
            isPlaying = !isPlaying;
        });

        binding.fileNameTextView.setText(recordingItem.getName());
        binding.fileLengthTextView.setText(String.format("%02d:%02d", minutes, seconds));

        builder.setView(binding.getRoot());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return builder.create();
    }

    private void onPlay(boolean isPlaying) throws IOException {
        if (!isPlaying) {
            if (mediaPlayer == null) {
                startPlaying();
            }
        } else {
            pausePlaying();
        }
    }

    private void startPlaying() throws IOException {
        binding.fabPlay.setImageResource(R.drawable.ic_media_pause);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(recordingItem.getPath());
        mediaPlayer.prepare();
        binding.seekbar.setMax(mediaPlayer.getDuration());

        mediaPlayer.setOnPreparedListener(MediaPlayer::start);

        mediaPlayer.setOnCompletionListener(mediaPlayer -> stopPlaying());

        updateSeekBar();
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void pausePlaying() {
        binding.fabPlay.setImageResource(R.drawable.ic_media_play);
        handler.removeCallbacks(runnable);
        mediaPlayer.pause();
    }

    private void setSeekbarValues() {
        ColorFilter colorFilter = new LightingColorFilter(
                getResources().getColor(R.color.light_blue),
                getResources().getColor(R.color.light_blue)
        );
        binding.seekbar.getProgressDrawable().setColorFilter(colorFilter);
        binding.seekbar.getThumb().setColorFilter(colorFilter);

        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                    handler.removeCallbacks(runnable);

                    long minutes = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition());
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(minutes);
                    binding.currentProgressTextView.setText(String.format("%02d:%02d", minutes, seconds));
                    updateSeekBar();
                } else if (mediaPlayer == null && fromUser) {
                    try {
                        prepareMediaPlayerFromPoint(progress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    updateSeekBar();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void prepareMediaPlayerFromPoint(int progress) throws IOException {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(recordingItem.getPath());
        mediaPlayer.prepare();
        binding.seekbar.setMax(mediaPlayer.getDuration());
        mediaPlayer.seekTo(progress);
        mediaPlayer.setOnCompletionListener(mediaPlayer -> stopPlaying());
    }

    private void stopPlaying() {
        binding.fabPlay.setImageResource(R.drawable.ic_media_play);
        handler.removeCallbacks(runnable);
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        binding.seekbar.setProgress(binding.seekbar.getMax());
        isPlaying = !isPlaying;
        binding.currentProgressTextView.setText(binding.fileLengthTextView.getText());
        binding.seekbar.setProgress(binding.seekbar.getMax());
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                binding.seekbar.setProgress(currentPosition);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(currentPosition);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(currentPosition) - TimeUnit.MINUTES.toSeconds(minutes);
                binding.currentProgressTextView.setText(String.format("%02d:%02d", minutes, seconds));
                updateSeekBar();
            }
        }
    };

    private void updateSeekBar() {
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }
}