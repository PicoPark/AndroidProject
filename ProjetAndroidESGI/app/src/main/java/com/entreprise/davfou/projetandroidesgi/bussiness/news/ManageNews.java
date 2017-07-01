package com.entreprise.davfou.projetandroidesgi.bussiness.news;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.realm.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.method.retrofit.NewsInterface;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.NewsRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.News;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.NewsCreate;
import com.entreprise.davfou.projetandroidesgi.data.newsservice.NewsService;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceResult;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.news.ListNewsFragment;
import com.entreprise.davfou.projetandroidesgi.ui.utils.ProgressDialog;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Retrofit;

/**
 * Created by davidfournier on 28/06/2017.
 */

public class ManageNews {

    Context context;
    Activity activityReference;
    android.app.ProgressDialog progressDialog;
    Realm realm;
    NewsInterface newsInterface;
    NewsService newsService;



    public ManageNews(Context context, Activity activityReference) {
        this.context = context;
        this.activityReference = activityReference;
        realm = RealmController.with(activityReference).getRealm();
        Retrofit retrofit = ClientRetrofit.getClient();
        newsInterface= retrofit.create(NewsInterface.class);
        newsService = new NewsService();

    }



    public void updateNews(News news,UserRealm userRealm){
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
        progressDialog.show();
        newsService.updateNews(userRealm, news, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {
                    System.out.println("reussi");
                }else{
                    System.out.println("rate");
                }
            }
        });
    }



    public void deleteNews(News news,UserRealm userRealm){
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
        progressDialog.show();
        newsService.deleteNews(userRealm, news, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {
                    System.out.println("reussi");
                }else{
                    System.out.println("rate");
                }
            }
        });

    }


    public void createNew(NewsCreate newsCreate, final UserRealm userRealmIn){

        //create news
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
        progressDialog.show();

        newsService.create(newsCreate, userRealmIn, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {
                    getAllNews(userRealmIn);
                }
            }
        });

    }



    public void getAllNews(UserRealm userRealm){

        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
        progressDialog.show();


        newsService.getAll(userRealm, new IServiceResultListener<ArrayList<News>>() {
            @Override
            public void onResult(ServiceResult<ArrayList<News>> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {

                    createOrUpdateNewRealm(result.getmData());

                    ListNewsFragment.setRecycler(result.getmData(),activityReference);

                } else {
                    Toast.makeText(context,context.getString(R.string.msgErrorNetworkNews),Toast.LENGTH_SHORT).show();

                    ListNewsFragment.setRecyclerOffline(getAllNewInRealm(),activityReference);
                }
            }
        });

    }



    private ArrayList<News> getAllNewInRealm(){


        RealmResults<NewsRealm> newsRealms = RealmController.getInstance().getNews();
        ArrayList<News> news =new ArrayList();


        for(int i = 0; i< newsRealms.size(); i++){
            news.add(new News(newsRealms.get(i).get_id(), newsRealms.get(i).getAuthor(), newsRealms.get(i).getContent(), newsRealms.get(i).getTitle()));
        }

        return news;
    }


    private void createOrUpdateNewRealm(ArrayList<News> newsArrayList){

        for(int i = 0; i< newsArrayList.size(); i++){

            NewsRealm newsRealm = new NewsRealm(i, newsArrayList.get(i).get_id(), newsArrayList.get(i).getAuthor(), newsArrayList.get(i).getContent(), newsArrayList.get(i).getTitle());
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(newsRealm);
            realm.commitTransaction();
        }

    }

}
