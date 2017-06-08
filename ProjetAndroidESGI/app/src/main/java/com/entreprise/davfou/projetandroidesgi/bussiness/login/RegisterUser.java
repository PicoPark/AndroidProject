package com.entreprise.davfou.projetandroidesgi.bussiness.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Explode;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.methodRest.ApiInterface;
import com.entreprise.davfou.projetandroidesgi.data.model.User;
import com.entreprise.davfou.projetandroidesgi.ui.activity.login.LoginSuccessActivity;
import com.entreprise.davfou.projetandroidesgi.ui.utils.ProgressDialog;

import org.json.JSONObject;

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
    String  result="";

    public RegisterUser(Context context, Activity activityReference) {
        this.context = context;
        this.activityReference = activityReference;
    }

    public String register(User user) {

        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteInscription), context);
        progressDialog.show();
        ApiInterface apiInterface = ClientRetrofit.getClient();

        Call<JSONObject> call = apiInterface.createUser(user);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
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
                    result="";
                } else {
                    result=context.getString(R.string.msgErrorLogin);

                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("response :" + t.toString());
                result=context.getString(R.string.msgErrorNetwork);

            }
        });


        return result=context.getString(R.string.msgErrorNetwork);


    }
}

