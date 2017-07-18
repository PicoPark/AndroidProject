package com.entreprise.davfou.projetandroidesgi.ui.fragment.topics;

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
import com.entreprise.davfou.projetandroidesgi.bussiness.post.ManagePost;
import com.entreprise.davfou.projetandroidesgi.bussiness.topic.ManageTopic;
import com.entreprise.davfou.projetandroidesgi.data.method.realm.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserInfoRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Post;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.PostCreate;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Topic;
import com.entreprise.davfou.projetandroidesgi.ui.adapters.posts.PostAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davidfournier on 02/07/2017.
 */

public class TopicDetailsFragment extends Fragment {


    static UserRealm userRealm;
    ManageTopic manageTopic;
    static ManagePost managePost;

    static Topic topic;

    static RecyclerView recyclerViewPosts;

    @BindView(R.id.detailTopic_tv_title)
    EditText detailTopic_tv_title;
    @BindView(R.id.detailTopic_tv_author)
    TextView detailTopic_tv_author;
    @BindView(R.id.detailTopic_tv_content)
    EditText detailTopic_tv_content;
    @BindView(R.id.btn_delete_Topic)
    Button btn_delete_Topic;
    @BindView(R.id.btn_update_Topic)
    Button btn_update_Topic;
    @BindView(R.id.btnAddPost)
    Button btnAddPost;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_details, container, false);
    }

    public static TopicDetailsFragment newInstance(Topic _topic, UserRealm _userRealm) {
        topic = _topic;
        userRealm = _userRealm;
        return new TopicDetailsFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        recyclerViewPosts = (RecyclerView) view.findViewById(R.id.lv_post);
        manageTopic = new ManageTopic(getContext(), getActivity());
        managePost = new ManagePost(getContext(), getActivity(), topic);
        detailTopic_tv_title.setText(topic.getTitle());


        UserInfoRealm userInfoRealm = RealmController.getInstance().getUserById(topic.getAuthor());


        detailTopic_tv_author.setText(getContext().getString(R.string.writeBy) + userInfoRealm.getFirstName() + " " + userInfoRealm.getLastName());

        detailTopic_tv_content.setText(topic.getContent());


        UserRealm userRealmCo = RealmController.getInstance().getUserConnected(true);



        if (new String("" + userInfoRealm.get_id()).equals(topic.getAuthor()) && userInfoRealm.getEmail().equals(userRealmCo.getEmail()) && userInfoRealm.getLastName().equals(userRealmCo.getLastName()) && userInfoRealm.getFirstName().equals(userRealmCo.getFirstName()))
            System.out.println("c'est l'auteur");
        else{
            btn_delete_Topic.setVisibility(View.GONE);
            btn_update_Topic.setVisibility(View.GONE);
            detailTopic_tv_content.setFocusable(false);
            detailTopic_tv_content.setClickable(false);
            detailTopic_tv_title.setFocusable(false);
            detailTopic_tv_title.setClickable(false);
        }
            managePost.getAllPost(userRealm);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPosts.setLayoutManager(layoutManager);
        recyclerViewPosts.setHasFixedSize(true);

    }


    public static void setRecycler(final ArrayList<Post> posts, final Activity activity) {


        if (posts != null) {
            if (posts.size() > 0) {

                final PostAdapter topicAdapter = new PostAdapter(posts);
                topicAdapter.setOnArticleClickedListener(new PostAdapter.OnArticleClickedListener() {
                    @Override
                    public void onArticleClicked(Post post, View articleView) {
                        clickItemRecyclerView(post, activity);

                    }
                });

                recyclerViewPosts.setAdapter(topicAdapter);
            }
        }

    }


    public static void clickItemRecyclerView(final Post post, Activity activity) {



        final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertdialog_add_topics, null);
        final EditText editTitleNews = (EditText) dialogView.findViewById(R.id.editTitleTopics);
        final EditText editContentNews = (EditText) dialogView.findViewById(R.id.editContentTopics);
        final TextView txtTitleAdd = (TextView) dialogView.findViewById(R.id.txtTitleAdd);

        editTitleNews.setText(post.getTitle());
        editContentNews.setText(post.getContent());
        txtTitleAdd.setText(activity.getString(R.string.btnUpdate));

        dialog.setView(dialogView);


        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String date = df.format(Calendar.getInstance().getTime());

//    public PostCreate(String content, String title, String date, String topic) {
                post.setTitle(editTitleNews.getText().toString());
                post.setContent(editContentNews.getText().toString());
                managePost.updatePost(post, userRealm);


            }
        });
        dialog.setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                managePost.deletePost(post, userRealm);


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


    @OnClick(R.id.btnAddPost)
    public void clickbtnAddPost() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertdialog_add_topics, null);
        final EditText editTitleNews = (EditText) dialogView.findViewById(R.id.editTitleTopics);
        final EditText editContentNews = (EditText) dialogView.findViewById(R.id.editContentTopics);
        final TextView txtTitleAdd = (TextView) dialogView.findViewById(R.id.txtTitleAdd);


        txtTitleAdd.setText(getString(R.string.btnAjouterPost));

        dialog.setView(dialogView);


        dialog.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String date = df.format(Calendar.getInstance().getTime());

//    public PostCreate(String content, String title, String date, String topic) {

                managePost.createPost(new PostCreate(editContentNews.getText().toString(), editTitleNews.getText().toString(), date, topic.get_id()), userRealm);


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


    @OnClick(R.id.btn_delete_Topic)
    public void clickbtn_delete_news() {
        manageTopic.deleteTopic(topic, userRealm);

    }

    @OnClick(R.id.btn_update_Topic)
    public void clickbtn_update_news() {

        topic.setTitle(detailTopic_tv_title.getText().toString());

        topic.setContent(detailTopic_tv_content.getText().toString());
        manageTopic.updateTopic(topic, userRealm);

    }


}