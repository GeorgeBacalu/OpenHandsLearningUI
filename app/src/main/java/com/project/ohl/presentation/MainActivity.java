package com.project.ohl.presentation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
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
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   private RecyclerView sectionsRecyclerView;
   private MainRecyclerViewAdapter sectionsAdapter;

   private DrawerLayout drawerLayout;
   private NavigationView navigationView;
   private ActionBarDrawerToggle toggle;
   private Toolbar toolbar;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      initializeViews();

      sectionsRecyclerView.setAdapter(sectionsAdapter);
      sectionsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

      LearningViewModel.of(this).<List<Section>>load(this, "getAllSections", repository -> repository.getAllSections(), result -> {
            if (result.error() != null) {
            Toast.makeText(MainActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
               List<Section> sections = result.value();
               for (Section section : sections) {
                  String name = section.getName();
                  section.setIcon(MediaResources.drawable(this, section.getIconKey() == null ? "ic_" + name : section.getIconKey()));
                  section.setImageSource(MediaResources.drawable(this, section.getMediaKey() == null ? name : section.getMediaKey()));
               }
               sectionsAdapter.setSections(sections);

            }
        });

      toolbar = findViewById(R.id.toolbar);
      toolbar.setTitleTextAppearance(this, R.style.HomeToolbarTitle);
      setSupportActionBar(toolbar);

      drawerLayout = findViewById(R.id.drawerLayout);


      toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

      drawerLayout.addDrawerListener(toggle);
      toggle.syncState();

      navigationView = findViewById(R.id.navigationView);
      navigationView.setNavigationItemSelectedListener(this);
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
      boolean hasProfile = UserProfiles.id(this) != null;
      menu.findItem(R.id.navRegisterActionBar).setVisible(!hasProfile);
      menu.findItem(R.id.navSettingsActionBar).setVisible(hasProfile);
      return super.onPrepareOptionsMenu(menu);
   }

   @Override
   protected void onResume() {
      super.onResume();
      invalidateOptionsMenu();
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == R.id.navSettingsActionBar) {
         Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
         startActivity(intent);
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @SuppressLint("NonConstantResourceId")
   @Override
   public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      return true;
   }

   String getStringResource(int intResource) {
      return getResources().getString(intResource);
   }
}
