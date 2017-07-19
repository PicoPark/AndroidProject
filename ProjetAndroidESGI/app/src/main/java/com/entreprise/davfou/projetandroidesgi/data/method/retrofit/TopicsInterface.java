package com.entreprise.davfou.projetandroidesgi.data.method.retrofit;

/**
 * Created by davidfournier on 28/02/2017.
 */


import com.entreprise.davfou.projetandroidesgi.data.model.api.Topic;
import com.entreprise.davfou.projetandroidesgi.data.model.api.TopicCreate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface TopicsInterface {
    public static final String ENDPOINT = "https://esgi-2017.herokuapp.com";



    @GET("/topics")
    Call<ArrayList<Topic>> getAllTopics(@Header("Authorization") String token);


    @POST("/topics")
    Call<String> createTopic(@Header("Authorization") String token, @Body TopicCreate topicCreate);


    @DELETE("/topics/{id}")
    Call<String> deleteTopic(@Header("Authorization") String token, @Path("id") String id);


    @PUT("/topics/{id}")
    Call<String> updateTopics(@Header("Authorization") String token, @Path("id") String id, @Body TopicCreate topicCreate);


}