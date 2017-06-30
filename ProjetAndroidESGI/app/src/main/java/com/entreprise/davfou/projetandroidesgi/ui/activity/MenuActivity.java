package com.entreprise.davfou.projetandroidesgi.ui.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.bussiness.MyApplication;
import com.entreprise.davfou.projetandroidesgi.bussiness.login.ManageUser;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.news.ListNewsFragment;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.profil.ProfilFragment;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.topics.ListTopicsFragment;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        }


        Fragment fragment = new ListNewsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public static UserRealm getUser(Activity activity){


        ManageUser manageUser = new ManageUser(MyApplication.getAppContext(),activity);



        return manageUser.getUserConnected();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_news:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right).replace(
                            R.id.frame_container,
                            new ListNewsFragment().newInstance())
                            .commit();
                    return true;
                case R.id.navigation_topics:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right).replace(
                            R.id.frame_container,
                            new ListTopicsFragment().newInstance())
                            .commit();
                    return true;
                case R.id.navigation_profil:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right).replace(
                            R.id.frame_container,
                            new ProfilFragment().newInstance())
                            .commit();
                    return true;
            }
            return false;
        }

    };
}
