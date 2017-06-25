package com.entreprise.davfou.projetandroidesgi.data;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by davidfournier on 18/06/2017.
 */

public class MyApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
