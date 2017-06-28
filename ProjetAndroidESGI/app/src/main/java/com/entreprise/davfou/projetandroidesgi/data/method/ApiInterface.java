package com.entreprise.davfou.projetandroidesgi.data.method;

/**
 * Created by davidfournier on 28/02/2017.
 */


import com.entreprise.davfou.projetandroidesgi.data.modelRest.New;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.NewsCreate;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.User;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.UserInfo;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.UserLogin;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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



    @GET("/users/me")
    Call<UserInfo> getAuthorizedDriver(@Header("Authorization") String token);

    @GET("/news")
    Call<ArrayList<New>> getAllNew(@Header("Authorization") String token);


    @POST("/news")
    Call<Void> createNew(@Header("Authorization") String token, @Body NewsCreate newsCreate);





}