package br.com.litelink;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
public class BootReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context c, Intent i) {
        if (c.getSharedPreferences("config",0).getBoolean("enabled", false)) Scheduler.schedule(c);
    }
}
