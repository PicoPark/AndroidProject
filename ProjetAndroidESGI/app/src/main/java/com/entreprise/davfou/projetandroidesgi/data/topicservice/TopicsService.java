package com.entreprise.davfou.projetandroidesgi.data.topicservice;

import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.retrofit.TopicsInterface;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Topic;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.TopicCreate;
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

public class TopicsService implements ITopicsInterface {
    private TopicsInterface mRftopicsService;


    private TopicsInterface getmRftopicsService() {
        if (mRftopicsService == null)
            mRftopicsService = ClientRetrofit.getClient().create(TopicsInterface.class);
        return mRftopicsService;
    }


    @Override
    public void create(TopicCreate topicCreate, UserRealm userRealm, final IServiceResultListener<String> resultListener) {


        Call<String> call = getmRftopicsService().createTopic("Bearer "+userRealm.getToken(),topicCreate);
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
    public void getAll(UserRealm userRealm, final IServiceResultListener<ArrayList<Topic>> resultListener) {

        Call<ArrayList<Topic>> call = getmRftopicsService().getAllTopics("Bearer "+userRealm.getToken());
        call.enqueue(new Callback<ArrayList<Topic>>() {
            @Override
            public void onResponse(Call<ArrayList<Topic>> call, Response<ArrayList<Topic>> response) {
                ServiceResult<ArrayList<Topic>> result = new ServiceResult<>();
                if(response.code() == 200)
                    result.setmData(response.body());
                else
                    result.setmError(new ServiceException(response.code()));
                if(resultListener != null)
                    resultListener.onResult(result);
            }

            @Override
            public void onFailure(Call<ArrayList<Topic>> call, Throwable t) {
                ServiceResult<ArrayList<Topic>> result = new ServiceResult<>();
                result.setmError(new ServiceException(t, ServiceExceptionType.UNKNOWN));
                if(resultListener != null)
                    resultListener.onResult(result);
            }
        });
    }

    @Override
    public void deleteTopic(UserRealm userRealm,Topic topic, final IServiceResultListener<String> resultListener) {
        Call<String> call = getmRftopicsService().deleteTopic("Bearer "+userRealm.getToken(),topic.get_id());
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
    public void updateTopic(UserRealm userRealm, TopicCreate topicCreate,Topic topic, final IServiceResultListener<String> resultListener) {
        Call<String> call = getmRftopicsService().updateTopics("Bearer "+userRealm.getToken(),topic.get_id(),topicCreate);
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
