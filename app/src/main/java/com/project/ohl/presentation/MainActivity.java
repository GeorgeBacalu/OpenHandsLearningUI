package com.project.ohl.presentation;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.os.LocaleListCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.ohl.R;
import com.project.ohl.app.AppViewModels;
import com.project.ohl.domain.model.User;
import com.project.ohl.domain.repository.AuthenticationException;
import com.project.ohl.presentation.state.UiState;

public class MainActivity extends AuthenticatedActivity {
    private SessionViewModel model;
    private boolean sessionVerified;
    private final ActivityResultLauncher<String> localNetworkPermission =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    granted -> {
                        if (granted) model.refresh();

                        else {
                            Toast.makeText(this, R.string.local_network_required, Toast.LENGTH_LONG).show();

                            showGate(false);
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ro-RO"));

        showGate(true);

        model = AppViewModels.get(this, SessionViewModel.class);

        model.state().observe(this, this::render);
    }

    @Override
    protected boolean validatesAccountOnResume() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        verifySession();
    }

    private void verifySession() {
        showGate(true);

        if (LocalApiAccess.allowed(this)) model.refresh();

        else if (Build.VERSION.SDK_INT >= 37) localNetworkPermission.launch(Manifest.permission.ACCESS_LOCAL_NETWORK);
    }

    private void showGate(boolean loading) {
        sessionVerified = false;

        setContentView(R.layout.activity_auth_gate);

        findViewById(R.id.auth_retry).setOnClickListener(view -> verifySession());

        findViewById(R.id.auth_gate_progress).setVisibility(loading ? View.VISIBLE : View.GONE);

        findViewById(R.id.auth_gate_error).setVisibility(loading ? View.GONE : View.VISIBLE);

        findViewById(R.id.auth_retry).setVisibility(loading ? View.GONE : View.VISIBLE);

        invalidateOptionsMenu();
    }

    private void render(UiState<User> state) {
        if (state.isLoading()) {
            showGate(true);

            return;
        }

        if (state.error() instanceof AuthenticationException failure && failure.status() == 401) {
            redirectToLogin();

            return;
        }

        if (state.error() != null) {
            showGate(false);

            return;
        }

        if (!state.isSuccess()) return;

        if (state.value() == null) {
            showGate(false);

            return;
        }

        sessionVerified = true;

        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.welcomeMessage))
                .setText(getString(R.string.welcome_user, state.value().getName()));

        ViewCompat.setAccessibilityHeading(findViewById(R.id.home_headline), true);
        ViewCompat.setAccessibilityHeading(findViewById(R.id.home_explore_heading), true);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitleTextAppearance(this, R.style.HomeToolbarTitle);

        setSupportActionBar(toolbar);

        RecyclerView sections = findViewById(R.id.sectionsRecyclerView);

        sections.setAdapter(new MainRecyclerViewAdapter(this));
        sections.setLayoutManager(new LinearLayoutManager(this));

        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (sessionVerified) getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navProfileActionBar) {
            if (sessionVerified && UserSession.id() != null) startActivity(new Intent(this, ProfileActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
