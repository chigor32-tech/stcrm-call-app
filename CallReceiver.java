package ru.stcrm.callrouter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (TelephonyManager.EXTRA_STATE_RINGING.equals(intent.getStringExtra(TelephonyManager.EXTRA_STATE))) {
                String phone = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                if (phone != null && !phone.isEmpty()) {
                    SharedPreferences prefs = context.getSharedPreferences("StcrmPrefs", Context.MODE_PRIVATE);
                    String urlStr = prefs.getString("webhook_url", "https://stcrm.ru/webhook_call.php");
                    
                    new Thread(() -> {
                        try {
                            HttpURLConnection c = (HttpURLConnection) new URL(urlStr + "?phone=" + phone).openConnection();
                            c.setRequestMethod("GET");
                            c.getResponseCode();
                            c.disconnect();
                        } catch (Exception ignored) {}
                    }).start();
                }
            }
        } catch (Exception ignored) {}
    }
}
