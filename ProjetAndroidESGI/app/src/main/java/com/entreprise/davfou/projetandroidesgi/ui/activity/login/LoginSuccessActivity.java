package com.entreprise.davfou.projetandroidesgi.ui.activity.login;

/**
 * Created by davidfournier on 04/06/2017.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.bussiness.user.ManageUser;

public class LoginSuccessActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        ManageUser manageUser = new ManageUser(getBaseContext(),this);



        manageUser.getALlUser();



    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
