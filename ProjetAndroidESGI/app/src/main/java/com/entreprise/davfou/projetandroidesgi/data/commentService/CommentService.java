package com.entreprise.davfou.projetandroidesgi.data.commentService;

import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.retrofit.CommentInterface;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Comment;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.CommentCreate;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceException;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceExceptionType;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pico on 06/07/2017.
 */

public class CommentService implements ICommentInterface {
    private CommentInterface mRfcommentService;

    private CommentInterface getmRfcommentService() {
        if (mRfcommentService == null)
            mRfcommentService = ClientRetrofit.getClient().create(CommentInterface.class);
        return mRfcommentService;
    }

    @Override
    public void create(CommentCreate commentCreate, UserRealm userRealm, final IServiceResultListener<String> resultListener) {

        Call<String> call = getmRfcommentService().createComment("Bearer "+userRealm.getToken(),commentCreate);
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
    public void getAllComment(UserRealm userRealm, final IServiceResultListener<ArrayList<Comment>> resultListener) {

        Call<ArrayList<Comment>> call = getmRfcommentService().getAllComment("Bearer "+userRealm.getToken());
        call.enqueue(new Callback<ArrayList<Comment>>() {
            @Override
            public void onResponse(Call<ArrayList<Comment>> call, final Response<ArrayList<Comment>> response) {
                ServiceResult<ArrayList<Comment>> result = new ServiceResult<>();
                if(response.code() == 200)
                    result.setmData(response.body());
                else
                    result.setmError(new ServiceException(response.code()));
                if(resultListener != null)
                    resultListener.onResult(result);
            }

            @Override
            public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {

                ServiceResult<ArrayList<Comment>> result = new ServiceResult<>();
                result.setmError(new ServiceException(t, ServiceExceptionType.UNKNOWN));
                if(resultListener != null)
                    resultListener.onResult(result);

            }
        });

    }

    @Override
    public void deleteComment(UserRealm userRealm, Comment comment, final IServiceResultListener<String> resultListener) {
        Call<String> call = getmRfcommentService().deleteComment("Bearer "+userRealm.getToken(),comment.get_id());
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
    public void updateComment(UserRealm userRealm, CommentCreate commentCreate, Comment comment, final IServiceResultListener<String> resultListener) {
        Call<String> call = getmRfcommentService().updateComment("Bearer "+userRealm.getToken(),comment.get_id(),commentCreate);
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
