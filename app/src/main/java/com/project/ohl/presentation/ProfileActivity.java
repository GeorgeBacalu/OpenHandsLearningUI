package com.project.ohl.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import com.project.ohl.app.AppViewModels;
import androidx.core.view.ViewCompat;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.project.ohl.R;
import com.project.ohl.app.ThemePreferences;
import com.project.ohl.domain.model.User;
import com.project.ohl.domain.repository.AuthenticationException;
import java.util.Locale;

public class ProfileActivity extends AuthenticatedActivity {
    private ProfileViewModel model;
    private AlertDialog dialog;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        for (int id : new int[]{R.id.profile_display_name, R.id.profile_personal_heading,
                R.id.profile_school_heading, R.id.profile_security_heading, R.id.profile_appearance_heading})
            ViewCompat.setAccessibilityHeading(findViewById(id), true);
        SwitchMaterial themeSwitch = findViewById(R.id.profile_dark_theme);
        themeSwitch.setChecked(ThemePreferences.isDarkTheme(this));
        themeSwitch.setOnCheckedChangeListener((button, checked) -> ThemePreferences.setDarkTheme(this, checked));
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        toolbar.setNavigationOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        model = AppViewModels.get(this, ProfileViewModel.class);
        findViewById(R.id.profile_retry).setOnClickListener(view -> model.load());
        findViewById(R.id.profile_change_password).setOnClickListener(view -> showAccountDialog(false));
        findViewById(R.id.profile_delete_account).setOnClickListener(view -> showAccountDialog(true));
        findViewById(R.id.profile_logout).setOnClickListener(view -> model.submit(ProfileViewModel.Action.LOGOUT, null, null));
        model.state().observe(this, this::render);
        model.load();
    }

    private void render(ProfileViewModel.State state) {
        boolean busy = state.pending() != null;
        findViewById(R.id.profile_progress).setVisibility(state.loading() || busy ? View.VISIBLE : View.GONE);
        findViewById(R.id.profile_content).setVisibility(state.user() == null ? View.GONE : View.VISIBLE);
        findViewById(R.id.profile_load_error).setVisibility(state.error() == null ? View.GONE : View.VISIBLE);
        for (int id : new int[]{R.id.profile_change_password, R.id.profile_delete_account, R.id.profile_logout})
            findViewById(id).setEnabled(!busy);
        if (state.user() != null) bind(state.user());
        if (state.error() instanceof AuthenticationException failure && failure.status() == 401) redirectToLogin();
        if (state.completion() == null) return;
        var completion = state.completion();
        model.acknowledgeCompletion();
        if (completion.result().isSuccess()) {
            if (completion.action() != ProfileViewModel.Action.LOGOUT)
                Toast.makeText(this, completion.action() == ProfileViewModel.Action.DELETE
                        ? R.string.profile_deleted : R.string.profile_password_changed, Toast.LENGTH_LONG).show();
            redirectToLogin();
        } else {
            Throwable error = completion.result().error();
            if (error instanceof AuthenticationException failure && failure.status() == 401) { redirectToLogin(); return; }
            int message = error instanceof AuthenticationException failure && failure.status() == 400
                    ? R.string.profile_incorrect_password
                    : error instanceof AuthenticationException failure && failure.status() == 429
                    ? R.string.login_throttled : R.string.request_failed;
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    private void bind(User user) {
        setText(R.id.profile_display_name, user.getName());
        setText(R.id.profile_name, user.getName());
        setText(R.id.profile_email, user.getEmail());
        setText(R.id.profile_phone, user.getPhone());
        setText(R.id.profile_school, getString(R.string.profile_school_combination,
                display(user.getSchoolName()), display(user.getCityName()), display(user.getCountyName())));
        String name = user.getName() == null ? "" : user.getName().strip();
        String[] parts = name.isEmpty() ? new String[0] : name.split("\\s+");
        String initials = parts.length == 0 ? "?" : first(parts[0]) + (parts.length > 1 ? first(parts[parts.length - 1]) : "");
        setText(R.id.profile_initials, initials.toUpperCase(Locale.forLanguageTag("ro")));
    }

    private static String first(String value) { return new String(Character.toChars(value.codePointAt(0))); }
    private String display(String value) { return value == null || value.isBlank() ? getString(R.string.profile_unavailable) : value; }
    private void setText(int id, String value) { ((TextView) findViewById(id)).setText(display(value)); }

    private void showAccountDialog(boolean deleting) {
        View form = getLayoutInflater().inflate(R.layout.dialog_profile_password, null);
        TextInputEditText current = form.findViewById(R.id.profile_current_password);
        TextInputEditText next = form.findViewById(R.id.profile_new_password);
        TextInputEditText confirmation = form.findViewById(R.id.profile_confirm_password);
        TextInputLayout currentLayout = form.findViewById(R.id.profile_current_password_layout);
        TextInputLayout nextLayout = form.findViewById(R.id.profile_new_password_layout);
        TextInputLayout confirmationLayout = form.findViewById(R.id.profile_confirm_password_layout);
        if (deleting) {
            nextLayout.setVisibility(View.GONE);
            confirmationLayout.setVisibility(View.GONE);
        }
        dialog = new MaterialAlertDialogBuilder(this)
                .setTitle(deleting ? R.string.profile_delete_title : R.string.profile_change_password)
                .setMessage(deleting ? R.string.profile_delete_confirmation : R.string.profile_password_instructions)
                .setView(form).setNegativeButton(R.string.profile_cancel, null)
                .setPositiveButton(deleting ? R.string.profile_delete_action : R.string.profile_save_password, null).create();
        dialog.setOnShowListener(ignored -> {
            if (deleting) dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.auth_error));
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
                currentLayout.setError(null);
                nextLayout.setError(null);
                confirmationLayout.setError(null);
                String password = String.valueOf(current.getText());
                String newPassword = String.valueOf(next.getText());
                if (password.isBlank()) { currentLayout.setError(getString(R.string.required_field)); return; }
                if (!deleting && newPassword.isBlank()) { nextLayout.setError(getString(R.string.required_field)); return; }
                if (!deleting && !newPassword.equals(String.valueOf(confirmation.getText()))) {
                    confirmationLayout.setError(getString(R.string.password_mismatch)); return;
                }
                model.submit(deleting ? ProfileViewModel.Action.DELETE : ProfileViewModel.Action.PASSWORD, password, newPassword);
                current.setText(null); next.setText(null); confirmation.setText(null);
                dialog.dismiss();
            });
        });
        dialog.show();
    }

    @Override protected void onDestroy() {
        if (dialog != null) dialog.dismiss();
        super.onDestroy();
    }
}
