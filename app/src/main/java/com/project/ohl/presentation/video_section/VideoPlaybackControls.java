package com.project.ohl.presentation.video_section;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import com.google.android.material.button.MaterialButton;
import com.project.ohl.R;
import java.util.Locale;

/** Keeps the accessible, persistent playback controls in sync with the video surface. */
final class VideoPlaybackControls {
    private static final String POSITION = "video.position";
    private static final String PLAYING = "video.playing";
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final VideoView video;
    private final MaterialButton play;
    private final View replay;
    private final SeekBar progress;
    private final TextView elapsed;
    private final TextView duration;
    private final View loading;
    private final View error;
    private boolean prepared;
    private boolean tracking;
    private boolean dragging;
    private boolean completed;
    private boolean playWhenReady;
    private int position;
    private final Runnable ticker = new Runnable() {
        @Override public void run() {
            render();
            if (tracking) handler.postDelayed(this, 250);
        }
    };

    VideoPlaybackControls(View root, Bundle savedState) {
        video = root.findViewById(R.id.videoView);
        play = root.findViewById(R.id.video_play_pause);
        replay = root.findViewById(R.id.video_replay);
        progress = root.findViewById(R.id.video_progress);
        elapsed = root.findViewById(R.id.video_elapsed);
        duration = root.findViewById(R.id.video_duration);
        loading = root.findViewById(R.id.video_loading);
        error = root.findViewById(R.id.video_error);
        position = savedState == null ? 0 : savedState.getInt(POSITION);
        playWhenReady = savedState == null || savedState.getBoolean(PLAYING);
        video.setOnPreparedListener(player -> {
            prepared = true;
            loading.setVisibility(View.GONE);
            progress.setMax(Math.max(1, video.getDuration()));
            duration.setText(time(video.getDuration()));
            enableControls(true);
            video.seekTo(position);
            if (playWhenReady && tracking) video.start();
            render();
        });
        video.setOnCompletionListener(player -> {
            completed = true;
            playWhenReady = false;
            render();
        });
        video.setOnErrorListener((player, what, extra) -> {
            showError();
            return true;
        });
        play.setOnClickListener(view -> {
            if (!prepared) return;
            if (video.isPlaying()) {
                video.pause();
                playWhenReady = false;
            } else {
                if (completed) video.seekTo(0);
                completed = false;
                playWhenReady = true;
                video.start();
            }
            render();
        });
        replay.setOnClickListener(view -> {
            completed = false;
            video.seekTo(0);
            playWhenReady = true;
            video.start();
            render();
        });
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar bar, int value, boolean fromUser) {
                if (!fromUser || !prepared) return;
                elapsed.setText(time(value));
                // Keyboard and accessibility actions do not call the touch callbacks.
                if (!dragging) seek(value);
            }
            @Override public void onStartTrackingTouch(SeekBar bar) { dragging = true; }
            @Override public void onStopTrackingTouch(SeekBar bar) {
                dragging = false;
                if (prepared) seek(bar.getProgress());
            }
        });
    }

    void showLoading() {
        prepared = false;
        completed = false;
        video.stopPlayback();
        enableControls(false);
        error.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    void load(String url) {
        if (url == null || !url.startsWith("https://")) { showError(); return; }
        video.setVisibility(View.VISIBLE);
        video.setVideoPath(url);
    }

    void showError() {
        prepared = false;
        video.stopPlayback();
        video.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
        enableControls(false);
    }

    void startTracking() {
        tracking = true;
        if (prepared && playWhenReady && !completed) video.start();
        handler.post(ticker);
    }

    void pause() {
        tracking = false;
        handler.removeCallbacks(ticker);
        if (prepared) {
            position = video.getCurrentPosition();
            playWhenReady = video.isPlaying();
        }
        video.pause();
        render();
    }

    void saveState(Bundle state) {
        state.putInt(POSITION, prepared ? video.getCurrentPosition() : position);
        state.putBoolean(PLAYING, prepared && tracking ? video.isPlaying() : playWhenReady);
    }

    void release() {
        tracking = false;
        handler.removeCallbacks(ticker);
        video.stopPlayback();
    }

    private void seek(int value) {
        completed = false;
        position = value;
        video.seekTo(value);
    }

    private void enableControls(boolean enabled) {
        play.setEnabled(enabled);
        replay.setEnabled(enabled);
        progress.setEnabled(enabled);
    }

    private void render() {
        boolean playing = prepared && video.isPlaying();
        play.setText(playing ? R.string.video_player_pause : R.string.video_player_play);
        play.setIconResource(playing ? R.drawable.ic_media_pause : R.drawable.ic_media_play);
        if (prepared && !dragging) {
            int current = completed ? video.getDuration() : video.getCurrentPosition();
            progress.setProgress(current);
            elapsed.setText(time(current));
        }
    }

    private static String time(int milliseconds) {
        int seconds = Math.max(0, milliseconds / 1000);
        return String.format(Locale.ROOT, "%d:%02d", seconds / 60, seconds % 60);
    }
}
