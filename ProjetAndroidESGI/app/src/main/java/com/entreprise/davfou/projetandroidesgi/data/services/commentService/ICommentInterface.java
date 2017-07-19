package com.entreprise.davfou.projetandroidesgi.data.services.commentService;

import com.entreprise.davfou.projetandroidesgi.data.model.local.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.model.api.Comment;
import com.entreprise.davfou.projetandroidesgi.data.model.api.CommentCreate;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;

import java.util.ArrayList;

/**
 * Created by Pico on 06/07/2017.
 */

public interface ICommentInterface {

    void create(CommentCreate commentCreate, UserRealm userRealm, final IServiceResultListener<String>
            resultListener);

    void getAllComment(UserRealm userRealm, final IServiceResultListener<ArrayList<Comment>>
            resultListener);

    void deleteComment(UserRealm userRealm,Comment comment,final IServiceResultListener<String> resultListener);

    void updateComment(UserRealm userRealm,CommentCreate commentCreate,Comment comment,final IServiceResultListener<String> resultListener);

}
