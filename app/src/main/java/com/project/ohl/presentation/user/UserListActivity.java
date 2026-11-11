package com.project.ohl.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.project.ohl.R;
import com.project.ohl.domain.model.User;
import com.project.ohl.presentation.LearningViewModel;
import com.project.ohl.presentation.UserProfiles;
import java.util.List;

import static android.R.layout.simple_list_item_single_choice;

public class UserListActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setTitle(R.string.choose_profile);
        var layout = new LinearLayout(this); layout.setOrientation(LinearLayout.VERTICAL);
        var create = new Button(this); create.setText(R.string.create_profile);
        create.setOnClickListener(view -> startActivity(new Intent(this, UserActivity.class)));
        layout.addView(create);
        var users = new ListView(this); layout.addView(users, new LinearLayout.LayoutParams(-1, 0, 1));
        setContentView(layout);
        LearningViewModel.of(this).<List<User>>load(this, "profiles", repository -> repository.getAllUsers(), result -> {
            if (!result.isSuccess() || result.value() == null) {
                Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show(); return;
            }
            var profiles = result.value();
            users.setAdapter(new ArrayAdapter<>(this, simple_list_item_single_choice,
                    profiles.stream().map(user -> user.getName() + " (" + user.getEmail() + ")").toList()));
            users.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            for (int i = 0; i < profiles.size(); i++) if (profiles.get(i).getId().equals(UserProfiles.id(this))) users.setItemChecked(i, true);
            users.setOnItemClickListener((parent, view, position, id) -> {
                UserProfiles.select(this, profiles.get(position).getId()); setResult(RESULT_OK); finish();
            });
        });
    }
    @Override protected void onRestart() { super.onRestart(); recreate(); }
}
