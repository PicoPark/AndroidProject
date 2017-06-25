package com.entreprise.davfou.projetandroidesgi.data.method;


import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from UserRealm.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(UserRealm.class);
        realm.commitTransaction();
    }

    //find all objects in the Defi.class
    public RealmResults<UserRealm> getUsers() {

        return realm.where(UserRealm.class).findAll();
    }

    //find all objects in the Defi.class
    public UserRealm getUser(String email) {

        return realm.where(UserRealm.class).equalTo("email", email).findFirst();
    }

    public UserRealm getUserConnected(Boolean isConnected) {

        return realm.where(UserRealm.class).equalTo("connected", isConnected).findFirst();
    }



}
