package com.entreprise.davfou.projetandroidesgi.ui.fragment.topics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.bussiness.ManageTopic;
import com.entreprise.davfou.projetandroidesgi.bussiness.news.ManageNews;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.ui.activity.MenuActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidfournier on 26/06/2017.
 */

public class ListTopicsFragment extends Fragment {

    static FragmentTransaction ft;

    static RecyclerView recyclerViewNews;

    @BindView(R.id.btnAddNews)

    UserRealm userRealm; //?
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

        recyclerViewNews = (RecyclerView) view.findViewById(R.id.recyclerViewNews);

 //       manageTopic=new ManageTopic(getContext(),getActivity());

        userRealm= MenuActivity.getUser(getActivity());

//        manageTopic.getAllTopic(userRealm);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNews.setLayoutManager(layoutManager);
        recyclerViewNews.setHasFixedSize(true);


    }


}