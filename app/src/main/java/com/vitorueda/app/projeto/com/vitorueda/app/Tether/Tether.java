package com.vitorueda.app.projeto.com.vitorueda.app.Tether;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;

import com.vitorueda.app.projeto.MainActivity;

import java.lang.reflect.Method;

/**
 * Created by lsitec61.ueda on 17/09/2014.
 */
public class Tether extends Activity {
    private static int constant = 0;
    public static final int TETHER_STATE_DISABLING = 0;
    public static final int TETHER_STATE_DISABLED = 1;
    public static final int TETHER_STATE_ENABLING = 2;
    public static final int TETHER_STATE_ENABLED = 3;
    public static final int TETHER_STATE_FAILED = 4;

    private WifiManager mWifiManager;

    public void toggleTether(WifiManager wifiHandler, Context context) {
        if (mWifiManager==null){
            mWifiManager = wifiHandler;
        }

        boolean isTetherOn = (getTetherState() == TETHER_STATE_ENABLED || getTetherState() == TETHER_STATE_ENABLING);
        new SetTetherTask(!isTetherOn,false,context).execute();
    }

    public int getTetherState() {
        int state = TETHER_STATE_FAILED;
        try {
            Method method = mWifiManager.getClass().getMethod("getWifiApState");
            state = (Integer) method.invoke(mWifiManager);
        } catch (Exception e) {

        }
        return state;
    }

    private int setTetherEnabled(boolean enabled) {
        int state = TETHER_STATE_FAILED;
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "Teste";
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);


        //Desligar o Wifi
        if (enabled && mWifiManager.getConnectionInfo() !=null) {
            mWifiManager.setWifiEnabled(false);
            int loopMax = 10;
            while(loopMax>0 && mWifiManager.getWifiState()!=WifiManager.WIFI_STATE_DISABLED){
                try {
                    Thread.sleep(500);
                    loopMax--;
                } catch (Exception e) {

                }
            }
        }

        //Ligar o Tether
        try {
            mWifiManager.setWifiEnabled(false);
            Method method1 = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method1.invoke(mWifiManager, config, enabled); // true
            Method method2 = mWifiManager.getClass().getMethod("getWifiApState");
            state = (Integer) method2.invoke(mWifiManager);
        } catch (Exception e) {

        }

        //hold thread up while processing occurs
        if (!enabled) {
            int loopMax = 10;
            while (loopMax>0 && (getTetherState()== TETHER_STATE_DISABLING || getTetherState() == TETHER_STATE_ENABLED || getTetherState() == TETHER_STATE_FAILED)) {
                try {
                    Thread.sleep(500);
                    loopMax--;
                } catch (Exception e) {

                }
            }

        } else if (enabled) {
            int loopMax = 10;
            while (loopMax>0 && (getTetherState() == TETHER_STATE_ENABLING || getTetherState() == TETHER_STATE_DISABLED || getTetherState() == TETHER_STATE_FAILED)) {
                try {
                    Thread.sleep(500);
                    loopMax--;
                } catch (Exception e) {

                }
            }
        }
        return state;
    }

    class SetTetherTask extends AsyncTask<Void, Void, Void> {
        boolean mMode; //enable or disable wifi AP
        boolean mFinish; //finalize or not (e.g. on exit)
        ProgressDialog d;

        public SetTetherTask(boolean mode, boolean finish, Context context) {
            mMode = mode;
            mFinish = finish;
            d = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            d.setTitle("Turning WiFi Tethering " + (mMode?"on":"off") + "...");
            d.setMessage("...please wait a moment.");
            d.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                d.dismiss();
                MainActivity.updateStatusDisplay();
            } catch (IllegalArgumentException e) {

            };
            if (mFinish){
                finish();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            setTetherEnabled(mMode);
            return null;
        }
    }
}
