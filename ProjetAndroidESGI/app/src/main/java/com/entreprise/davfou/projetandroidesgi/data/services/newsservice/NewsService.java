package com.entreprise.davfou.projetandroidesgi.data.services.newsservice;

import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.retrofit.NewsInterface;
import com.entreprise.davfou.projetandroidesgi.data.model.local.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.model.api.News;
import com.entreprise.davfou.projetandroidesgi.data.model.api.NewsCreate;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceException;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceExceptionType;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by davidfournier on 29/06/2017.
 */

public class NewsService implements INewsInterface {
    private NewsInterface mRfuserService;


    private NewsInterface getmRfnewsService() {
        if (mRfuserService == null)
            mRfuserService = ClientRetrofit.getClient().create(NewsInterface.class);
        return mRfuserService;
    }


    @Override
    public void create(NewsCreate newsCreate, UserRealm userRealm, final IServiceResultListener<String> resultListener) {

        Call<String> call = getmRfnewsService().createNew("Bearer "+userRealm.getToken(),newsCreate);
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
    public void getAll(UserRealm userRealm, final IServiceResultListener<ArrayList<News>> resultListener) {

        Call<ArrayList<News>> call = getmRfnewsService().getAllNew("Bearer "+userRealm.getToken());
        call.enqueue(new Callback<ArrayList<News>>() {
            @Override
            public void onResponse(Call<ArrayList<News>> call, final Response<ArrayList<News>> response) {
                ServiceResult<ArrayList<News>> result = new ServiceResult<>();
                if(response.code() == 200)
                    result.setmData(response.body());
                else
                    result.setmError(new ServiceException(response.code()));
                if(resultListener != null)
                    resultListener.onResult(result);
            }

            @Override
            public void onFailure(Call<ArrayList<News>> call, Throwable t) {

                ServiceResult<ArrayList<News>> result = new ServiceResult<>();
                result.setmError(new ServiceException(t, ServiceExceptionType.UNKNOWN));
                if(resultListener != null)
                    resultListener.onResult(result);

            }
        });


    }

    @Override
    public void deleteNews(UserRealm userRealm,News news,final IServiceResultListener<String> resultListener) {
        Call<String> call = getmRfnewsService().deleteNews("Bearer "+userRealm.getToken(),news.get_id());
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
    public void updateNews(UserRealm userRealm,NewsCreate newsCreate,News news,final IServiceResultListener<String> resultListener) {
        Call<String> call = getmRfnewsService().updateNews("Bearer "+userRealm.getToken(),news.get_id(),newsCreate);
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
