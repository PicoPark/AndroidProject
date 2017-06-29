package com.entreprise.davfou.projetandroidesgi.bussiness.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Explode;
import android.widget.Toast;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.UserInterface;
import com.entreprise.davfou.projetandroidesgi.data.method.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.User;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.UserInfo;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.UserLogin;
import com.entreprise.davfou.projetandroidesgi.ui.activity.login.FirstActivity;
import com.entreprise.davfou.projetandroidesgi.ui.activity.login.LoginSuccessActivity;
import com.entreprise.davfou.projetandroidesgi.ui.utils.ProgressDialog;

import org.json.JSONObject;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by davidfournier on 04/06/2017.
 */

public class ConnectUser {

    Context context;
    Activity activityReference;
    android.app.ProgressDialog progressDialog;
    Realm realm;
    UserInterface userInterface;


    public ConnectUser(Context context, Activity activityReference) {
        this.context = context;
        this.activityReference = activityReference;
        realm = RealmController.with(activityReference).getRealm();
        Retrofit retrofit = ClientRetrofit.getClient();
        userInterface = retrofit.create(UserInterface.class);

    }

    public void logout() {

        final UserRealm userRealm = RealmController.getInstance().getUserConnected(true);

        if (!(userRealm == null)) {


            realm.executeTransaction(new Realm.Transaction() {
                public void execute(Realm realm) {
                    userRealm.setConnected(false);
                    userRealm.setKeepConnection(false);
                    realm.copyToRealm(userRealm); // could be copyToRealmOrUpdate
                    goToLogin();

                }

            });



        }

    }

    public UserRealm getUserConnected() {
        return RealmController.getInstance().getUserConnected(true);
    }


    public void isConnected() {

        UserRealm userRealm = RealmController.getInstance().getUserConnected(true);

        if (!(userRealm == null)) {
            System.out.println("user : " + userRealm.isConnected());

            ConnectUser connectUser = new ConnectUser(context, activityReference);
            if (userRealm.getFirstName().equals("")) {
                connectUser.getInfo(userRealm);

            }

            goToMain();

        }


    }

    public void getInfo(final UserRealm userRealm) {

        final UserInfo[] userInfo = new UserInfo[1];
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteLogin), context);
        progressDialog.show();


        Call<UserInfo> call = userInterface.getAuthorizedDriver("Bearer " + userRealm.getToken());
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, final Response<UserInfo> response) {
                progressDialog.dismiss();
                System.out.println("response : " + response.raw().toString());

                if (response.code() == 200) {


                    realm.executeTransaction(new Realm.Transaction() {
                        public void execute(Realm realm) {
                            userRealm.setFirstName(response.body().getFirstname());
                            userRealm.setLastName(response.body().getLastname());
                            realm.copyToRealmOrUpdate(userRealm);

                        }

                    });


                } else {
                    Toast.makeText(context, context.getString(R.string.msgErrorLogin), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("call : " + t.getMessage().toString());
                System.out.println("response :" + t.toString());
                Toast.makeText(context, context.getString(R.string.msgErrorNetwork), Toast.LENGTH_SHORT).show();


            }
        });


    }

    public void connect(final String email, final String password, final boolean stayConneted) {

        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteLogin), context);
        progressDialog.show();


        Call<String> call = userInterface.connectUser(new UserLogin(email, password));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, final Response<String> response) {
                progressDialog.dismiss();
                System.out.println("user : " + response.code());
                System.out.println("user : " + response.raw().toString());
                System.out.println("user : " + response.body().toString());


                if (response.code() == 200) {

                    final boolean connected = stayConneted;


                    //on verifie si il y a des user qui se sont déja connecté avec ce device
                    // si jamais, on en creer un avec id = 1
                    //sinon on verifie si il existe pas déja pour update le token et mettre isConnected a true sinon on en creer un  nouveau avec id = total + 1

                    realm.executeTransaction(new Realm.Transaction() { // must be in transaction for this to work
                        @Override
                        public void execute(Realm realm) {
                            // increment index
                            System.out.println("compte : " + realm.where(UserRealm.class).count());
                            Number currentIdNum = realm.where(UserRealm.class).count();
                            int nextId;
                            if (currentIdNum == null) {
                                nextId = 1;
                                UserRealm userRealm = new UserRealm(nextId, email, password, response.body().toString(), "", "", true, connected); // unmanaged
                                userRealm.setId(nextId);
                                realm.copyToRealm(userRealm); // using insert API

                                getInfo(userRealm);
                            } else {
                                UserRealm userR = RealmController.getInstance().getUser(email);

                                if (userR == null) {
                                    nextId = currentIdNum.intValue() + 1;
                                    UserRealm userRealm = new UserRealm(nextId, email, password, response.body().toString(), "", "", true, connected); // unmanaged
                                    userRealm.setId(nextId);
                                    realm.copyToRealm(userRealm); // using insert API
                                    getInfo(userRealm);

                                } else {
                                    System.out.println("update user : " + userR.toString());
                                    userR.setConnected(connected);
                                    userR.setToken(response.body().toString());
                                    realm.copyToRealm(userR);

                                }


                            }

                        }
                    });

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                        goToMainAnimation();
                    } else {
                        goToMain();
                    }

                } else {
                    Toast.makeText(context, context.getString(R.string.msgErrorLogin), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("call : " + t.getMessage().toString());
                System.out.println("response :" + t.toString());
                Toast.makeText(context, context.getString(R.string.msgErrorNetwork), Toast.LENGTH_SHORT).show();


            }
        });


    }


    public void register(final User user) {

        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteInscription), context);
        progressDialog.show();

        Call<JSONObject> call = userInterface.createUser(user);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, final Response<JSONObject> response) {
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
                            if (currentIdNum == null) {
                                nextId = 1;
                                UserRealm userRealm = new UserRealm(nextId, user.getEmail(), user.getPassword(), response.body().toString(), user.getFirstname(), user.getLastname(), true, true); // unmanaged
                                userRealm.setId(nextId);
                                realm.copyToRealm(userRealm); // using insert API
                            } else {
                                UserRealm userR = RealmController.getInstance().getUser(user.getEmail());

                                if (userR == null) {
                                    nextId = currentIdNum.intValue() + 1;
                                    UserRealm userRealm = new UserRealm(nextId, user.getEmail(), user.getPassword(), response.body().toString(), user.getFirstname(), user.getLastname(), true, true); // unmanaged
                                    userRealm.setId(nextId);
                                    realm.copyToRealm(userRealm); // using insert API
                                }
                            }

                        }
                    });

                    Toast.makeText(context, context.getString(R.string.textInscriptionReussi), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, context.getString(R.string.textInscriptionError), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("response :" + t.toString());
                Toast.makeText(context, context.getString(R.string.msgErrorNetwork), Toast.LENGTH_SHORT).show();


            }
        });


    }


    private void goToMain() {

        Intent goToMain = new Intent(context, LoginSuccessActivity.class);
        context.startActivity(goToMain);

    }

    private void goToLogin() {

        Intent goToLogin = new Intent(context, FirstActivity.class);
        context.startActivity(goToLogin);

    }

    private void goToMainAnimation() {


        Explode explode = new Explode();

        activityReference.getWindow().setExitTransition(explode);
        activityReference.getWindow().setEnterTransition(explode);
        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(activityReference);
        Intent i2 = new Intent(activityReference.getApplicationContext(), LoginSuccessActivity.class);
        activityReference.startActivity(i2, oc2.toBundle());
        explode.setDuration(500);

    }
}
