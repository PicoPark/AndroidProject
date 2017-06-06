package com.entreprise.davfou.projetandroidesgi.bussiness.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Explode;

import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.methodRest.ApiInterface;
import com.entreprise.davfou.projetandroidesgi.ui.activity.login.LoginSuccessActivity;
import com.entreprise.davfou.projetandroidesgi.ui.utils.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by davidfournier on 04/06/2017.
 */

public class ConnectUser {

    Context context;
    Activity activityReference;
    android.app.ProgressDialog progressDialog;
    String result="Pas d'acces réseau";


    public ConnectUser(Context context, Activity activityReference) {
        this.context = context;
        this.activityReference = activityReference;
    }


    public String connect(String nom, String prenom) {
        progressDialog = ProgressDialog.getProgress("test", "test", context);
        progressDialog.show();
        ApiInterface apiInterface = ClientRetrofit.getClient();

        Call<String> call = apiInterface.connectUser(nom, prenom);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                System.out.println("user : " + response.code());
                System.out.println("user : " + response.raw().toString());


                if (response.code() == 200) {

                    Explode explode = new Explode();
                    explode.setDuration(500);

                    activityReference.getWindow().setExitTransition(explode);
                    activityReference.getWindow().setEnterTransition(explode);
                    ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(activityReference);
                    Intent i2 = new Intent(activityReference.getApplicationContext(), LoginSuccessActivity.class);
                    activityReference.startActivity(i2, oc2.toBundle());
                } else {
                    result="email et/ou password faux";

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("call : " + t.getMessage().toString());
                System.out.println("response :" + t.toString());
                result="erreur réseau";


            }
        });


        return result;


    }
}
