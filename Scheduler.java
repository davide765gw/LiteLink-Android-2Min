package br.com.litelink;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
public class Scheduler {
    static final long INTERVAL = 2L*60*1000;
    static PendingIntent pending(Context c) {
        Intent i = new Intent(c, WakeReceiver.class);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= 23) flags |= PendingIntent.FLAG_IMMUTABLE;
        return PendingIntent.getBroadcast(c, 101, i, flags);
    }
    static void schedule(Context c) {
        AlarmManager am=(AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
        if (am == null) return;
        long when=SystemClock.elapsedRealtime()+INTERVAL;
        if (Build.VERSION.SDK_INT>=23) am.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,when,pending(c));
        else am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,when,pending(c));
    }
    static void stop(Context c) {
        AlarmManager am=(AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
        if (am != null) am.cancel(pending(c));
    }
}
