package com.entreprise.davfou.projetandroidesgi.bussiness.comment;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.CommentService.CommentService;
import com.entreprise.davfou.projetandroidesgi.data.clientWS.ClientRetrofit;
import com.entreprise.davfou.projetandroidesgi.data.method.realm.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.method.retrofit.CommentInterface;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.CommentRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Comment;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.CommentCreate;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;
import com.entreprise.davfou.projetandroidesgi.data.utils.ServiceResult;
import com.entreprise.davfou.projetandroidesgi.ui.utils.ProgressDialog;

import io.realm.Realm;
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



    public ManageComment(Context context, Activity activityReference) {
        this.context = context;
        this.activityReference = activityReference;
        realm = RealmController.with(activityReference).getRealm();
        Retrofit retrofit = ClientRetrofit.getClient();
        commentInterface= retrofit.create(CommentInterface.class);
        commentService = new CommentService();

    }



    public void updateComment(Comment comment, UserRealm userRealm){
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
        progressDialog.show();
        commentService.updateComment(userRealm,new CommentCreate(comment.getContent(),comment.getTitle(),comment.getDate()), comment, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {
                    System.out.println("reussi");


                    Toast.makeText(context,context.getString(R.string.msgSuccesUpdNews),Toast.LENGTH_SHORT).show();

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



    public void deleteComment(Comment comment,UserRealm userRealm){
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
        progressDialog.show();
        commentService.deleteComment(userRealm, comment, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {
                    System.out.println("reussi");


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


    public void createNew(CommentCreate commentCreate, final UserRealm userRealmIn){

        //create comment
        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
        progressDialog.show();

        commentService.create(commentCreate, userRealmIn, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {
                    getAllComment(userRealmIn);
                }
            }
        });

    }



    public void getAllComment(UserRealm userRealm){

        progressDialog = ProgressDialog.getProgress(context.getString(R.string.titreAttente), context.getString(R.string.textAttenteNews), context);
        progressDialog.show();


        commentService.getAllComment(userRealm, new IServiceResultListener<ArrayList<Comment>>() {
            @Override
            public void onResult(ServiceResult<ArrayList<Comment>> result) {
                progressDialog.dismiss();

                if(result.getmError()==null) {

                    createOrUpdateNewRealm(result.getmData());

                  //  ListCommentFragment.setRecycler(result.getmData(),activityReference);

                } else {
                    Toast.makeText(context,context.getString(R.string.msgErrorNetworkNews),Toast.LENGTH_SHORT).show();

                  //  ListCommentFragment.setRecycler(getAllNewInRealm(),activityReference);
                }
            }
        });

    }



    private ArrayList<Comment> getAllNewInRealm(){


        RealmResults<CommentRealm> commentRealms = RealmController.getInstance().getComment();
        ArrayList<Comment> comment =new ArrayList();


        for(int i = 0; i< commentRealms.size(); i++){
            comment.add(new Comment(commentRealms.get(i).get_id(), commentRealms.get(i).getAuthor(), commentRealms.get(i).getContent(), commentRealms.get(i).getTitle(),commentRealms.get(i).getDate()));
        }

        return comment;
    }


    private void createOrUpdateNewRealm(ArrayList<Comment> commentArrayList){

        for(int i = 0; i< commentArrayList.size(); i++){

            CommentRealm commentRealm = new CommentRealm(i, commentArrayList.get(i).get_id(), commentArrayList.get(i).getAuthor(), commentArrayList.get(i).getContent(), commentArrayList.get(i).getTitle(),commentArrayList.get(i).getDate());
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(commentRealm);
            realm.commitTransaction();
        }

    }
}
