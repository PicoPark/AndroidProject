package com.entreprise.davfou.projetandroidesgi.data.method.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.entreprise.davfou.projetandroidesgi.data.modelLocal.CommentRealm;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by davidfournier on 19/07/2017.
 */

public class CommentsController {
    private static CommentsController instance;
    private final Realm realm;

    public CommentsController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static CommentsController with(Fragment fragment) {

        if (instance == null) {
            instance = new CommentsController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static CommentsController with(Activity activity) {

        if (instance == null) {
            instance = new CommentsController(activity.getApplication());
        }
        return instance;
    }

    public static CommentsController with(Application application) {

        if (instance == null) {
            instance = new CommentsController(application);
        }
        return instance;
    }

    public static CommentsController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    public RealmResults<CommentRealm> getComments() {
        return realm.where(CommentRealm.class).findAll();
    }






    public CommentRealm getCommentById(long id) {
        return realm.where(CommentRealm.class).equalTo("id", id).findFirst();

    }

    public CommentRealm getCommentByNews(String news) {
        return realm.where(CommentRealm.class).equalTo("id", news).findFirst();

    }


}
