package br.com.litelink;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.SharedPreferences;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        final SharedPreferences prefs=getSharedPreferences("config",0);
        LinearLayout root=new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL); root.setPadding(40,60,40,30);
        root.setBackgroundColor(Color.rgb(16,7,26));
        TextView title=new TextView(this); title.setText("LiteLink • Android"); title.setTextSize(25); title.setTextColor(Color.WHITE); root.addView(title);
        TextView info=new TextView(this); info.setText("Configure seu link do TikTok Lite. Ao ativar, o aplicativo tentará abrir o destino a cada 2 minutos. O Android pode impedir aberturas em segundo plano ou atrasar a rotina."); info.setTextColor(Color.WHITE); info.setPadding(0,25,0,25); root.addView(info);
        EditText url=new EditText(this); url.setSingleLine(false); url.setHint("https://... (link do evento)"); url.setTextColor(Color.WHITE); url.setHintTextColor(Color.LTGRAY); url.setText(prefs.getString("event_url","https://www.tiktok.com/d/4/ZS9fJfFUMvjER-JkCEM/")); root.addView(url);
        TextView status=new TextView(this); status.setTextColor(Color.WHITE); status.setPadding(0,15,0,15); root.addView(status);
        Runnable refresh=()->status.setText("Rotina: "+(prefs.getBoolean("enabled",false)?"ATIVADA":"DESATIVADA"));
        Button enable=new Button(this); enable.setText("Salvar e ativar rotina de 2 minutos"); root.addView(enable);
        enable.setOnClickListener(v->{String s=url.getText().toString().trim(); if (!s.isEmpty() && !s.startsWith("https://") && !s.startsWith("http://")) {url.setError("Use um link https:// ou deixe vazio");return;} prefs.edit().putString("event_url",s).putBoolean("enabled",true).apply(); Scheduler.schedule(this); refresh.run();});
        Button test=new Button(this); test.setText("Testar abertura agora"); root.addView(test);
        test.setOnClickListener(v->{String s=url.getText().toString().trim(); if (!s.isEmpty()&&!s.startsWith("https://")&&!s.startsWith("http://")){url.setError("Link inválido");return;} prefs.edit().putString("event_url",s).apply(); Launcher.open(this);});
        Button stop=new Button(this); stop.setText("Parar rotina"); root.addView(stop);
        stop.setOnClickListener(v->{prefs.edit().putBoolean("enabled",false).apply();Scheduler.stop(this);refresh.run();});
        refresh.run(); setContentView(root);
    }
}
