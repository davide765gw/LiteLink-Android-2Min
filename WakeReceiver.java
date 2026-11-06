package br.com.litelink;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
public class WakeReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context c, Intent intent) {
        if (!c.getSharedPreferences("config",0).getBoolean("enabled",false)) return;
        try { Launcher.open(c); }
        catch (Exception ignored) { /* Android pode bloquear a abertura em segundo plano. */ }
        Scheduler.schedule(c);
    }
}
