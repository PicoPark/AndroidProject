package com.entreprise.davfou.projetandroidesgi.bussiness.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Explode;
import android.widget.Toast;

import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.methodRest.ApiInterface;
import com.entreprise.davfou.projetandroidesgi.data.model.User;
import com.entreprise.davfou.projetandroidesgi.ui.activity.login.LoginSuccessActivity;
import com.entreprise.davfou.projetandroidesgi.ui.utils.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by davidfournier on 05/06/2017.
 */

public class RegisterUser {
    Context context;
    Activity activityReference;
    android.app.ProgressDialog progressDialog;

    public RegisterUser(Context context, Activity activityReference) {
        this.context = context;
        this.activityReference = activityReference;
    }

    public String register(User user) {

        progressDialog = ProgressDialog.getProgress("test", "test", context);
        progressDialog.show();
        ApiInterface apiInterface = ClientRetrofit.getClient();

        Call<String> call = apiInterface.createUser(user);
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
                    Intent i2 = new Intent(context, LoginSuccessActivity.class);
                    activityReference.startActivity(i2, oc2.toBundle());
                } else {
                    Toast.makeText(context, "erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("response :" + t.toString());

                Toast.makeText(context, "Erreur lors de la création de l'utilisateur", Toast.LENGTH_SHORT).show();

            }
        });


        return "non";


    }
}

