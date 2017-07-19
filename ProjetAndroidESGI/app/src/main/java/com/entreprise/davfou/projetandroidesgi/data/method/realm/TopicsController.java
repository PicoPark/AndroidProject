package com.entreprise.davfou.projetandroidesgi.data.method.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.entreprise.davfou.projetandroidesgi.data.modelLocal.NewsRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.TopicRealm;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by davidfournier on 19/07/2017.
 */

public class TopicsController {
    private static TopicsController instance;
    private final Realm realm;

    public TopicsController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static TopicsController with(Fragment fragment) {

        if (instance == null) {
            instance = new TopicsController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static TopicsController with(Activity activity) {

        if (instance == null) {
            instance = new TopicsController(activity.getApplication());
        }
        return instance;
    }

    public static TopicsController with(Application application) {

        if (instance == null) {
            instance = new TopicsController(application);
        }
        return instance;
    }

    public static TopicsController getInstance() {

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


    public TopicRealm getTopicById(long id) {
        return realm.where(TopicRealm.class).equalTo("id", id).findFirst();

    }

    public RealmResults<TopicRealm> getTopics() {
        return realm.where(TopicRealm.class).findAll();
    }



}
