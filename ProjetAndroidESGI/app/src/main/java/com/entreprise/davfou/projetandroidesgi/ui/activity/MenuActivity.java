package com.entreprise.davfou.projetandroidesgi.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.business.MyApplication;
import com.entreprise.davfou.projetandroidesgi.business.user.ManageUser;
import com.entreprise.davfou.projetandroidesgi.data.model.local.UserRealm;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.news.ListNewsFragment;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.news.NewsDetailsFragment;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.profil.ProfilFragment;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.topics.ListTopicsFragment;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.topics.TopicDetailsFragment;

public class MenuActivity extends AppCompatActivity {

    static ManageUser manageUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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



        manageUser = new ManageUser(MyApplication.getAppContext(),activity);



        return manageUser.getUserConnected();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment obj = (Fragment)getSupportFragmentManager().findFragmentById(R.id.frame_container);

            switch (item.getItemId()) {

                case R.id.navigation_news:

                    if(!(obj instanceof ListNewsFragment)) {
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(
                                R.id.frame_container,
                                new ListNewsFragment().newInstance())
                                .commit();
                    }
                    return true;
                case R.id.navigation_topics:

                    if(!(obj instanceof ListTopicsFragment)) {
                        if (obj instanceof ProfilFragment) {
                            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(
                                    R.id.frame_container,
                                    new ListTopicsFragment().newInstance())
                                    .commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(
                                    R.id.frame_container,
                                    new ListTopicsFragment().newInstance())
                                    .commit();
                        }
                    }


                    return true;
                case R.id.navigation_profil:
                    if(!(obj instanceof ProfilFragment)) {
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(
                                R.id.frame_container,
                                new ProfilFragment().newInstance())
                                .commit();
                    }
                    return true;
            }
            return false;
        }

    };


    @Override
    public void onBackPressed() {


        Fragment obj = (Fragment)getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (obj instanceof ListNewsFragment) {

            new AlertDialog.Builder(this,R.style.MyAlertDialogStyle)
                    .setTitle("Attention")
                    .setMessage("Voulez-vous vous quitter l'application?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else if(obj instanceof NewsDetailsFragment){
            Fragment fragment = new ListNewsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }
        else if(obj instanceof ProfilFragment) {
            Fragment fragment = new ListNewsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }
        else if(obj instanceof ListTopicsFragment) {
            Fragment fragment = new ListNewsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }
        else if(obj instanceof TopicDetailsFragment) {
            Fragment fragment = new ListTopicsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }

    }
}
