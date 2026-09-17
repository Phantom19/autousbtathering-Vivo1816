package id.sobatjasa.autousbtether;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.lang.reflect.Method;

public final class TetherController {
    private static final String TAG = "AutoUsbTether";

    private TetherController() {}

    public static String enable(Context context) {
        // First try Android 8.1 hidden ConnectivityManager.setUsbTethering(boolean).
        // A stock third-party APK normally lacks TETHER_PRIVILEGED, so failure is expected.
        try {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Method m = ConnectivityManager.class.getDeclaredMethod("setUsbTethering", boolean.class);
            m.setAccessible(true);
            Object result = m.invoke(cm, true);
            return "API tethering dipanggil. Hasil=" + result;
        } catch (Throwable e) {
            Log.e(TAG, "Hidden API failed", e);
            return "NON-ROOT DITOLAK/TERBATAS.\n" +
                    "Error: " + e.getClass().getSimpleName() + ": " + e.getMessage() +
                    "\n\nGunakan MODE ROOT atau buka pengaturan USB tethering.";
        }
    }

    public static String enableRoot() {
        String[] commands = new String[] {
                "svc usb setFunctions rndis",
                "settings put global usb_config rndis",
                "setprop sys.usb.config rndis"
        };

        StringBuilder out = new StringBuilder();
        boolean ok = false;

        for (String cmd : commands) {
            try {
                Process p = Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
                int code = p.waitFor();
                out.append(cmd).append(" -> exit ").append(code).append("\n");
                if (code == 0) ok = true;
            } catch (Throwable e) {
                out.append(cmd).append(" -> ").append(e.getClass().getSimpleName())
                   .append(": ").append(e.getMessage()).append("\n");
            }
        }

        if (ok) {
            return "MODE ROOT BERHASIL DIJALANKAN.\n" + out +
                    "\nSekarang cek OpenWrt dengan: ip link show usb0";
        }
        return "MODE ROOT GAGAL / SU TIDAK TERSEDIA.\n" + out;
    }
}
