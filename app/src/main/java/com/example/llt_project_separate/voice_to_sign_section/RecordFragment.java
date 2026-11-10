package com.example.llt_project_separate.voice_to_sign_section;

import androidx.fragment.app.Fragment;

public class RecordFragment extends Fragment {

    /*
    @BindView(R.id.chronometer) Chronometer chronometer;
    @BindView(R.id.recordingStatusText) TextView recordingStatusText;
    @BindView(R.id.recordingButton) FloatingActionButton recordingButton;
    @BindView(R.id.pauseButton) Button pauseButton;

    private boolean hasRecordingStarted = true;
    private boolean wasRecordingPaused = true;
    long timeWhenPaused = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View recordView = inflater.inflate(R.layout.fragment_record, container, false);
        ButterKnife.bind(this, recordView);
        return recordView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pauseButton.setVisibility(View.GONE);
        recordingButton.setColorPressed(getResources().getColor(R.color.light_blue));
    }

    @OnClick(R.id.recordingButton)
    public void recordAudio() {
        System.out.println("record");
        onRecord(hasRecordingStarted);
        hasRecordingStarted = !hasRecordingStarted;
    }

    private void onRecord(boolean start) {
        Intent intent = new Intent(getActivity(), RecordingService.class);
        if(start) {
            recordingButton.setImageResource(R.drawable.ic_media_stop);
            Toast.makeText(getContext(), "Inregistrare pornita", Toast.LENGTH_SHORT).show();
            File folder = new File(Environment.getExternalStorageDirectory() + "/MySoundRec");
            if(!folder.exists()) folder.mkdir();
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            getActivity().startService(intent);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            recordingStatusText.setText("Se inregistreaza...");
        } else {
            recordingButton.setImageResource(R.drawable.ic_mic_white);
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            timeWhenPaused = 0;
            recordingStatusText.setText("Apasa butonul pentru a porni inregistrarea");
            getActivity().stopService(intent);
        }
    }
     */
}
