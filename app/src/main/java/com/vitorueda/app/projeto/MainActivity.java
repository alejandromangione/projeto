package com.vitorueda.app.projeto;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.vitorueda.app.projeto.com.vitorueda.app.Http.WebServer;
import com.vitorueda.app.projeto.com.vitorueda.app.Tether.Tether;

import java.io.IOException;


public class MainActivity extends Activity {
    static Tether mTether;
    private WifiManager mWifiManager;

    private static CheckBox sCheckBox;
    private static Button sButton;
    private static Button sButton2;

    private WebServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTether = new Tether();
        server = new WebServer();
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        sCheckBox = (CheckBox) findViewById(R.id.checkbox_status);
        sButton = (Button) findViewById(R.id.button_start);
        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTether.toggleTether(mWifiManager, MainActivity.this);
            }
        });

        sButton2 = (Button) findViewById(R.id.button_mudaTexto);
        sButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                server.setMsg("<html><body><h1>FUNCIONA</h1></body></html>");
            }
        });
        try {
            server.start();
        } catch(IOException ioe) {
            Log.w("MAIN", "The server could not start.");
        }
    }


    public static void updateStatusDisplay() {
        if (mTether.getTetherState() == mTether.TETHER_STATE_ENABLED || mTether.getTetherState() == mTether.TETHER_STATE_ENABLING) {
            sCheckBox.setChecked(true);
            //findViewById(R.id.bg).setBackgroundResource(R.drawable.bg_wifi_on);
        } else {
            sCheckBox.setChecked(false);
            //findViewById(R.id.bg).setBackgroundResource(R.drawable.bg_wifi_off);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (server != null)
            server.stop();
    }
}
