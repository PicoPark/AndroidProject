package com.entreprise.davfou.projetandroidesgi.bussiness.news;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.ApiInterface;
import com.entreprise.davfou.projetandroidesgi.data.method.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.NewRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.New;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.NewsCreate;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.news.ListNewsFragment;
import com.entreprise.davfou.projetandroidesgi.ui.utils.ProgressDialog;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
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


    public void createNew(NewsCreate newsCreate, final UserRealm userRealmIn){

        //create news
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
        progressDialog.show();


        ApiInterface apiInterface = ClientRetrofit.getClient();
        System.out.println("token : "+userRealmIn.getToken());


        final Call<Void> call = apiInterface.createNew("Bearer "+userRealmIn.getToken(),newsCreate);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, final Response<Void> response) {
                progressDialog.dismiss();
                System.out.println("news : " + response.code());
                System.out.println("news : " + response.raw().toString());
                //System.out.println("news : "+response.body().toString());


                if (response.code() == 201) {

                    getAllNews(userRealmIn);

                } else if(response.code() == 200){

                    Toast.makeText(context,context.getString(R.string.msgErrorNewsAlreadyExist),Toast.LENGTH_SHORT).show();

                } else{
                    
                    Toast.makeText(context,context.getString(R.string.msgErrorNewsCreate),Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("call : " + t.getMessage().toString());
                System.out.println("response :" + t.toString());
                Toast.makeText(context,context.getString(R.string.msgErrorNetworkNews),Toast.LENGTH_SHORT).show();


            }
        });

    }



    public void getAllNews(UserRealm userRealm){



        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
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

                        createOrUpdateNewRealm(response.body());

                    ListNewsFragment.setRecycler(response.body(),activityReference);

                } else {
                    Toast.makeText(context,context.getString(R.string.msgErrorNews),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<New>> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("call : " + t.getMessage().toString());
                System.out.println("response :" + t.toString());
                Toast.makeText(context,context.getString(R.string.msgErrorNetworkNews),Toast.LENGTH_SHORT).show();

                ListNewsFragment.setRecyclerOffline(getAllNewInRealm(),activityReference);

            }
        });





    }



    private ArrayList<New> getAllNewInRealm(){


        RealmResults<NewRealm> newRealms = RealmController.getInstance().getNews();
        ArrayList<New> news =new ArrayList();



        for(int i =0;i<newRealms.size();i++){
            news.add(new New(newRealms.get(i).get_id(),newRealms.get(i).getAuthor(),newRealms.get(i).getContent(),newRealms.get(i).getTitle()));
        }



        return news;
    }


    private void createOrUpdateNewRealm(ArrayList<New> newArrayList){

        for(int i = 0; i< newArrayList.size();i++){

            NewRealm newRealm = new NewRealm(i,newArrayList.get(i).get_id(),newArrayList.get(i).getAuthor(),newArrayList.get(i).getContent(),newArrayList.get(i).getTitle());
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(newRealm);
            realm.commitTransaction();
        }



    }

}
