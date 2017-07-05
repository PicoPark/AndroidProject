package com.entreprise.davfou.projetandroidesgi.ui.fragment.topics;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Post;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Topic;
import com.entreprise.davfou.projetandroidesgi.ui.recycler.posts.PostAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davidfournier on 02/07/2017.
 */

public class TopicDetailsFragment extends Fragment {


    static UserRealm userRealm;
    ManageTopic manageTopic;
    ManagePost managePost;

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
        managePost = new ManagePost(getContext(), getActivity());
        detailTopic_tv_title.setText(topic.getTitle());


        //Faire la récupération de l'author d'abord en local puis via le réseau en les récupérant tous

        // UserInfoRealm userRealm = RealmController.getInstance().getUserById(news.getAuthor());


        //detailTopic_tv_author.setText(getContext().getString(R.string.writeBy)+userRealm.getFirstName()+" "+userRealm.getLastName());
        detailTopic_tv_content.setText(topic.getContent());


        managePost.getAllPost(userRealm, topic);

        System.out.println("topics id : "+topic.get_id());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPosts.setLayoutManager(layoutManager);
        recyclerViewPosts.setHasFixedSize(true);

    }


    public static void setRecycler(final ArrayList<Post> posts, Activity activity) {


        if (posts != null) {
            if (posts.size() > 0) {

                System.out.println("nbr topics : " + posts.size());
                final PostAdapter topicAdapter = new PostAdapter(posts);
                topicAdapter.setOnArticleClickedListener(new PostAdapter.OnArticleClickedListener() {
                    @Override
                    public void onArticleClicked(Post post, View articleView) {
                        clickItemRecyclerView(post);

                    }
                });

                recyclerViewPosts.setAdapter(topicAdapter);
            }
        }

    }


    public static void clickItemRecyclerView(Post post) {

        System.out.println("topics : " + post.getTitle());

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