package com.entreprise.davfou.projetandroidesgi.bussiness.comment;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.commentService.CommentService;
import com.entreprise.davfou.projetandroidesgi.data.method.realm.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.method.retrofit.CommentInterface;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.CommentRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Comment;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.CommentCreate;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.News;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceResult;
import com.entreprise.davfou.projetandroidesgi.ui.fragment.news.NewsDetailsFragment;
import com.entreprise.davfou.projetandroidesgi.ui.utils.ProgressDialog;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Retrofit;

/**
 * Created by Pico on 06/07/2017.
 */

public class ManageComment {

    Context context;
    Activity activityReference;
    android.app.ProgressDialog progressDialog;
    Realm realm;
    CommentInterface commentInterface;
    CommentService commentService;
    News news;



    public ManageComment(Context context, Activity activityReference,News news) {
        this.context = context;
        this.activityReference = activityReference;
        realm = RealmController.with(activityReference).getRealm();
        Retrofit retrofit = ClientRetrofit.getClient();
        commentInterface= retrofit.create(CommentInterface.class);
        commentService = new CommentService();
        this.news = news;

    }



    public void updateComment(Comment comment, final UserRealm userRealm){
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textUpdateComments), context);
        progressDialog.show();
        commentService.updateComment(userRealm,new CommentCreate(comment.getTitle(),comment.getContent(),comment.getNews(),comment.getDate()), comment, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {


                    Toast.makeText(context,context.getString(R.string.msgSuccesUpdNews),Toast.LENGTH_SHORT).show();
                    getAllComment(userRealm);

                }else{

                    if(result.getmError().getCode()==404){
                        Toast.makeText(context,context.getString(R.string.msgErrorDelNews),Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context,context.getString(R.string.msgErrorNetwork),Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }



    public void deleteComment(final Comment comment, final UserRealm userRealm){
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textSupressionComment), context);
        progressDialog.show();
        commentService.deleteComment(userRealm, comment, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {

                    deleteCommentRealm(comment);

                    getAllComment(userRealm);

                    Toast.makeText(context,context.getString(R.string.msgSuccesDel),Toast.LENGTH_SHORT).show();

                }else{

                    if(result.getmError().getCode()==404){
                        Toast.makeText(context,context.getString(R.string.msgErrorDelNews),Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(context,context.getString(R.string.msgErrorNetwork),Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

    }


    public void createComment(CommentCreate commentCreate, final UserRealm userRealmIn){

        //create comment
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textCreationComments), context);
        progressDialog.show();

        commentService.create(commentCreate, userRealmIn, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {
                    getAllComment(userRealmIn);
                }else{
                    showErrorNetwork();
                }
            }
        });

    }



    public void getAllComment(UserRealm userRealm){

        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteComments), context);
        progressDialog.show();


        commentService.getAllComment(userRealm, new IServiceResultListener<ArrayList<Comment>>() {
            @Override
            public void onResult(ServiceResult<ArrayList<Comment>> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {

                    createOrUpdateCommentRealm(result.getmData());

                    ArrayList<Comment> comments = new ArrayList<>();
                    for(int i =0;i<result.getmData().size();i++){

                        if(result.getmData().get(i).getNews().equals(news.get_id())){
                            comments.add(result.getmData().get(i));
                        }
                    }


                    NewsDetailsFragment.setRecycler(comments,activityReference);
                    // maj list view
                } else {


                    Toast.makeText(context,context.getString(R.string.msgErrorNetworkNews),Toast.LENGTH_SHORT).show();

                    NewsDetailsFragment.setRecycler(getAllCommentInRealm(),activityReference);
                }
            }
        });

    }



    private ArrayList<Comment> getAllCommentInRealm(){


        RealmResults<CommentRealm> commentRealms = RealmController.getInstance().getComments();
        ArrayList<Comment> comment =new ArrayList();

//String _id, String author, String title, String content, String news, String date
        for(int i = 0; i< commentRealms.size(); i++){

            if(commentRealms.get(i).getNews().equals(news.get_id())) {
                comment.add(new Comment(commentRealms.get(i).get_id(), commentRealms.get(i).getAuthor(), commentRealms.get(i).getTitle(), commentRealms.get(i).getContent(), commentRealms.get(i).getNews(), commentRealms.get(i).getDate()));
            }
        }

        return comment;
    }


    private void deleteCommentRealm(final Comment comment){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<CommentRealm> result = realm.where(CommentRealm.class).equalTo("_id",comment.get_id()).findAll();
                result.clear();
            }
        });
    }


    private void createOrUpdateCommentRealm(ArrayList<Comment> commentArrayList){

        for(int i = 0; i< commentArrayList.size(); i++){

            CommentRealm commentRealm = new CommentRealm(i, commentArrayList.get(i).get_id(), commentArrayList.get(i).getAuthor(), commentArrayList.get(i).getContent(), commentArrayList.get(i).getTitle(),commentArrayList.get(i).getDate(), commentArrayList.get(i).getNews());
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(commentRealm);
            realm.commitTransaction();
        }

    }

    private void showErrorNetwork(){
        Toast.makeText(context,context.getString(R.string.msgErrorNetwork),Toast.LENGTH_SHORT).show();

    }
}
