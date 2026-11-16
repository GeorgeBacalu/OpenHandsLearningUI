package com.project.ohl.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.project.ohl.R;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.presentation.AuthenticationViewModel;
import com.project.ohl.presentation.UserSession;

public class LoginActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_login);
        setTitle(R.string.login);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextInputEditText email = findViewById(R.id.login_email);
        TextInputEditText password = findViewById(R.id.login_password);
        MaterialButton submit = findViewById(R.id.button_login);
        TextView error = findViewById(R.id.auth_error);
        var authentication = AuthenticationViewModel.of(this);
        submit.setEnabled(!authentication.isSubmitting());
        findViewById(R.id.auth_progress).setVisibility(authentication.isSubmitting() ? View.VISIBLE : View.GONE);
        authentication.observeAuthentication(this, result -> {
            if (result == null) return;
            submit.setEnabled(true);
            findViewById(R.id.auth_progress).setVisibility(View.GONE);
            if (result.isSuccess()) {
                startActivity(new Intent(this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
            else { error.setText(AuthMessages.forError(result.error())); error.setVisibility(View.VISIBLE); }
        });
        findViewById(R.id.open_registration).setOnClickListener(view -> startActivity(new Intent(this, UserActivity.class)));
        submit.setOnClickListener(view -> {
            String address = String.valueOf(email.getText()).strip();
            String secret = String.valueOf(password.getText());
            if (!Patterns.EMAIL_ADDRESS.matcher(address).matches()) { email.setError(getString(R.string.invalid_email)); return; }
            if (secret.isEmpty()) { password.setError(getString(R.string.required_field)); return; }
            submit.setEnabled(false);
            error.setVisibility(View.GONE);
            findViewById(R.id.auth_progress).setVisibility(View.VISIBLE);
            authentication.authenticate(repository -> repository.login(address, secret));
        });
    }

    @Override protected void onResume() {
        super.onResume();
        if (UserSession.id() != null) {
            startActivity(new Intent(this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }
    }

    @Override public boolean onSupportNavigateUp() { finish(); return true; }
}
