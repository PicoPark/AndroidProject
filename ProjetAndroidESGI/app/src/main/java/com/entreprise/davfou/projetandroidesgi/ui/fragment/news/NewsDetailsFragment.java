package com.entreprise.davfou.projetandroidesgi.ui.fragment.news;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.bussiness.comment.ManageComment;
import com.entreprise.davfou.projetandroidesgi.bussiness.news.ManageNews;
import com.entreprise.davfou.projetandroidesgi.data.method.realm.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserInfoRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Comment;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.CommentCreate;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.News;
import com.entreprise.davfou.projetandroidesgi.ui.adapters.comment.CommentAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davidfournier on 28/06/2017.
 */

public class NewsDetailsFragment extends Fragment {


    static UserRealm userRealm;
    ManageNews manageNews;
    static News news;
    static ManageComment manageComment;

    static RecyclerView recyclerViewComments;


    @BindView(R.id.detailNews_tv_title)
    EditText detailNews_tv_title;
    @BindView(R.id.detailNews_tv_author)
    TextView detailNews_tv_author;
    @BindView(R.id.detailNews_tv_content)
    EditText detailNews_tv_content;
    @BindView(R.id.btn_delete_news)
    Button btn_delete_news;
    @BindView(R.id.btn_update_news)
    Button btn_update_news;
    @BindView(R.id.btnAddComment)
    Button btnAddComment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_details, container, false);
    }

    public static NewsDetailsFragment newInstance(News _news,UserRealm _userRealm) {
        news = _news;
        userRealm=_userRealm;
        return new NewsDetailsFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        manageNews = new ManageNews(getContext(),getActivity());
        detailNews_tv_title.setText(news.getTitle());
        recyclerViewComments = (RecyclerView) view.findViewById(R.id.lv_comment);
        manageComment = new ManageComment(getContext(),getActivity(),news);



        //Faire la récupération de l'author d'abord en local puis via le réseau en les récupérant tous

        UserInfoRealm userInfoRealm = RealmController.getInstance().getUserById(news.getAuthor());

        System.out.println(news.get_id());

        detailNews_tv_author.setText(getContext().getString(R.string.writeBy)+userInfoRealm.getFirstName()+" "+userInfoRealm.getLastName());
        detailNews_tv_content.setText(news.getContent());


        UserRealm userRealmCo = RealmController.getInstance().getUserConnected(true);


        System.out.println(new String("" + userInfoRealm.get_id()) + " : " + news.getAuthor());

        if (new String("" + userInfoRealm.get_id()).equals(news.getAuthor()) && userInfoRealm.getEmail().equals(userRealmCo.getEmail()) && userInfoRealm.getLastName().equals(userRealmCo.getLastName()) && userInfoRealm.getFirstName().equals(userRealmCo.getFirstName()))
            System.out.println("c'est l'auteur");
        else{
            btn_delete_news.setVisibility(View.GONE);
            btn_update_news.setVisibility(View.GONE);
            detailNews_tv_content.setFocusable(false);
            detailNews_tv_content.setClickable(false);
            detailNews_tv_title.setFocusable(false);
            detailNews_tv_title.setClickable(false);
        }



        manageComment.getAllComment(userRealm);



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewComments.setLayoutManager(layoutManager);
        recyclerViewComments.setHasFixedSize(true);


    }


    public static void setRecycler(final ArrayList<Comment> comments, final Activity activity) {


        if (comments != null) {
            if (comments.size() > 0) {

                System.out.println("nbr topics : " + comments.size());
                final CommentAdapter commentAdapter = new CommentAdapter(comments);
                commentAdapter.setOnArticleClickedListener(new CommentAdapter.OnArticleClickedListener() {
                    @Override
                    public void onArticleClicked(Comment comment, View articleView) {
                        clickItemRecyclerView(comment,  activity);

                    }
                });

                recyclerViewComments.setAdapter(commentAdapter);
            }
        }

    }







    public static void clickItemRecyclerView(final Comment comment, Activity activity) {

        System.out.println("comment : " + comment.getTitle());


        final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertdialog_add_topics, null);
        final EditText editTitleNews = (EditText)dialogView.findViewById(R.id.editTitleTopics);
        final EditText editContentNews = (EditText)dialogView.findViewById(R.id.editContentTopics);
        final TextView txtTitleAdd = (TextView) dialogView.findViewById(R.id.txtTitleAdd);

        editTitleNews.setText(comment.getTitle());
        editContentNews.setText(comment.getContent());
        txtTitleAdd.setText(activity.getString(R.string.btnUpdate));

        dialog.setView(dialogView);


        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String date = df.format(Calendar.getInstance().getTime());

//    public PostCreate(String content, String title, String date, String topic) {
                comment.setTitle(editTitleNews.getText().toString());
                comment.setContent(editContentNews.getText().toString());
                manageComment.updateComment(comment,userRealm);




            }
        });
        dialog.setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                manageComment.deleteComment(comment,userRealm);


            }
        });
        dialog.setNeutralButton("Fermer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog b = dialog.create();
        b.show();




    }



    @OnClick(R.id.btnAddComment)
    public void clickbtnAddComment(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertdialog_add_topics, null);
        final EditText editTitleNews = (EditText)dialogView.findViewById(R.id.editTitleTopics);
        final EditText editContentNews = (EditText)dialogView.findViewById(R.id.editContentTopics);
        final TextView txtTitleAdd = (TextView) dialogView.findViewById(R.id.txtTitleAdd);


        txtTitleAdd.setText(getString(R.string.btnAjouterComment));

        dialog.setView(dialogView);


        dialog.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String date = df.format(Calendar.getInstance().getTime());

//       public CommentCreate(String title, String content, String news, String date) {


                manageComment.createComment(new CommentCreate(editTitleNews.getText().toString(),editContentNews.getText().toString(),news.get_id(),date),userRealm);



            }
        });
        dialog.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog b = dialog.create();
        b.show();
    }



    @OnClick(R.id.btn_delete_news)
    public  void clickbtn_delete_news(){
        manageNews.deleteNews(news,userRealm,null,0);

    }

    @OnClick(R.id.btn_update_news)
    public  void clickbtn_update_news(){
        news.setTitle(detailNews_tv_title.getText().toString());
        news.setContent(detailNews_tv_content.getText().toString());
        manageNews.updateNews(news,userRealm);

    }

}

