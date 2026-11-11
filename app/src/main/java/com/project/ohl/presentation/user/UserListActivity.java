package com.project.ohl.presentation.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.project.ohl.R;

import java.util.Objects;

public class UserListActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_list_user);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      initializeViews();


   }

   private void initializeViews() {
   }
}