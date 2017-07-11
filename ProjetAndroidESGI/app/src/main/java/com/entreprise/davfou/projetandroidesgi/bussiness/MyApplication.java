package com.entreprise.davfou.projetandroidesgi.bussiness;

import android.app.Activity;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by davidfournier on 18/06/2017.
 */

public class MyApplication extends android.app.Application {

    private static Context context;
    private static Activity activity;


    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();



        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }


    public static Context getAppContext() {

        return MyApplication.context;
    }



}
