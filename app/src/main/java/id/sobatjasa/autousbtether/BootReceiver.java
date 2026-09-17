package id.sobatjasa.autousbtether;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "AutoUsbTetherBoot";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Broadcast: " + intent.getAction());

        // Give USB/network stack time to initialize after boot.
        final Context app = context.getApplicationContext();
        new Handler().postDelayed(() -> {
            String result = TetherController.enable(app);
            Log.i(TAG, result);

            // If the phone is rooted, try the stronger RNDIS command as well.
            String rootResult = TetherController.enableRoot();
            Log.i(TAG, rootResult);
        }, 12000);
    }
}
