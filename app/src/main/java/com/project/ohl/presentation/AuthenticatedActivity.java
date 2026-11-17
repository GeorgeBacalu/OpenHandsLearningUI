package com.project.ohl.presentation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.project.ohl.presentation.user.LoginActivity;

public abstract class AuthenticatedActivity extends AppCompatActivity {
    public static final String AUTHENTICATION_EXPIRED = "com.project.ohl.AUTHENTICATION_EXPIRED";
    private final BroadcastReceiver authenticationExpired =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    redirectToLogin();
                }
            };

    @Override
    protected void onStart() {
        super.onStart();

        ContextCompat.registerReceiver(
                this,
                authenticationExpired,
                new IntentFilter(AUTHENTICATION_EXPIRED),
                ContextCompat.RECEIVER_NOT_EXPORTED);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(authenticationExpired);

        super.onStop();
    }

    protected boolean validatesAccountOnResume() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!validatesAccountOnResume() && UserSession.id() == null) {
            getWindow().getDecorView().setVisibility(View.INVISIBLE);

            startActivity(
                    new Intent(this, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }
    }

    protected final void redirectToLogin() {
        if (isFinishing()) return;

        getWindow().getDecorView().setVisibility(View.INVISIBLE);

        startActivity(
                new Intent(this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }
}
