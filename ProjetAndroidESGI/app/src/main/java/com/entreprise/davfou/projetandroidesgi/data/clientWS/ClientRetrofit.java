package com.entreprise.davfou.projetandroidesgi.data.clientWS;


import com.entreprise.davfou.projetandroidesgi.data.method.retrofit.UserInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by davidfournier on 04/06/2017.
 */

public  class ClientRetrofit {

    public ClientRetrofit() {
    }

    public static Retrofit getClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserInterface.ENDPOINT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }


}
