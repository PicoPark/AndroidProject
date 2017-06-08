package com.entreprise.davfou.projetandroidesgi.ui.activity.login;

/**
 * Created by davidfournier on 04/06/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.ui.activity.MainActivity;

public class LoginSuccessActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                startActivity(new Intent(LoginSuccessActivity.this, MainActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);



    }
}
