package com.entreprise.davfou.projetandroidesgi.data.method;


import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.entreprise.davfou.projetandroidesgi.data.modelLocal.NewRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.PostRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.New;

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
        realm.clear(NewRealm.class);
        realm.clear(PostRealm.class);
        realm.commitTransaction();
    }

    //find all objects in the Defi.class
    public RealmResults<UserRealm> getUsers() {

        return realm.where(UserRealm.class).findAll();
    }

    public RealmResults<PostRealm> getPosts(){
        return  realm.where(PostRealm.class).findAll();
    }

    public RealmResults<NewRealm> getNews(){
        return realm.where(NewRealm.class).findAll();
    }

    //find all objects in the Defi.class
    public UserRealm getUser(String email) {

        return realm.where(UserRealm.class).equalTo("email", email).findFirst();
    }

    public PostRealm getPostById(long id){
        return realm.where(PostRealm.class).equalTo("id", id).findFirst();

    }

    public NewRealm getNewById(long id){
        return realm.where(NewRealm.class).equalTo("id", id).findFirst();

    }

    public UserRealm getUserConnected(Boolean isConnected) {

        return realm.where(UserRealm.class).equalTo("connected", isConnected).findFirst();
    }



}
