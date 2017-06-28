package com.entreprise.davfou.projetandroidesgi.bussiness.news;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.ApiInterface;
import com.entreprise.davfou.projetandroidesgi.data.method.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.New;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.news.ListNewsFragment;
import com.entreprise.davfou.projetandroidesgi.ui.utils.ProgressDialog;

import java.util.ArrayList;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by davidfournier on 28/06/2017.
 */

public class ManageNews {

    Context context;
    Activity activityReference;
    android.app.ProgressDialog progressDialog;
    Realm realm;



    public ManageNews(Context context, Activity activityReference) {
        this.context = context;
        this.activityReference = activityReference;
        realm = RealmController.with(activityReference).getRealm();

    }


    public void getAllNews(UserRealm userRealm){



        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteLogin), context);
        progressDialog.show();
        ApiInterface apiInterface = ClientRetrofit.getClient();

        Call<ArrayList<New>> call = apiInterface.getAllNew("Bearer "+userRealm.getToken());
        call.enqueue(new Callback<ArrayList<New>>() {
            @Override
            public void onResponse(Call<ArrayList<New>> call, final Response<ArrayList<New>> response) {
                progressDialog.dismiss();
                System.out.println("news : " + response.code());
                System.out.println("news : " + response.raw().toString());
                System.out.println("news : "+response.body().toString());


                if (response.code() == 200) {
                    System.out.println("news size : " + response.body().size());







                        //createOrUpdateNewRealm(response.body());




                    ListNewsFragment.setRecycler(response.body());


                } else {
                    Toast.makeText(context,context.getString(R.string.msgErrorLogin),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<New>> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("call : " + t.getMessage().toString());
                System.out.println("response :" + t.toString());
                Toast.makeText(context,context.getString(R.string.msgErrorNetwork),Toast.LENGTH_SHORT).show();


            }
        });





    }



    /*private void createOrUpdateNewRealm(ArrayList<New> newArrayList){



        for(int i = 0; i< newArrayList.size();i++){

            //    public NewRealm(long id, String _id, String author, String content, String title) {



            NewRealm newRealm = new NewRealm("")
        }
        Object obj = new Object();
        obj.setField1(field1);
        obj.setField2(field2);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();

    }*/ 

}
