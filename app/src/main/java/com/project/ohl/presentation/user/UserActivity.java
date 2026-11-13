package com.project.ohl.presentation.user;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.project.ohl.R;
import com.project.ohl.domain.model.User;
import com.project.ohl.presentation.LearningViewModel;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserActivity extends AppCompatActivity {
   private TextInputEditText nameInput;
   private TextInputEditText emailInput;
   private TextInputEditText passwordInput;
   private TextInputEditText phoneInput;
   private TextInputEditText schoolIdInput;
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
         String phone = String.valueOf(phoneInput.getText());
         String schoolId = String.valueOf(schoolIdInput.getText());
         String roleId = String.valueOf(roleIdInput.getText());

         final User user;
         try {
            user = new User(null, name, email, password, phone,
                    UUID.fromString(schoolId), UUID.fromString(roleId));
         } catch (IllegalArgumentException exception) {
            Toast.makeText(this, R.string.invalid_user_input, Toast.LENGTH_SHORT).show();
            return;
         }
         saveUserButton.setEnabled(false);

         LearningViewModel.of(this).<User>load(this, "saveUser", repository -> repository.saveUser(user), result -> {
            saveUserButton.setEnabled(true);
            if (result.error() != null) {
               Toast.makeText(UserActivity.this, "User save failed", Toast.LENGTH_SHORT).show();
               Logger.getLogger(UserActivity.class.getName()).log(Level.SEVERE, "Error occurred:", result.error());

            } else {
               User savedUser = result.value();
               Toast.makeText(UserActivity.this, "User saved successfully", Toast.LENGTH_SHORT).show();

            }
        });
      });
   }

   private void initializeViews() {
      nameInput = findViewById(R.id.form_textFieldName);
      emailInput = findViewById(R.id.form_textFieldEmail);
      passwordInput = findViewById(R.id.form_textFieldPassword);
      phoneInput = findViewById(R.id.form_textFieldPhone);
      schoolIdInput = findViewById(R.id.form_textFieldSchoolId);
      roleIdInput = findViewById(R.id.form_textFieldRoleId);
      saveUserButton = findViewById(R.id.button_saveUser);
   }
}
