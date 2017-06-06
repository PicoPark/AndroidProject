package com.entreprise.davfou.projetandroidesgi.data.methodRest;

/**
 * Created by davidfournier on 28/02/2017.
 */


import com.entreprise.davfou.projetandroidesgi.data.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {
    public static final String ENDPOINT = "https://esgi-2017.herokuapp.com";





    /**
     *  Connect user
     * @param email
     * @param password
     * @return user
     */
    @FormUrlEncoded
    @POST("/auth/login")
    Call<String> connectUser(@Field("email") String email, @Field("password") String password);



    /**
     *  Création d'un user
     * @param user
     * @return result
     */

    @POST("/auth/subscribe")
    Call<String> createUser(@Body User user);





}