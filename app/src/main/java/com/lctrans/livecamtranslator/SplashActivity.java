package com.lctrans.livecamtranslator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    String f212gm;
    int f213i = 0;
    private SharedPreferences f214sp;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_splash);
        setStoreToken(getResources().getString(R.string.app_name));
    }

    @Override
    protected void onResume() {
        super.onResume();
        goMain();
    }

    private void setStoreToken(String str) {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), 0);
        this.f214sp = sharedPreferences;
        String string = sharedPreferences.getString("gm", "");
        this.f212gm = string;
        if (this.f213i == 0 && string.equals("")) {
            SharedPreferences.Editor edit = this.f214sp.edit();
            edit.putString("gm", "0");
            edit.commit();
            this.f212gm = this.f214sp.getString("gm", "");
        }
    }

    private void goMain() {
        SplashActivity.this.startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        SplashActivity.this.finish();
    }

}
