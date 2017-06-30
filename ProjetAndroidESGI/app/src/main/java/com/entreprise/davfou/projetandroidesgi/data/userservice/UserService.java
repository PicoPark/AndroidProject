package com.entreprise.davfou.projetandroidesgi.data.userservice;

import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.retrofit.UserInterface;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.User;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.UserInfo;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.UserLogin;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceException;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceExceptionType;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by davidfournier on 29/06/2017.
 */

public class UserService implements IUserInterface {
    private UserInterface mRfuserService;


    private UserInterface getmRfuserService() {
        if (mRfuserService == null)
            mRfuserService = ClientRetrofit.getClient().create(UserInterface.class);
        return mRfuserService;
    }

    @Override
    public void create(User user, final IServiceResultListener<String> resultListener) {



        Call<Void> call = getmRfuserService().createUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, final Response<Void> response) {
                ServiceResult<String> result = new ServiceResult<>();
                if(response.code() == 201)
                    result.setmData(response.headers().get("Resourceuri"));
                else
                    result.setmError(new ServiceException(response.code()));
                if(resultListener != null)
                    resultListener.onResult(result);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                ServiceResult<String> result = new ServiceResult<>();
                result.setmError(new ServiceException(t, ServiceExceptionType.UNKNOWN));
                if(resultListener != null)
                    resultListener.onResult(result);

            }
        });


    }

    @Override
    public void connect(UserLogin userLogin, final IServiceResultListener<String> resultListener) {


        Call<String> call = getmRfuserService().connectUser(userLogin);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ServiceResult<String> result = new ServiceResult<>();
                if(response.code() == 200)
                    result.setmData(response.body());
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
    public void getInfo(UserRealm userRealm, final IServiceResultListener<UserInfo> resultListener) {


        Call<UserInfo> call = getmRfuserService().getAuthorizedDriver("Bearer "+userRealm.getToken());
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                ServiceResult<UserInfo> result = new ServiceResult<>();
                if(response.code() == 200)
                    result.setmData(response.body());
                else
                    result.setmError(new ServiceException(response.code()));
                if(resultListener != null)
                    resultListener.onResult(result);
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                ServiceResult<UserInfo> result = new ServiceResult<>();
                result.setmError(new ServiceException(t, ServiceExceptionType.UNKNOWN));
                if(resultListener != null)
                    resultListener.onResult(result);

            }
        });
    }
}
