package com.project.ohl.presentation;

import android.annotation.SuppressLint;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import com.project.ohl.R;
import com.project.ohl.domain.model.Section;
import com.project.ohl.presentation.user.UserActivity;
import com.project.ohl.presentation.user.LoginActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   private RecyclerView sectionsRecyclerView;
   private MainRecyclerViewAdapter sectionsAdapter;

   private DrawerLayout drawerLayout;
   private NavigationView navigationView;
   private ActionBarDrawerToggle toggle;
   private Toolbar toolbar;
   private boolean sessionVerified;
   private final ActivityResultLauncher<String> localNetworkPermission = registerForActivityResult(
           new ActivityResultContracts.RequestPermission(), granted -> {
               if (granted) { loadSections(); refreshSession(); }
               else Toast.makeText(this, R.string.local_network_required, Toast.LENGTH_LONG).show();
           });


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      initializeViews();

      sectionsRecyclerView.setAdapter(sectionsAdapter);
      sectionsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

      toolbar = findViewById(R.id.toolbar);
      toolbar.setTitleTextAppearance(this, R.style.HomeToolbarTitle);
      setSupportActionBar(toolbar);

      drawerLayout = findViewById(R.id.drawerLayout);


      toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

      drawerLayout.addDrawerListener(toggle);
      toggle.syncState();

      navigationView = findViewById(R.id.navigationView);
      navigationView.setNavigationItemSelectedListener(this);
      if (LocalApiAccess.allowed(this)) loadSections();
      else if (Build.VERSION.SDK_INT >= 37) localNetworkPermission.launch(Manifest.permission.ACCESS_LOCAL_NETWORK);
   }

   private void loadSections() {
      LearningViewModel.of(this).<List<Section>>load(this, "getAllSections", repository -> repository.getAllSections(), result -> {
          if (!result.isSuccess() || result.value() == null) {
              Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
              return;
          }
          for (Section section : result.value()) {
              String name = section.getName();
              section.setIcon(MediaResources.drawable(this, section.getIconKey() == null ? "ic_" + name : section.getIconKey()));
              section.setImageSource(MediaResources.drawable(this, section.getMediaKey() == null ? name : section.getMediaKey()));
          }
          sectionsAdapter.setSections(result.value());
      });
   }

   private void initializeViews() {
      sectionsRecyclerView = findViewById(R.id.sectionsRecyclerView);
      sectionsAdapter = new MainRecyclerViewAdapter(this);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater menuInflater = getMenuInflater();
      menuInflater.inflate(R.menu.action_bar_menu, menu);
      menu.findItem(R.id.navRegisterActionBar).getActionView().findViewById(R.id.button_register)
              .setOnClickListener(view -> startActivity(new Intent(this, UserActivity.class)));
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onPrepareOptionsMenu(Menu menu) {
      boolean signedIn = sessionVerified && UserSession.id() != null;
      menu.findItem(R.id.navRegisterActionBar).setVisible(!signedIn);
      menu.findItem(R.id.navSettingsActionBar).setVisible(signedIn);
      navigationView.getMenu().findItem(R.id.navSettings).setVisible(signedIn);
      navigationView.getMenu().findItem(R.id.navLogin).setVisible(!signedIn);
      return super.onPrepareOptionsMenu(menu);
   }

   @Override
   protected void onResume() {
      super.onResume();
      sessionVerified = false;
      invalidateOptionsMenu();
      if (LocalApiAccess.allowed(this)) refreshSession();
   }

   private void refreshSession() {
      AuthenticationViewModel.of(this).load(this, repository -> repository.current(), result -> {
          sessionVerified = result.isSuccess() && result.value() != null;
          invalidateOptionsMenu();
      });
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == R.id.navSettingsActionBar) {
         if (!sessionVerified || UserSession.id() == null) return true;
         Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
         startActivity(intent);
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @SuppressLint("NonConstantResourceId")
   @Override
   public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == R.id.navLogin) startActivity(new Intent(this, LoginActivity.class));
      if (item.getItemId() == R.id.navSettings && sessionVerified && UserSession.id() != null)
          startActivity(new Intent(this, SettingsActivity.class));
      drawerLayout.closeDrawers();
      return true;
   }

   String getStringResource(int intResource) {
      return getResources().getString(intResource);
   }
}
