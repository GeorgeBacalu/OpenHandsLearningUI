package com.project.ohl.presentation;

import android.content.Context;
import java.util.UUID;

public final class UserProfiles {
    private UserProfiles() {}
    public static UUID id(Context context) {
        String value = context.getSharedPreferences("profile", Context.MODE_PRIVATE).getString("user_id", null);
        try { return value == null ? null : UUID.fromString(value); }
        catch (IllegalArgumentException ignored) { return null; }
    }
    public static void select(Context context, UUID id) {
        context.getSharedPreferences("profile", Context.MODE_PRIVATE).edit().putString("user_id", id.toString()).apply();
    }
}
