package com.entreprise.davfou.projetandroidesgi.data.method.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.entreprise.davfou.projetandroidesgi.data.model.local.PostRealm;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by davidfournier on 19/07/2017.
 */

public class PostsController {
    private static PostsController instance;
    private final Realm realm;

    public PostsController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static PostsController with(Fragment fragment) {

        if (instance == null) {
            instance = new PostsController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static PostsController with(Activity activity) {

        if (instance == null) {
            instance = new PostsController(activity.getApplication());
        }
        return instance;
    }

    public static PostsController with(Application application) {

        if (instance == null) {
            instance = new PostsController(application);
        }
        return instance;
    }

    public static PostsController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }




    public PostRealm getPostById(long id) {
        return realm.where(PostRealm.class).equalTo("id", id).findFirst();

    }

    public RealmResults<PostRealm> getPosts() {
        return realm.where(PostRealm.class).findAll();
    }



}
