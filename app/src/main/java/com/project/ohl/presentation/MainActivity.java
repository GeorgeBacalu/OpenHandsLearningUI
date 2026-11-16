package com.project.ohl.presentation;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.project.ohl.R;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.repository.AuthenticationException;
import java.util.List;

public class MainActivity extends AuthenticatedActivity {
   private MainRecyclerViewAdapter sectionsAdapter;
   private boolean sessionVerified;
   private final ActivityResultLauncher<String> localNetworkPermission = registerForActivityResult(
           new ActivityResultContracts.RequestPermission(), granted -> {
               if (granted) refreshSession();
               else {
                   Toast.makeText(this, R.string.local_network_required, Toast.LENGTH_LONG).show();
                   showConnectionError();
               }
           });

   @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ro-RO"));
   }

   @Override protected boolean validatesAccountOnResume() { return true; }

   @Override protected void onResume() {
      super.onResume();
      sessionVerified = false;
      setContentView(R.layout.activity_auth_gate);
      findViewById(R.id.auth_retry).setOnClickListener(view -> verifySession());
      verifySession();
   }

   private void verifySession() {
      findViewById(R.id.auth_gate_error).setVisibility(View.GONE);
      findViewById(R.id.auth_retry).setVisibility(View.GONE);
      findViewById(R.id.auth_gate_progress).setVisibility(View.VISIBLE);
      if (LocalApiAccess.allowed(this)) refreshSession();
      else if (Build.VERSION.SDK_INT >= 37) localNetworkPermission.launch(Manifest.permission.ACCESS_LOCAL_NETWORK);
   }

   private void refreshSession() {
      AuthenticationViewModel.of(this).load(this, repository -> repository.current(), result -> {
          if (result.isSuccess() && result.value() != null) {
              sessionVerified = true;
              setContentView(R.layout.activity_main);
              ((TextView) findViewById(R.id.welcomeMessage)).setText(getString(R.string.welcome_user, result.value().getName()));
              ViewCompat.setAccessibilityHeading(findViewById(R.id.home_headline), true);
              ViewCompat.setAccessibilityHeading(findViewById(R.id.home_explore_heading), true);
              Toolbar toolbar = findViewById(R.id.toolbar);
              toolbar.setTitleTextAppearance(this, R.style.HomeToolbarTitle);
              setSupportActionBar(toolbar);
              RecyclerView sections = findViewById(R.id.sectionsRecyclerView);
              sectionsAdapter = new MainRecyclerViewAdapter(this);
              sections.setAdapter(sectionsAdapter);
              sections.setLayoutManager(new LinearLayoutManager(this));
              invalidateOptionsMenu();
              loadSections();
          } else if (result.error() instanceof AuthenticationException failure && failure.status() == 401) {
              redirectToLogin();
          } else showConnectionError();
      });
   }

   private void showConnectionError() {
      findViewById(R.id.auth_gate_progress).setVisibility(View.GONE);
      findViewById(R.id.auth_gate_error).setVisibility(View.VISIBLE);
      findViewById(R.id.auth_retry).setVisibility(View.VISIBLE);
   }

   private void loadSections() {
      LearningViewModel.of(this).<List<Section>>load(this, "getAllSections", repository -> repository.getAllSections(), result -> {
          if (!sessionVerified) return;
          if (!result.isSuccess() || result.value() == null) {
              Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
              return;
          }
          for (Section section : result.value()) {
              String name = section.getName();
          }
          sectionsAdapter.setSections(result.value());
      });
   }

   @Override public boolean onCreateOptionsMenu(Menu menu) {
      if (sessionVerified) getMenuInflater().inflate(R.menu.action_bar_menu, menu);
      return true;
   }

   @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == R.id.navProfileActionBar) {
         if (sessionVerified && UserSession.id() != null) startActivity(new Intent(this, ProfileActivity.class));
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   String getStringResource(int intResource) { return getResources().getString(intResource); }
}
