package com.entreprise.davfou.projetandroidesgi.ui.fragment.topics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.bussiness.topic.ManageTopic;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Topic;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davidfournier on 02/07/2017.
 */

public class TopicDetailsFragment extends Fragment {


    static UserRealm userRealm;
    ManageTopic manageTopic;
    static Topic topic;
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
        userRealm=_userRealm;
        return new TopicDetailsFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        manageTopic = new ManageTopic(getContext(),getActivity());
        detailTopic_tv_title.setText(topic.getTitle());


        //Faire la récupération de l'author d'abord en local puis via le réseau en les récupérant tous

       // UserInfoRealm userRealm = RealmController.getInstance().getUserById(news.getAuthor());


        //detailTopic_tv_author.setText(getContext().getString(R.string.writeBy)+userRealm.getFirstName()+" "+userRealm.getLastName());
        detailTopic_tv_content.setText(topic.getContent());

    }


    @OnClick(R.id.btn_delete_Topic)
    public  void clickbtn_delete_news(){
        manageTopic.deleteTopic(topic,userRealm);

    }

    @OnClick(R.id.btn_update_Topic)
    public  void clickbtn_update_news(){

        topic.setTitle(detailTopic_tv_title.getText().toString());

        topic.setContent(detailTopic_tv_content.getText().toString());
        manageTopic.updateTopic(topic,userRealm);

    }

}