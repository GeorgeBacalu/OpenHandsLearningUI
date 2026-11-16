package com.project.ohl.presentation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.content.ContextCompat;
import com.project.ohl.BuildConfig;
import java.net.URI;
import java.util.Locale;

final class LocalApiAccess {
    private LocalApiAccess() {}

    static boolean allowed(Context context) {
        if (!BuildConfig.DEBUG || Build.VERSION.SDK_INT < 37 || !localHost()) return true;
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_LOCAL_NETWORK)
                == PackageManager.PERMISSION_GRANTED;
    }

    private static boolean localHost() {
        String host = URI.create(BuildConfig.API_BASE_URL).getHost();
        if (host == null) return false;
        host = host.replace("[", "").replace("]", "").toLowerCase(Locale.ROOT);
        boolean privateIpv4 = host.matches("[0-9]+(\\.[0-9]+){3}")
                && (host.startsWith("127.") || host.startsWith("10.") || host.startsWith("192.168.")
                || host.startsWith("169.254.") || host.matches("172\\.(1[6-9]|2[0-9]|3[01])\\..*"));
        return host.equals("localhost") || host.endsWith(".local") || host.equals("::1") || privateIpv4
                || host.contains(":") && (host.startsWith("fc") || host.startsWith("fd") || host.startsWith("fe80:"));
    }
}
