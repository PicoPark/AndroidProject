package com.entreprise.davfou.projetandroidesgi.bussiness.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Explode;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.ApiInterface;
import com.entreprise.davfou.projetandroidesgi.data.method.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.User;
import com.entreprise.davfou.projetandroidesgi.ui.activity.login.LoginSuccessActivity;
import com.entreprise.davfou.projetandroidesgi.ui.utils.ProgressDialog;

import org.json.JSONObject;

import io.realm.Realm;
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

    public String register(final User user) {

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

                    Realm realm = RealmController.with(activityReference).getRealm();


                    //on verifie si il y a des user qui se sont déja connecté avec ce device
                    // si jamais, on en creer un avec id = 1
                    //ou si Il n'existe pas dans la BDD local on en creer un  nouveau avec id = total + 1

                    realm.executeTransaction(new Realm.Transaction() { // must be in transaction for this to work
                        @Override
                        public void execute(Realm realm) {
                            // increment index
                            System.out.println("compte : "+realm.where(UserRealm.class).count());
                            Number currentIdNum = realm.where(UserRealm.class).count();
                            int nextId;
                            if(currentIdNum == null) {
                                nextId = 1;
                                UserRealm userRealm = new UserRealm(nextId,user.getEmail(),user.getPassword(),"",false); // unmanaged
                                userRealm.setId(nextId);
                                realm.copyToRealm(userRealm); // using insert API
                            } else {
                                UserRealm userR = RealmController.getInstance().getUser(user.getEmail());

                                if(userR == null){
                                    nextId = currentIdNum.intValue() + 1;
                                    UserRealm userRealm = new UserRealm(nextId,user.getEmail(),user.getPassword(),"",false); // unmanaged
                                    userRealm.setId(nextId);
                                    realm.copyToRealm(userRealm); // using insert API
                                }
                            }

                        }
                    });

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

