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
import android.widget.EditText;
import android.widget.Toast;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.bussiness.login.ConnectUser;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.bt_go, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, com.entreprise.davfou.projetandroidesgi.ui.activity.login.RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, com.entreprise.davfou.projetandroidesgi.ui.activity.login.RegisterActivity.class));
                }

                break;
            case R.id.bt_go:

                ConnectUser connectUser = new ConnectUser(this,this);
                String result=connectUser.connect(etUsername.getText().toString(),etPassword.getText().toString());


                System.out.println("result : "+result);
                if(result!="") {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
