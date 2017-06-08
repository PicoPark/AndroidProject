package com.entreprise.davfou.projetandroidesgi.bussiness.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Explode;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.methodRest.ApiInterface;
import com.entreprise.davfou.projetandroidesgi.data.model.UserLogin;
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
    String  result="";



    public ConnectUser(Context context, Activity activityReference) {
        this.context = context;
        this.activityReference = activityReference;
    }


    public String connect(String email, String password) {
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteLogin), context);
        progressDialog.show();
        ApiInterface apiInterface = ClientRetrofit.getClient();

        Call<String> call = apiInterface.connectUser(new UserLogin(email,password));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                System.out.println("user : " + response.code());
                System.out.println("user : " + response.raw().toString());
                System.out.println("user : " + response.body().toString());



                if (response.code() == 200) {

                    Explode explode = new Explode();
                    explode.setDuration(500);

                    activityReference.getWindow().setExitTransition(explode);
                    activityReference.getWindow().setEnterTransition(explode);
                    ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(activityReference);
                    Intent i2 = new Intent(activityReference.getApplicationContext(), LoginSuccessActivity.class);
                    activityReference.startActivity(i2, oc2.toBundle());

                    result="";
                } else {
                    result=context.getString(R.string.msgErrorLogin);

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("call : " + t.getMessage().toString());
                System.out.println("response :" + t.toString());
                result=context.getString(R.string.msgErrorNetwork);


            }
        });


        return result=context.getString(R.string.msgErrorNetwork);


    }
}
