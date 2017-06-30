package com.entreprise.davfou.projetandroidesgi.ui.activity.login;

/**
 * Created by davidfournier on 04/06/2017.
 */

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.bussiness.login.ManageUser;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.UserLogin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_go)
    Button btGo;
    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.checkboxStayConnected)
    CheckBox checkboxStayConnected;

    ManageUser manageUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
        manageUser = new ManageUser(this,this);

        //check si user connecté


    }

    @Override
    protected void onResume() {
        super.onResume();

        manageUser.isConnected();

    }


//Rajouter autocomplétion sur mail

    @OnClick({R.id.bt_go, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(null);
                    getWindow().setEnterTransition(null);

                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }

                break;
            case R.id.bt_go:

                manageUser.connect(new UserLogin(etUsername.getText().toString(),etPassword.getText().toString()),checkboxStayConnected.isChecked());
                break;
        }
    }
}
