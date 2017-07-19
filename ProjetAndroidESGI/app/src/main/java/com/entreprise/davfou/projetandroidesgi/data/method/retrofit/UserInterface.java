package com.entreprise.davfou.projetandroidesgi.data.method.retrofit;

/**
 * Created by davidfournier on 28/02/2017.
 */


import com.entreprise.davfou.projetandroidesgi.data.model.api.User;
import com.entreprise.davfou.projetandroidesgi.data.model.api.UserInfo;
import com.entreprise.davfou.projetandroidesgi.data.model.api.UserLogin;
import com.entreprise.davfou.projetandroidesgi.data.model.api.UserUpdate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface UserInterface {
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
    Call<Void> createUser(@Body User user);

    /**
     * Récupération des info par rapport au user
     * @param token
     * @return
     */
    @GET("/users/me")
    Call<UserInfo> getAuthorizedDriver(@Header("Authorization") String token);

    @GET("/users")
    Call<ArrayList<UserInfo>> getAll();


    @PUT("/users/bug/")
    Call<String> updateUser(@Header("Authorization") String token, @Body UserUpdate user);






}