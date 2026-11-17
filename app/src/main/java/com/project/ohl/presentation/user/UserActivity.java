package com.project.ohl.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.project.ohl.R;
import com.project.ohl.app.AppViewModels;
import com.project.ohl.domain.model.Registration;
import com.project.ohl.presentation.AuthenticationViewModel;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.presentation.SchoolDirectoryViewModel;
import java.util.List;
import java.util.UUID;

public class UserActivity extends AppCompatActivity {
    private MaterialAutoCompleteTextView countyInput;
    private MaterialAutoCompleteTextView cityInput;
    private MaterialAutoCompleteTextView schoolInput;
    private SchoolDirectoryViewModel directory;
    private boolean restoring;

    private record DirectoryOption(UUID id, String name) {
        @Override
        public @NonNull String toString() {
            return name;
        }
    }

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);

        setContentView(R.layout.activity_user);
        setTitle(R.string.register);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        countyInput = findViewById(R.id.registration_county);

        cityInput = findViewById(R.id.registration_city);

        schoolInput = findViewById(R.id.registration_school);

        directory = AppViewModels.get(this, SchoolDirectoryViewModel.class);

        TextInputEditText name = findViewById(R.id.form_textFieldName);
        TextInputEditText email = findViewById(R.id.form_textFieldEmail);
        TextInputEditText password = findViewById(R.id.form_textFieldPassword);
        TextInputEditText confirmation = findViewById(R.id.password_confirmation);
        TextInputEditText phone = findViewById(R.id.form_textFieldPhone);

        MaterialButton submit = findViewById(R.id.button_saveUser);

        TextView error = findViewById(R.id.auth_error);

        var authentication = AppViewModels.get(this, AuthenticationViewModel.class);

        authentication
                .state()
                .observe(
                        this,
                        result -> {
                            submit.setEnabled(!result.isLoading());

                            findViewById(R.id.auth_progress)
                                    .setVisibility(result.isLoading() ? View.VISIBLE : View.GONE);

                            if (result.isLoading()) error.setVisibility(View.GONE);

                            if (result.isSuccess()) {
                                startActivity(
                                        new Intent(this, MainActivity.class)
                                                .addFlags(
                                                        Intent.FLAG_ACTIVITY_NEW_TASK
                                                                | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                finish();
                            } else if (result.error() != null) {
                                error.setText(AuthMessages.forError(result.error()));
                                error.setVisibility(View.VISIBLE);
                            }
                        });

        findViewById(R.id.open_login)
                .setOnClickListener(view -> {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                });

        setupDirectory();

        submit.setOnClickListener(view -> {
            String fullName = String.valueOf(name.getText()).strip();
            String address = String.valueOf(email.getText()).strip();
            String secret = String.valueOf(password.getText());
            String number = String.valueOf(phone.getText()).strip();

            if (fullName.isBlank()) {
                name.setError(getString(R.string.required_field));

                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(address).matches()) {
                email.setError(getString(R.string.invalid_email));

                return;
            }

            if (secret.isBlank()) {
                password.setError(getString(R.string.required_field));

                return;
            }

            if (!secret.equals(String.valueOf(confirmation.getText()))) {
                confirmation.setError(getString(R.string.password_mismatch));

                return;
            }

            if (!number.matches("0[0-9]{9}")) {
                phone.setError(getString(R.string.invalid_phone));

                return;
            }

            if (directory.schoolId() == null) {
                schoolInput.setError(getString(R.string.select_school));

                return;
            }

            var registration = new Registration(fullName, address, secret, number, directory.schoolId());

            submit.setEnabled(false);

            error.setVisibility(View.GONE);

            findViewById(R.id.auth_progress).setVisibility(View.VISIBLE);

            authentication.register(registration);
        });
    }

    private void setupDirectory() {
        cityInput.setEnabled(directory.countyId() != null);

        schoolInput.setEnabled(directory.cityId() != null);

        directory
                .counties()
                .observe(
                        this,
                        state -> {
                            if (state.isSuccess() && state.value() != null) {
                                countyInput.setAdapter(
                                        adapter(
                                                state.value().stream()
                                                        .map(value ->
                                                                new DirectoryOption(value.getId(), value.getName()))
                                                        .toList()));
                            } else if (state.error() != null) showDirectoryError();
                        });

        directory
                .cities()
                .observe(
                        this,
                        state -> {
                            if (state.isSuccess() && state.value() != null) {
                                cityInput.setAdapter(
                                        adapter(
                                                state.value().stream()
                                                        .map(value ->
                                                                new DirectoryOption(value.getId(), value.getName()))
                                                        .toList()));

                                if (cityInput.hasFocus() && directory.cityId() == null && !state.value().isEmpty())
                                    cityInput.showDropDown();
                            } else if (state.error() != null) showDirectoryError();
                        });

        directory
                .schools()
                .observe(
                        this,
                        state -> {
                            if (state.isSuccess() && state.value() != null) {
                                schoolInput.setAdapter(
                                        adapter(
                                                state.value().stream()
                                                        .map(value ->
                                                                new DirectoryOption(value.getId(), value.getName()))
                                                        .toList()));

                                if (schoolInput.hasFocus() && directory.schoolId() == null && !state.value().isEmpty())
                                    schoolInput.showDropDown();
                            } else if (state.error() != null) showDirectoryError();
                        });

        countyInput.setOnItemClickListener((parent, view, position, id) -> {
            directory.selectCounty(((DirectoryOption) parent.getItemAtPosition(position)).id());

            cityInput.setText("", false);

            schoolInput.setText("", false);

            cityInput.setEnabled(true);

            schoolInput.setEnabled(false);

            directory.searchCities("");
        });

        cityInput.setOnItemClickListener((parent, view, position, id) -> {
            directory.selectCity(((DirectoryOption) parent.getItemAtPosition(position)).id());

            schoolInput.setText("", false);
            schoolInput.setEnabled(true);

            directory.searchSchools("");
        });

        schoolInput.setOnItemClickListener((parent, view, position, id) ->
                directory.selectSchool(((DirectoryOption) parent.getItemAtPosition(position)).id()));

        watch(
                cityInput,
                () -> {
                    directory.editCityQuery(cityInput.getText().toString());

                    schoolInput.setText("", false);
                    schoolInput.setEnabled(false);
                });
        watch(schoolInput, () -> directory.editSchoolQuery(schoolInput.getText().toString()));

        directory.load();
    }

    private ArrayAdapter<DirectoryOption> adapter(List<DirectoryOption> names) {
        return new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
    }

    private void showDirectoryError() {
        TextView error = findViewById(R.id.auth_error);

        error.setText(R.string.directory_failed);
        error.setVisibility(View.VISIBLE);
    }

    private void watch(MaterialAutoCompleteTextView input, Runnable changed) {
        input.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence text, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence text, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable text) {
                        if (!restoring && !input.isPerformingCompletion()) changed.run();
                    }
                });
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle state) {
        restoring = true;

        super.onRestoreInstanceState(state);

        restoring = false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();

        return true;
    }
}
