package com.entreprise.davfou.projetandroidesgi.bussiness.post;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.PostService.PostService;
import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.realm.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.method.retrofit.PostInterface;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.NewsRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.PostRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Post;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.PostCreate;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Topic;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceResult;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.topics.TopicDetailsFragment;
import com.entreprise.davfou.projetandroidesgi.ui.utils.ProgressDialog;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Retrofit;

/**
 * Created by Pico on 04/07/2017.
 */

public class ManagePost {


    Context context;
    Activity activityReference;
    android.app.ProgressDialog progressDialog;
    Realm realm;
    PostInterface postInterface;
    PostService postService;



    public ManagePost(Context context, Activity activityReference) {
        this.context = context;
        this.activityReference = activityReference;
        realm = RealmController.with(activityReference).getRealm();
        Retrofit retrofit = ClientRetrofit.getClient();
        postInterface = retrofit.create(PostInterface.class);
        postService = new PostService();

    }

    public void updatePost(Post post, UserRealm userRealm){
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
        progressDialog.show();
        postService.updatePost(userRealm, new PostCreate(post.getContent(),post.getTitle(),post.getDate(), post.getAuthor(),post.getTopic()), post, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();
                if(result.getmError()==null) {
                    System.out.println("reussi");
                    Toast.makeText(context,context.getString(R.string.msgSuccesUpdNews),Toast.LENGTH_SHORT).show();
                }else{
                    if(result.getmError().getCode()==404){
                        Toast.makeText(context,context.getString(R.string.msgErrorDelPosts),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,context.getString(R.string.msgErrorNetwork),Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public void deletePost(Post post,UserRealm userRealm){
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
        progressDialog.show();
        postService.deletePost(userRealm, post, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();
                if(result.getmError()==null) {
                    System.out.println("reussi");
                    Toast.makeText(context,context.getString(R.string.msgSuccesDel),Toast.LENGTH_SHORT).show();
                }else{
                    if(result.getmError().getCode()==404){
                        Toast.makeText(context,context.getString(R.string.msgErrorDelPosts),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,context.getString(R.string.msgErrorNetwork),Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    public void createPost(PostCreate postCreate, final Topic topic, final UserRealm userRealmIn){

        //create news
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
        progressDialog.show();

        postService.create(postCreate, userRealmIn, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();
                if(result.getmError()==null) {
                    getAllPost(userRealmIn,topic);
                }
            }
        });

    }

    public void getAllPost(UserRealm userRealm, final Topic topic){

        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttentePosts), context);
        progressDialog.show();


        postService.getAll(userRealm, new IServiceResultListener<ArrayList<Post>>() {
            @Override
            public void onResult(ServiceResult<ArrayList<Post>> result) {
                progressDialog.dismiss();
                if(result.getmError()==null) {



                    createOrUpdatePostRealm(result.getmData());
                    ArrayList<Post> posts = new ArrayList<Post>();
                    for(int i =0;i<result.getmData().size();i++){

                        if(result.getmData().get(i).getTopic().equals(topic.get_id())){
                            posts.add(result.getmData().get(i));
                        }
                    }


                    TopicDetailsFragment.setRecycler(posts,activityReference);
                  // maj list view
                } else {
                    Toast.makeText(context,context.getString(R.string.msgErrorNetworkNews),Toast.LENGTH_SHORT).show();

                //   maj list view
                }
            }
        });

    }



    private ArrayList<Post> getAllPostInRealm(){


        RealmResults<PostRealm> postRealms = RealmController.getInstance().getPosts();
        ArrayList<Post> post =new ArrayList();

        for(int i = 0; i< postRealms.size(); i++){
            post.add(
                    new Post(
                            postRealms.get(i).get_id(),
                            postRealms.get(i).getContent(),
                            postRealms.get(i).getDate(),
                            postRealms.get(i).getAuthor(),
                            postRealms.get(i).getTitle(),
                            postRealms.get(i).getTopic()
                    )
            );
        }
        return post;
    }


    private void createOrUpdatePostRealm(ArrayList<Post> postArrayList){
        for(int i = 0; i< postArrayList.size(); i++){

            System.out.println("posts id : "+postArrayList.get(i).get_id());



            NewsRealm newsRealm = new NewsRealm(i,
                    postArrayList.get(i).get_id(),
                    postArrayList.get(i).getContent(),
                    postArrayList.get(i).getDate(),
                    postArrayList.get(i).getTitle(),
                    postArrayList.get(i).getTopic()
            );
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(newsRealm);
            realm.commitTransaction();
        }
    }
}
