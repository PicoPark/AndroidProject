package com.entreprise.davfou.projetandroidesgi.data.newArchi;

/**
 * Created by davidfournier on 28/02/2017.
 */


import com.entreprise.davfou.projetandroidesgi.data.modelRest.User;


public interface IUserInterface {

    void create(User user, final IServiceResultListener<String>
            resultListener);
    void read(String userID, final IServiceResultListener<User>
            resultListener);

}