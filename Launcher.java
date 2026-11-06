package br.com.litelink;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class Launcher {
    static final String PACKAGE = "com.zhiliaoapp.musically.go";
    static final String PLAY_URL = "https://play.google.com/store/apps/details?id=com.zhiliaoapp.musically.go";
    static void open(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        boolean installed;
        try { pm.getPackageInfo(PACKAGE, 0); installed = true; }
        catch (PackageManager.NameNotFoundException ex) { installed = false; }
        String url = ctx.getSharedPreferences("config",0).getString("event_url", "https://www.tiktok.com/d/4/ZS9fJfFUMvjER-JkCEM/");
        Intent i;
        if (installed) {
            if (url.startsWith("https://") || url.startsWith("http://")) {
                i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                i.setPackage(PACKAGE);
                if (i.resolveActivity(pm) == null) i = pm.getLaunchIntentForPackage(PACKAGE);
            } else i = pm.getLaunchIntentForPackage(PACKAGE);
        } else {
            i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PACKAGE));
            if (i.resolveActivity(pm) == null) i = new Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_URL));
        }
        if (i != null) { i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); ctx.startActivity(i); }
    }
}
