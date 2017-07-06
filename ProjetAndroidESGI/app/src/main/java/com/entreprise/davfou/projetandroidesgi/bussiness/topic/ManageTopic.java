package com.entreprise.davfou.projetandroidesgi.bussiness.topic;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.realm.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.method.retrofit.TopicsInterface;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.PostRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.TopicRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Topic;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.TopicCreate;
import com.entreprise.davfou.projetandroidesgi.data.topicservice.TopicsService;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceResult;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.topics.ListTopicsFragment;
import com.entreprise.davfou.projetandroidesgi.ui.utils.ProgressDialog;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Retrofit;

/**
 * Created by Pico on 30/06/2017.
 */

public class ManageTopic {

    Context context;
    Activity activityReference;
    android.app.ProgressDialog progressDialog;
    Realm realm;
    TopicsInterface topicInterface;
    TopicsService topicService;




    public void updateTopic(Topic topic, UserRealm userRealm){
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
        progressDialog.show();
        topicService.updateTopic(userRealm,new TopicCreate(topic.getContent(),topic.getTitle(),topic.getDate()), topic, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {
                    System.out.println("reussi");


                    Toast.makeText(context,context.getString(R.string.msgSuccesUpdNews),Toast.LENGTH_SHORT).show();

                }else{

                    if(result.getmError().getCode()==404){
                        Toast.makeText(context,context.getString(R.string.msgErrorDelTopic),Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context,context.getString(R.string.msgErrorNetwork),Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }


    public void deleteTopic(final Topic topic, UserRealm userRealm){
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteTopics), context);
        progressDialog.show();
        topicService.deleteTopic(userRealm, topic, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {
                    System.out.println("reussi");


                    Toast.makeText(context,context.getString(R.string.msgSuccesDel),Toast.LENGTH_SHORT).show();
                    deleteTopicRealm(topic);
                }else{

                    if(result.getmError().getCode()==404){
                        Toast.makeText(context,context.getString(R.string.msgErrorDelTopic),Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context,context.getString(R.string.msgErrorNetwork),Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

    }


    public ManageTopic(Context context, Activity activityReference) {
        this.context = context;
        this.activityReference = activityReference;
        realm = RealmController.with(activityReference).getRealm();
        Retrofit retrofit = ClientRetrofit.getClient();
        topicInterface= retrofit.create(TopicsInterface.class);
        topicService = new TopicsService();

    }


    public void createTopic(TopicCreate topicCreate, final UserRealm userRealmIn){

        //create news
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteTopics), context);
        progressDialog.show();

        topicService.create(topicCreate, userRealmIn, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {
                    getALlTopic(userRealmIn);
                }
            }
        });

    }

    public void getALlTopic(UserRealm userRealm){

        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteTopics), context);
        progressDialog.show();
        topicService.getAll(userRealm,new IServiceResultListener<ArrayList<Topic>>() {
            @Override
            public void onResult(ServiceResult<ArrayList<Topic>> result) {
                progressDialog.dismiss();
                if (result.getmError() == null) {





                    createOrUpdateUserTopicRealm(result.getmData());

                    System.out.println("recupération reussi");
                    ListTopicsFragment.setRecycler(result.getmData(),activityReference);
                } else {
                Toast.makeText(context,context.getString(R.string.msgErrorNetworkTopics),Toast.LENGTH_SHORT).show();

                    ListTopicsFragment.setRecycler(getAllTopicsInRealm(),activityReference);
            }

            }
        });
    }



    private ArrayList<Topic> getAllTopicsInRealm(){


        RealmResults<TopicRealm> topicsRealm = RealmController.getInstance().getTopics();
        ArrayList<Topic> topics =new ArrayList();


        for(int i = 0; i< topicsRealm.size(); i++){
            topics.add(new Topic(topicsRealm.get(i).get_id(), topicsRealm.get(i).getContent(), topicsRealm.get(i).getTitle(),topicsRealm.get(i).getDate(),null));
        }

        return topics;
    }


    private void createOrUpdateUserTopicRealm(ArrayList<Topic> topics){
        for(int i = 0; i< topics.size(); i++){


            RealmList<PostRealm> postRealms = new RealmList<>();
            for (int j=0;j<topics.get(i).getPost().size();j++){


                postRealms.add(
                        new PostRealm(
                                topics.get(i).getPost().get(j).get_id(),
                                topics.get(i).getPost().get(j).getContent(),
                                topics.get(i).getPost().get(j).getDate(),
                                topics.get(i).getPost().get(j).getAuthor(),
                                topics.get(i).getPost().get(j).getTitle(),
                                topics.get(i).getPost().get(j).getTopic()));
                System.out.println(postRealms.get(j).getTitle());
            }


            TopicRealm topicRealm  = new TopicRealm(topics.get(i).get_id(),topics.get(i).getContent(),topics.get(i).getTitle(),topics.get(i).getDate(),postRealms);
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(topicRealm);
            realm.commitTransaction();
        }
    }


    private void deleteTopicRealm(final Topic topic){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TopicRealm> result = realm.where(TopicRealm.class).equalTo("_id",topic.get_id()).findAll();
                result.clear();
            }
        });
    }

}
