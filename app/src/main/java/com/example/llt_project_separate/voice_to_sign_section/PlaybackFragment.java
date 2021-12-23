package com.example.llt_project_separate.voice_to_sign_section;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.llt_project_separate.R;
import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaybackFragment extends DialogFragment {

    private RecordingItem recordingItem;
    private Handler handler = new Handler();
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    long minutes = 0;
    long seconds = 0;

    @BindView(R.id.fileNameTextView) TextView fileNameTextView;
    @BindView(R.id.fileLengthTextView) TextView fileLengthTextView;
    @BindView(R.id.currentProgressTextView) TextView fileCurrentProgress;
    @BindView(R.id.seekbar) SeekBar seekbar;
    @BindView(R.id.fabPlay) FloatingActionButton playButton;
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
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_playback, null);
        ButterKnife.bind(this, view);

        setSeekbarValues();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onPlay(isPlaying);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isPlaying = !isPlaying;
            }
        });

        fileNameTextView.setText(recordingItem.getName());
        fileLengthTextView.setText(String.format("%02d:%02d", minutes, seconds));

        builder.setView(view);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return builder.create();
    }

    private void onPlay(boolean isPlaying) throws IOException {
        if(!isPlaying) {
            if(mediaPlayer == null) {
                startPlaying();
            }
        } else {
            pausePlaying();
        }
    }

    private void startPlaying() throws IOException {
        playButton.setImageResource(R.drawable.ic_media_pause);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(recordingItem.getPath());
        mediaPlayer.prepare();
        seekbar.setMax(mediaPlayer.getDuration());

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopPlaying();
            }
        });

        updateSeekBar();
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void pausePlaying() {
        playButton.setImageResource(R.drawable.ic_media_play);
        handler.removeCallbacks(runnable);
        mediaPlayer.pause();
    }

    private void setSeekbarValues() {
        ColorFilter colorFilter = new LightingColorFilter(getResources().getColor(R.color.light_blue), getResources().getColor(R.color.light_blue));
        seekbar.getProgressDrawable().setColorFilter(colorFilter);
        seekbar.getThumb().setColorFilter(colorFilter);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if(mediaPlayer != null && fromUser) {
                   mediaPlayer.seekTo(progress);
                   handler.removeCallbacks(runnable);

                   long minutes = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition());
                   long seconds = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(minutes);
                    fileCurrentProgress.setText(String.format("%02d:%02d", minutes, seconds));
                    updateSeekBar();
               } else if(mediaPlayer == null && fromUser) {
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
        seekbar.setMax(mediaPlayer.getDuration());
        mediaPlayer.seekTo(progress);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stopPlaying();
            }
        });
    }

    private void stopPlaying() {
        playButton.setImageResource(R.drawable.ic_media_play);
        handler.removeCallbacks(runnable);
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        seekbar.setProgress(seekbar.getMax());
        isPlaying = !isPlaying;
        fileCurrentProgress.setText(fileLengthTextView.getText());
        seekbar.setProgress(seekbar.getMax());
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(mediaPlayer != null) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                seekbar.setProgress(currentPosition);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition());
                long seconds = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(minutes);
                fileCurrentProgress.setText(String.format("%02d:%02d", minutes, seconds));
                updateSeekBar();
            }
        }
    };

    private void updateSeekBar() {
        handler.postDelayed(runnable, 1000);
    }
}
