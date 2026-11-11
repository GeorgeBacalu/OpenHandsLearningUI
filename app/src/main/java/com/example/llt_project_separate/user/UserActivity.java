package com.example.llt_project_separate.user;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.general.standard_classes.Institution;
import com.example.llt_project_separate.general.standard_classes.Role;
import com.example.llt_project_separate.general.standard_classes.User;
import com.example.llt_project_separate.retrofit.UtilsRetrofit;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
   private TextInputEditText nameInput;
   private TextInputEditText emailInput;
   private TextInputEditText passwordInput;
   private TextInputEditText mobileInput;
   private TextInputEditText addressInput;
   private TextInputEditText birthdayInput;
   private TextInputEditText institutionIdInput;
   private TextInputEditText roleIdInput;
   private MaterialButton saveUserButton;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_user);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      initializeViews();

      saveUserButton.setOnClickListener(view -> {
         String name = String.valueOf(nameInput.getText());
         String email = String.valueOf(emailInput.getText());
         String password = String.valueOf(passwordInput.getText());
         String mobile = String.valueOf(mobileInput.getText());
         String address = String.valueOf(addressInput.getText());
         String birthday = String.valueOf(birthdayInput.getText());
         String institutionId = String.valueOf(institutionIdInput.getText());
         String roleId = String.valueOf(roleIdInput.getText());

//         Institution institution = new Institution();
//         institution.setId(Long.valueOf(institutionId));
//         Role role = new Role();
//         role.setId(Long.valueOf(roleId));
         LocalDate birthday1 = null;
         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            birthday1 = LocalDate.parse(birthday);
         }
         User user = new User(1L, name, email, password, mobile, address, birthday1, Long.valueOf(institutionId), Long.valueOf(roleId));

         UtilsRetrofit.getInstance(this).saveUser(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
               User savedUser = response.body();
               Logger.getLogger(UserActivity.class.getName()).log(Level.ALL, "savedUser", savedUser);
               Toast.makeText(UserActivity.this, "User saved successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable throwable) {
               Toast.makeText(UserActivity.this, "User save failed", Toast.LENGTH_SHORT).show();
               Logger.getLogger(UserActivity.class.getName()).log(Level.SEVERE, "Error occurred:", throwable);
            }
         }, user);
      });
   }

   private void initializeViews() {
      nameInput = findViewById(R.id.form_textFieldName);
      emailInput = findViewById(R.id.form_textFieldEmail);
      passwordInput = findViewById(R.id.form_textFieldPassword);
      mobileInput = findViewById(R.id.form_textFieldMobile);
      addressInput = findViewById(R.id.form_textFieldAddress);
      birthdayInput = findViewById(R.id.form_textFieldBirthday);
      institutionIdInput = findViewById(R.id.form_textFieldInstitutionId);
      roleIdInput = findViewById(R.id.form_textFieldRoleId);
      saveUserButton = findViewById(R.id.button_saveUser);
   }
}