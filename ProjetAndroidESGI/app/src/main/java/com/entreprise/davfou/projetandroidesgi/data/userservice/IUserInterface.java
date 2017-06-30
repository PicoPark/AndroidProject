package com.entreprise.davfou.projetandroidesgi.data.userservice;

/**
 * Created by davidfournier on 28/02/2017.
 */


import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.User;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.UserInfo;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.UserLogin;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;


public interface IUserInterface {

    void create(User user, final IServiceResultListener<String>
            resultListener);
    void connect(UserLogin userLogin, final IServiceResultListener<String>
            resultListener);

    void getInfo(UserRealm userRealm,final IServiceResultListener<UserInfo>
            resultListener);

}