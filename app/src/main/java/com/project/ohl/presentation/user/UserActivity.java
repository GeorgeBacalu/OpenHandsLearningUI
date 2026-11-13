package com.project.ohl.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.BundleCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.project.ohl.R;
import com.project.ohl.domain.model.City;
import com.project.ohl.domain.model.County;
import com.project.ohl.domain.model.Registration;
import com.project.ohl.domain.model.School;
import com.project.ohl.presentation.AuthenticationViewModel;
import com.project.ohl.presentation.LearningViewModel;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class UserActivity extends AppCompatActivity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private MaterialAutoCompleteTextView countyInput;
    private MaterialAutoCompleteTextView cityInput;
    private MaterialAutoCompleteTextView schoolInput;
    private List<County> counties = List.of();
    private List<City> cities = List.of();
    private List<School> schools = List.of();
    private UUID countyId;
    private UUID cityId;
    private UUID schoolId;
    private Runnable citySearch;
    private Runnable schoolSearch;
    private boolean restoring;
    private record DirectoryOption(UUID id, String name) {
        @Override public String toString() { return name; }
    }

    @Override protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_user);
        setTitle(R.string.register);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        countyInput = findViewById(R.id.registration_county);
        cityInput = findViewById(R.id.registration_city);
        schoolInput = findViewById(R.id.registration_school);
        if (saved != null) {
            countyId = BundleCompat.getSerializable(saved, "countyId", UUID.class);
            cityId = BundleCompat.getSerializable(saved, "cityId", UUID.class);
            schoolId = BundleCompat.getSerializable(saved, "schoolId", UUID.class);
        }
        TextInputEditText name = findViewById(R.id.form_textFieldName);
        TextInputEditText email = findViewById(R.id.form_textFieldEmail);
        TextInputEditText password = findViewById(R.id.form_textFieldPassword);
        TextInputEditText confirmation = findViewById(R.id.password_confirmation);
        TextInputEditText phone = findViewById(R.id.form_textFieldPhone);
        MaterialButton submit = findViewById(R.id.button_saveUser);
        TextView error = findViewById(R.id.auth_error);
        var authentication = AuthenticationViewModel.of(this);
        submit.setEnabled(!authentication.isSubmitting());
        findViewById(R.id.auth_progress).setVisibility(authentication.isSubmitting() ? View.VISIBLE : View.GONE);
        authentication.observeAuthentication(this, result -> {
            if (result == null) return;
            submit.setEnabled(true);
            findViewById(R.id.auth_progress).setVisibility(View.GONE);
            if (result.isSuccess()) { setResult(RESULT_OK); finish(); }
            else { error.setText(AuthMessages.forError(result.error())); error.setVisibility(View.VISIBLE); }
        });
        findViewById(R.id.open_login).setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class)); finish();
        });
        setupDirectory();
        submit.setOnClickListener(view -> {
            String fullName = String.valueOf(name.getText()).strip();
            String address = String.valueOf(email.getText()).strip();
            String secret = String.valueOf(password.getText());
            String number = String.valueOf(phone.getText()).strip();
            if (fullName.isBlank()) { name.setError(getString(R.string.required_field)); return; }
            if (!Patterns.EMAIL_ADDRESS.matcher(address).matches()) { email.setError(getString(R.string.invalid_email)); return; }
            if (secret.length() < 15) { password.setError(getString(R.string.password_policy)); return; }
            if (secret.getBytes(StandardCharsets.UTF_8).length > 72) { password.setError(getString(R.string.password_too_long)); return; }
            if (!secret.equals(String.valueOf(confirmation.getText()))) { confirmation.setError(getString(R.string.password_mismatch)); return; }
            if (!number.matches("0[0-9]{9}")) { phone.setError(getString(R.string.invalid_phone)); return; }
            if (schoolId == null) { schoolInput.setError(getString(R.string.select_school)); return; }
            var registration = new Registration(fullName, address, secret, number, schoolId);
            submit.setEnabled(false);
            error.setVisibility(View.GONE);
            findViewById(R.id.auth_progress).setVisibility(View.VISIBLE);
            authentication.authenticate(repository -> repository.register(registration));
        });
    }

    private void setupDirectory() {
        cityInput.setEnabled(countyId != null); schoolInput.setEnabled(cityId != null);
        LearningViewModel.of(this).<List<County>>load(this, "counties", repository -> repository.getCounties(), result -> {
            if (result.isSuccess() && result.value() != null) {
                counties = result.value();
                countyInput.setAdapter(adapter(counties.stream().map(value -> new DirectoryOption(value.getId(), value.getName())).toList()));
            } else showDirectoryError();
        });
        countyInput.setOnItemClickListener((parent, view, position, id) -> {
            countyId = ((DirectoryOption) parent.getItemAtPosition(position)).id(); cityId = null; schoolId = null;
            cityInput.setAdapter(adapter(List.of())); schoolInput.setAdapter(adapter(List.of()));
            cityInput.setText("", false); schoolInput.setText("", false);
            cityInput.setEnabled(true); schoolInput.setEnabled(false); loadCities("");
        });
        cityInput.setOnItemClickListener((parent, view, position, id) -> {
            cityId = ((DirectoryOption) parent.getItemAtPosition(position)).id(); schoolId = null;
            schoolInput.setAdapter(adapter(List.of()));
            schoolInput.setText("", false); schoolInput.setEnabled(true); loadSchools("");
        });
        schoolInput.setOnItemClickListener((parent, view, position, id) -> schoolId = ((DirectoryOption) parent.getItemAtPosition(position)).id());
        watch(cityInput, () -> {
            cityId = null; schoolId = null; schoolInput.setText("", false); schoolInput.setEnabled(false);
            if (citySearch != null) handler.removeCallbacks(citySearch);
            citySearch = () -> loadCities(cityInput.getText().toString()); handler.postDelayed(citySearch, 300);
        });
        watch(schoolInput, () -> {
            schoolId = null;
            if (schoolSearch != null) handler.removeCallbacks(schoolSearch);
            schoolSearch = () -> loadSchools(schoolInput.getText().toString()); handler.postDelayed(schoolSearch, 300);
        });
        if (countyId != null) loadCities("");
        if (cityId != null) loadSchools("");
    }

    private void loadCities(String query) {
        if (countyId == null) return;
        UUID selected = countyId;
        LearningViewModel.of(this).<List<City>>load(this, "cities", repository -> repository.getCities(selected, query, 0, 50), result -> {
            if (!selected.equals(countyId)) return;
            if (result.isSuccess() && result.value() != null) {
                cities = result.value(); cityInput.setAdapter(adapter(cities.stream().map(value -> new DirectoryOption(value.getId(), value.getName())).toList()));
                if (cityInput.hasFocus() && cityId == null) cityInput.showDropDown();
            } else showDirectoryError();
        });
    }

    private void loadSchools(String query) {
        if (cityId == null) return;
        UUID selected = cityId;
        LearningViewModel.of(this).<List<School>>load(this, "schools", repository -> repository.getSchools(selected, query, 0, 50), result -> {
            if (!selected.equals(cityId)) return;
            if (result.isSuccess() && result.value() != null) {
                schools = result.value(); schoolInput.setAdapter(adapter(schools.stream().map(value -> new DirectoryOption(value.getId(), value.getName())).toList()));
                if (schoolInput.hasFocus() && schoolId == null) schoolInput.showDropDown();
            } else showDirectoryError();
        });
    }

    private ArrayAdapter<DirectoryOption> adapter(List<DirectoryOption> names) {
        return new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, names);
    }

    private void showDirectoryError() {
        TextView error = findViewById(R.id.auth_error);
        error.setText(R.string.directory_failed); error.setVisibility(View.VISIBLE);
    }

    private void watch(MaterialAutoCompleteTextView input, Runnable changed) {
        input.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence text, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence text, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable text) { if (!restoring) changed.run(); }
        });
    }

    @Override protected void onDestroy() { handler.removeCallbacksAndMessages(null); super.onDestroy(); }
    @Override protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("countyId", countyId);
        state.putSerializable("cityId", cityId);
        state.putSerializable("schoolId", schoolId);
    }
    @Override protected void onRestoreInstanceState(Bundle state) {
        restoring = true;
        super.onRestoreInstanceState(state);
        restoring = false;
    }
    @Override public boolean onSupportNavigateUp() { finish(); return true; }
}
