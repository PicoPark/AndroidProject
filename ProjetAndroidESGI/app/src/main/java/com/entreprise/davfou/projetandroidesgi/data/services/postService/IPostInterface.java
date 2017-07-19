package com.entreprise.davfou.projetandroidesgi.data.services.postService;

import com.entreprise.davfou.projetandroidesgi.data.model.local.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.model.api.Post;
import com.entreprise.davfou.projetandroidesgi.data.model.api.PostCreate;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;

import java.util.ArrayList;

/**
 * Created by Pico on 04/07/2017.
 */

public interface IPostInterface {


    void create(PostCreate postCreate, UserRealm userRealm, final IServiceResultListener<String>
            resultListener);

    void getAll(UserRealm userRealm, final IServiceResultListener<ArrayList<Post>>
            resultListener);

    void deletePost(UserRealm userRealm, Post post, final IServiceResultListener<String> resultListener);

    void updatePost(UserRealm userRealm, PostCreate postCreate,Post post, final IServiceResultListener<String> resultListener);


}
