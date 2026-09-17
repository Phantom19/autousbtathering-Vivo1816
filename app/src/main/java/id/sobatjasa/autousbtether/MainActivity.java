package id.sobatjasa.autousbtether;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView status;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(32, 40, 32, 32);

        TextView title = new TextView(this);
        title.setText("AUTO USB TETHER\nVivo 1816");
        title.setTextSize(26);
        title.setGravity(Gravity.CENTER);
        root.addView(title, new LinearLayout.LayoutParams(-1, -2));

        status = new TextView(this);
        status.setText("\nMode: mencoba tanpa root...\n");
        status.setTextSize(17);
        root.addView(status);

        Button enable = new Button(this);
        enable.setText("AKTIFKAN USB TETHERING");
        enable.setOnClickListener(v -> {
            String result = TetherController.enable(this);
            status.setText(result);
        });
        root.addView(enable);

        Button settings = new Button(this);
        settings.setText("BUKA PENGATURAN TETHERING");
        settings.setOnClickListener(v -> {
            try {
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            } catch (Exception e) {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });
        root.addView(settings);

        Button rootTest = new Button(this);
        rootTest.setText("COBA MODE ROOT");
        rootTest.setOnClickListener(v -> status.setText(TetherController.enableRoot()));
        root.addView(rootTest);

        TextView note = new TextView(this);
        note.setText("\nCatatan:\nAndroid 8.1/Funtouch 4.5 membatasi API tethering untuk aplikasi biasa. "
                + "Mode non-root akan mencoba API tersembunyi; bila ditolak, gunakan mode root atau tombol pengaturan. "
                + "Jika root tersedia, aplikasi mencoba 'svc usb setFunctions rndis'.");
        note.setTextSize(14);
        root.addView(note);

        setContentView(root);
    }
}
