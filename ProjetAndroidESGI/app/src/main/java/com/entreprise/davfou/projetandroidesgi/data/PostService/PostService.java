package com.entreprise.davfou.projetandroidesgi.data.PostService;


import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.retrofit.PostInterface;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Post;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.PostCreate;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceException;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceExceptionType;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pico on 04/07/2017.
 */

public class PostService implements IPostInterface {
    private PostInterface mRfpostService;

    private PostInterface getmRfpostService() {
        if (mRfpostService == null)
            mRfpostService = ClientRetrofit.getClient().create(PostInterface.class);
        return mRfpostService;
    }

    @Override
    public void create(PostCreate postCreate, UserRealm userRealm,final IServiceResultListener<String> resultListener) {

        Call<String> call = getmRfpostService().createPost("Bearer "+userRealm.getToken(),postCreate);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, final Response<String> response) {
                ServiceResult<String> result = new ServiceResult<>();
                if(response.code() == 201)
                    result.setmData(response.headers().get("Resourceuri"));
                else
                    result.setmError(new ServiceException(response.code()));
                if(resultListener != null)
                    resultListener.onResult(result);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                ServiceResult<String> result = new ServiceResult<>();
                result.setmError(new ServiceException(t, ServiceExceptionType.UNKNOWN));
                if(resultListener != null)
                    resultListener.onResult(result);

            }
        });

    }

    @Override
    public void getAll(UserRealm userRealm, final IServiceResultListener<ArrayList<Post>> resultListener) {

        Call<ArrayList<Post>> call = getmRfpostService().getAllPost("Bearer "+userRealm.getToken());
        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                ServiceResult<ArrayList<Post>> result = new ServiceResult<>();
                if(response.code() == 200)
                    result.setmData(response.body());
                else
                    result.setmError(new ServiceException(response.code()));
                if(resultListener != null)
                    resultListener.onResult(result);
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                ServiceResult<ArrayList<Post>> result = new ServiceResult<>();
                result.setmError(new ServiceException(t, ServiceExceptionType.UNKNOWN));
                if(resultListener != null)
                    resultListener.onResult(result);
            }
        });
    }

    @Override
    public void deletePost(UserRealm userRealm, Post post, final IServiceResultListener<String> resultListener) {
        Call<String> call = getmRfpostService().deletePost("Bearer "+userRealm.getToken(),post.get_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                ServiceResult<String> result = new ServiceResult<>();
                if(response.code() == 204)
                    result.setmData(response.headers().get("Resourceuri"));
                else
                    result.setmError(new ServiceException(response.code()));
                if(resultListener != null)
                    resultListener.onResult(result);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ServiceResult<String> result = new ServiceResult<>();
                result.setmError(new ServiceException(t, ServiceExceptionType.UNKNOWN));
                if(resultListener != null)
                    resultListener.onResult(result);
            }
        });
    }

    @Override
    public void updatePost(UserRealm userRealm, PostCreate postCreate, Post post, final IServiceResultListener<String> resultListener) {
        Call<String> call = getmRfpostService().updatePost("Bearer "+userRealm.getToken(),post.get_id(),postCreate);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ServiceResult<String> result = new ServiceResult<>();
                if(response.code() == 204)
                    result.setmData(response.headers().get("Resourceuri"));
                else
                    result.setmError(new ServiceException(response.code()));
                if(resultListener != null)
                    resultListener.onResult(result);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ServiceResult<String> result = new ServiceResult<>();
                result.setmError(new ServiceException(t, ServiceExceptionType.UNKNOWN));
                if(resultListener != null)
                    resultListener.onResult(result);
            }
        });
    }
}

