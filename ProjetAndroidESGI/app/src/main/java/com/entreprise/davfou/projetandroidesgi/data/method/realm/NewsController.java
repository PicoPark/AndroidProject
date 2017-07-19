package com.entreprise.davfou.projetandroidesgi.data.method.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.entreprise.davfou.projetandroidesgi.data.model.local.NewsRealm;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by davidfournier on 19/07/2017.
 */

public class NewsController {
    private static NewsController instance;
    private final Realm realm;

    public NewsController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static NewsController with(Fragment fragment) {

        if (instance == null) {
            instance = new NewsController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static NewsController with(Activity activity) {

        if (instance == null) {
            instance = new NewsController(activity.getApplication());
        }
        return instance;
    }

    public static NewsController with(Application application) {

        if (instance == null) {
            instance = new NewsController(application);
        }
        return instance;
    }

    public static NewsController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    public RealmResults<NewsRealm> getNews() {
        return realm.where(NewsRealm.class).findAll();
    }

    public NewsRealm getNewById(long id) {
        return realm.where(NewsRealm.class).equalTo("id", id).findFirst();

    }


}
