package com.entreprise.davfou.projetandroidesgi.data.methodRest;

/**
 * Created by davidfournier on 28/02/2017.
 */


import com.entreprise.davfou.projetandroidesgi.data.model.User;
import com.entreprise.davfou.projetandroidesgi.data.model.UserLogin;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {
    public static final String ENDPOINT = "https://esgi-2017.herokuapp.com";





    /**
     *  Connect user
     * @param userLogin
     * @return user
     */
    @POST("/auth/login")
    Call<String> connectUser(@Body UserLogin userLogin);



    /**
     *  Création d'un user
     * @param user
     * @return result
     */

    @POST("/auth/subscribe")
    Call<JSONObject> createUser(@Body User user);





}