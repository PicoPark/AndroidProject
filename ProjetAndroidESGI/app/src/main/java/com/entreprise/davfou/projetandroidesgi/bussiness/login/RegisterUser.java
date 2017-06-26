package com.entreprise.davfou.projetandroidesgi.bussiness.login;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.ApiInterface;
import com.entreprise.davfou.projetandroidesgi.data.method.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.User;
import com.entreprise.davfou.projetandroidesgi.ui.utils.ProgressDialog;

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

    public RegisterUser(Context context, Activity activityReference) {
        this.context = context;
        this.activityReference = activityReference;
    }

    public void register(final User user) {

        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteInscription), context);
        progressDialog.show();
        ApiInterface apiInterface = ClientRetrofit.getClient();

        Call<String> call = apiInterface.createUser(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, final Response<String> response) {
                progressDialog.dismiss();
                System.out.println("user : " + response.raw().toString());

                if (response.code() == 201) {

                    System.out.println("user : " + response.body());

                    Realm realm = RealmController.with(activityReference).getRealm();


                    //on verifie si il y a des user qui se sont déja connecté avec ce device
                    // si jamais, on en creer un avec id = 1
                    //ou si Il n'existe pas dans la BDD local on en creer un  nouveau avec id = total + 1

                    realm.executeTransaction(new Realm.Transaction() { // must be in transaction for this to work
                        @Override
                        public void execute(Realm realm) {
                            // increment index
                            Number currentIdNum = realm.where(UserRealm.class).count();
                            int nextId;
                            if(currentIdNum == null) {
                                nextId = 1;
                                UserRealm userRealm = new UserRealm(nextId,user.getEmail(),user.getPassword(),response.body().toString(),user.getFirstname(),user.getLastname(),false); // unmanaged
                                userRealm.setId(nextId);
                                realm.copyToRealm(userRealm); // using insert API
                            } else {
                                UserRealm userR = RealmController.getInstance().getUser(user.getEmail());

                                if(userR == null){
                                    nextId = currentIdNum.intValue() + 1;
                                    UserRealm userRealm = new UserRealm(nextId,user.getEmail(),user.getPassword(),response.body().toString(),user.getFirstname(),user.getLastname(),false); // unmanaged
                                    userRealm.setId(nextId);
                                    realm.copyToRealm(userRealm); // using insert API
                                }
                            }

                        }
                    });

                    Toast.makeText(context,context.getString(R.string.textInscriptionReussi),Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context,context.getString(R.string.textInscriptionError),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("response :" + t.toString());
                Toast.makeText(context,context.getString(R.string.msgErrorNetwork),Toast.LENGTH_SHORT).show();


            }
        });




    }
}

