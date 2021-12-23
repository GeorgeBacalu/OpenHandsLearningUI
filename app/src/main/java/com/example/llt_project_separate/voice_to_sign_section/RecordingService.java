package com.example.llt_project_separate.voice_to_sign_section;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;

public class RecordingService extends Service {
    MediaRecorder mediaRecorder;
    long startingTimeMillis = 0;
    long elapsedMillis = 0;

    File file;
    String fileName;
    DBHelperRecordings dbHelperRecordings;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelperRecordings = new DBHelperRecordings(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }

    private void startRecording() {
        Long timestampLong = System.currentTimeMillis() / 1000;
        String timestamp = timestampLong.toString();
        fileName = "audio_" + timestamp;
        file = new File(Environment.getExternalStorageDirectory() + "/MySoundRec/" + fileName + ".mp3");
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(file.getAbsolutePath());
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setAudioChannels(1);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            startingTimeMillis = System.currentTimeMillis();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        mediaRecorder.stop();
        elapsedMillis = (System.currentTimeMillis() - startingTimeMillis);
        mediaRecorder.release();
        Toast.makeText(getApplicationContext(), "Inregistrare salvata" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();

        RecordingItem recordingItem = new RecordingItem(fileName, file.getAbsolutePath(), elapsedMillis, System.currentTimeMillis());
        dbHelperRecordings.addRecording(recordingItem);
    }

    @Override
    public void onDestroy() {
        if(mediaRecorder != null) stopRecording();
        super.onDestroy();
    }
}
