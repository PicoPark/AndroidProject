package com.entreprise.davfou.projetandroidesgi.data.userservice;

/**
 * Created by davidfournier on 28/02/2017.
 */


import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.User;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.UserInfo;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.UserLogin;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;

import java.util.ArrayList;


public interface IUserInterface {

    void create(User user, final IServiceResultListener<String>
            resultListener);
    void connect(UserLogin userLogin, final IServiceResultListener<String>
            resultListener);

    void getInfo(UserRealm userRealm,final IServiceResultListener<UserInfo>
            resultListener);


    void getAll(final IServiceResultListener<ArrayList<UserInfo>> resultListener);

    void update(UserRealm userRealm,User user, final IServiceResultListener<String>
            resultListener);

}