package com.example.llt_project_separate.general.on_boarding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.llt_project_separate.R;

public class IntroductoryActivity extends AppCompatActivity {

    /*
    private ImageView logo, backgroundImage;
    private TextView appName;
    private LottieAnimationView lottieAnimationView;

    private static final int NUMBER_OF_PAGES = 4;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_introductory);

        /*
        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.appName);
        backgroundImage = findViewById(R.id.backgroundImage);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);

        //viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        backgroundImage.animate().translationY(-2500).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1800).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(1500).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1500).setDuration(1000).setStartDelay(4000);
         */
    }

    /*
    private static class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0: return new OnBoardingFragment1();
                case 1: return new OnBoardingFragment2();
                case 2: return new OnBoardingFragment3();
                case 3: return new OnBoardingFragment4();
            }
            return null;
        }

        @Override
        public int getCount() { return NUMBER_OF_PAGES; }
    }
     */
}