package com.entreprise.davfou.projetandroidesgi.data.method.retrofit;

import com.entreprise.davfou.projetandroidesgi.data.modelRest.Post;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.PostCreate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Pico on 04/07/2017.
 */

public interface PostInterface {
    public static final String ENDPOINT = "https://esgi-2017.herokuapp.com";


    @GET("/posts")
    Call<ArrayList<Post>> getAllPost(@Header("Authorization") String token);


    @POST("/posts")
    Call<String> createPost(@Header("Authorization") String token, @Body PostCreate topicCreate);


    @DELETE("/posts/{id}")
    Call<String> deletePost(@Header("Authorization") String token, @Path("id") String id);


    @PUT("/posts/{id}")
    Call<String> updatePost(@Header("Authorization") String token, @Path("id") String id, @Body PostCreate postCreate);


}
