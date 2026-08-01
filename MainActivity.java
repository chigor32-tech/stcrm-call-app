package ru.stcrm.callrouter;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private EditText editWebhookUrl;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editWebhookUrl = findViewById(R.id.editWebhookUrl);
        btnSave = findViewById(R.id.btnSave);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 123);
        }

        SharedPreferences prefs = getSharedPreferences("StcrmPrefs", MODE_PRIVATE);
        editWebhookUrl.setText(prefs.getString("webhook_url", "https://stcrm.ru/webhook_call.php"));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editWebhookUrl.getText().toString().trim();
                getSharedPreferences("StcrmPrefs", MODE_PRIVATE).edit().putString("webhook_url", url).apply();
                Toast.makeText(MainActivity.this, "Сохранено!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
