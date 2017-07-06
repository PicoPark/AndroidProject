package com.entreprise.davfou.projetandroidesgi.data.method.realm;


import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.entreprise.davfou.projetandroidesgi.data.modelLocal.CommentRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.NewsRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.PostRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.TopicRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserInfoRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Comment;

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
        realm.clear(TopicRealm.class);
        realm.clear(CommentRealm.class);
        realm.clear(NewsRealm.class);
        realm.clear(PostRealm.class);

        realm.commitTransaction();
    }

    //find all objects in the Defi.class
    public RealmResults<UserRealm> getUsers() {

        return realm.where(UserRealm.class).findAll();
    }

    public RealmResults<PostRealm> getPosts() {
        return realm.where(PostRealm.class).findAll();
    }

    public RealmResults<NewsRealm> getNews() {
        return realm.where(NewsRealm.class).findAll();
    }

    public RealmResults<TopicRealm> getTopics() {
        return realm.where(TopicRealm.class).findAll();
    }

    public RealmResults<CommentRealm> getComments() {
        return realm.where(CommentRealm.class).findAll();
    }

    //find all objects in the Defi.class
    public UserRealm getUser(String email) {

        return realm.where(UserRealm.class).equalTo("email", email).findFirst();
    }

    public UserRealm getUserConnected(Boolean isConnected) {

        return realm.where(UserRealm.class).equalTo("connected", isConnected).findFirst();
    }
    public PostRealm getPostById(long id) {
        return realm.where(PostRealm.class).equalTo("id", id).findFirst();

    }

    public NewsRealm getNewById(long id) {
        return realm.where(NewsRealm.class).equalTo("id", id).findFirst();

    }

    public CommentRealm getCommentById(long id) {
        return realm.where(CommentRealm.class).equalTo("id", id).findFirst();

    }

    public CommentRealm getCommentByNews(String news) {
        return realm.where(CommentRealm.class).equalTo("id", news).findFirst();

    }


    public TopicRealm getTopicById(long id) {
        return realm.where(TopicRealm.class).equalTo("id", id).findFirst();

    }

    public UserInfoRealm getUserById(String id) {
        return realm.where(UserInfoRealm.class).equalTo("_id", id).findFirst();

    }






}
