package com.project.ohl.presentation;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.Preference;
import java.util.Objects;
import com.project.ohl.R;
import com.project.ohl.presentation.user.LoginActivity;
import com.project.ohl.domain.repository.AuthenticationException;

import static android.R.id.home;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        findViewById(R.id.settings).setVisibility(View.GONE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            Preference logout = Objects.requireNonNull(findPreference("logout"));
            logout.setOnPreferenceClickListener(preference -> {
                preference.setEnabled(false);
                AuthenticationViewModel.of(requireActivity()).load(requireActivity(), repository -> repository.logout(), result -> {
                    startActivity(new Intent(requireContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    requireActivity().finish();
                });
                return true;
            });
        }
    }

    @Override protected void onResume() {
        super.onResume();
        findViewById(R.id.settings).setVisibility(View.GONE);
        AuthenticationViewModel.of(this).load(this, repository -> repository.current(), result -> {
            if (result.isSuccess()) {
                if (getSupportFragmentManager().findFragmentById(R.id.settings) == null)
                    getSupportFragmentManager().beginTransaction().replace(R.id.settings, new SettingsFragment()).commit();
                findViewById(R.id.settings).setVisibility(View.VISIBLE);
            } else {
                if (result.error() instanceof AuthenticationException failure && failure.status() == 401)
                    startActivity(new Intent(this, LoginActivity.class));
                else Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == home) getOnBackPressedDispatcher().onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
