package com.entreprise.davfou.projetandroidesgi.ui.fragment.topics;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.bussiness.topic.ManageTopic;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Topic;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.TopicCreate;
import com.entreprise.davfou.projetandroidesgi.ui.activity.MenuActivity;
import com.entreprise.davfou.projetandroidesgi.ui.recycler.topics.TopicAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davidfournier on 26/06/2017.
 */

public class ListTopicsFragment extends Fragment {

    static FragmentTransaction ft;

    @BindView(R.id.btnAddTopics)
    Button btnAddTopics;

    static RecyclerView recyclerViewTopics;


    static UserRealm userRealm;
    ManageTopic manageTopic;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_topics, container, false);
    }

    public static ListTopicsFragment newInstance() {
        return new ListTopicsFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this,view);
        ft = getFragmentManager().beginTransaction();

        recyclerViewTopics = (RecyclerView) view.findViewById(R.id.recyclerViewTopics);
        manageTopic = new ManageTopic(getContext(),getActivity());

        userRealm= MenuActivity.getUser(getActivity());

        manageTopic.getALlTopic(userRealm);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewTopics.setLayoutManager(layoutManager);
        recyclerViewTopics.setHasFixedSize(true);

    }

    public static void clickItemRecyclerView(Topic topic){
       /* ft.replace(R.id.frame_container, NewsDetailsFragment.newInstance(news,userRealm));
        ft.commit();*/

        System.out.println("topics : "+topic.getTitle());
        if(topic.getPost().size()>0) {
            System.out.println("nbr post : " + topic.getPost().size());
            System.out.println("post n°1 : " + topic.getPost().get(0).getTitle());
        }


        ft.replace(R.id.frame_container, TopicDetailsFragment.newInstance(topic,userRealm));
        ft.commit();
    }


    public static void setRecycler(ArrayList<Topic> topics, Activity activity){

        System.out.println("nbr topics : "+topics.size());
        final TopicAdapter topicAdapter = new TopicAdapter(topics);
        topicAdapter.setOnArticleClickedListener(new TopicAdapter.OnArticleClickedListener() {
            @Override
            public void onArticleClicked(Topic topic, View articleView) {
                clickItemRecyclerView(topic);

            }
        });

        recyclerViewTopics.setAdapter(topicAdapter);



    }



    @OnClick(R.id.btnAddTopics)
    public void clickbtnAddTopics(){

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertdialog_add_topics, null);
        final EditText editTitleNews = (EditText)dialogView.findViewById(R.id.editTitleTopics);
        final EditText editContentNews = (EditText)dialogView.findViewById(R.id.editContentTopics);

        dialog.setView(dialogView);


        dialog.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String date = df.format(Calendar.getInstance().getTime());
                //createNew(new NewsCreate(editContentNews.getText().toString(),editTitleNews.getText().toString(),date),userRealm);
                manageTopic.createTopic(new TopicCreate(editContentNews.getText().toString(),editTitleNews.getText().toString(),date),userRealm);



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

}