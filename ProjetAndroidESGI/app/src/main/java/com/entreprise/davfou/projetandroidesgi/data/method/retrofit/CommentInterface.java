package com.entreprise.davfou.projetandroidesgi.data.method.retrofit;

import com.entreprise.davfou.projetandroidesgi.data.modelRest.Comment;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.CommentCreate;

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
 * Created by Pico on 06/07/2017.
 */

public interface CommentInterface {

    public static final String ENDPOINT = "https://esgi-2017.herokuapp.com";



    @GET("/comments")
    Call<ArrayList<Comment>> getAllComment(@Header("Authorization") String token);


    @POST("/comments")
    Call<String> createComment(@Header("Authorization") String token, @Body CommentCreate commentCreate);


    @DELETE("/comments/{id}")
    Call<String> deleteComment(@Header("Authorization") String token,@Path("id") String id);


    @PUT("/comments/{id}")
    Call<String> updateComment(@Header("Authorization") String token,@Path("id") String id,@Body CommentCreate commentCreate);


}
