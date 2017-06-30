package com.entreprise.davfou.projetandroidesgi.data.method.retrofit;

/**
 * Created by davidfournier on 28/02/2017.
 */


import com.entreprise.davfou.projetandroidesgi.data.modelRest.News;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.NewsCreate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface NewsInterface {
    public static final String ENDPOINT = "https://esgi-2017.herokuapp.com";



    @GET("/news")
    Call<ArrayList<News>> getAllNew(@Header("Authorization") String token);


    @POST("/news")
    Call<Void> createNew(@Header("Authorization") String token, @Body NewsCreate newsCreate);





}