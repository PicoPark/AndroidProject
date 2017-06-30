package com.entreprise.davfou.projetandroidesgi.data.newArchi;

import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.UserInterface;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.User;

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
    public void read(String userID, IServiceResultListener<User> resultListener) {

    }
}
