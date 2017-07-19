package com.entreprise.davfou.projetandroidesgi.data.method.retrofit;

/**
 * Created by davidfournier on 28/02/2017.
 */


import com.entreprise.davfou.projetandroidesgi.data.model.api.News;
import com.entreprise.davfou.projetandroidesgi.data.model.api.NewsCreate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface NewsInterface {
    public static final String ENDPOINT = "https://esgi-2017.herokuapp.com";



    @GET("/news")
    Call<ArrayList<News>> getAllNew(@Header("Authorization") String token);


    @POST("/news")
    Call<String> createNew(@Header("Authorization") String token, @Body NewsCreate newsCreate);


    @DELETE("/news/{id}")
    Call<String> deleteNews(@Header("Authorization") String token,@Path("id") String id);


    @PUT("/news/{id}")
    Call<String> updateNews(@Header("Authorization") String token,@Path("id") String id,@Body NewsCreate newsCreate);


}