package com.entreprise.davfou.projetandroidesgi.ui.fragment.news;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.bussiness.news.ManageNews;
import com.entreprise.davfou.projetandroidesgi.data.method.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.NewRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.New;
import com.entreprise.davfou.projetandroidesgi.ui.activity.MenuActivity;
import com.entreprise.davfou.projetandroidesgi.ui.recycler.NewAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by davidfournier on 26/06/2017.
 */

public class ListNewsFragment extends Fragment {



    static  RecyclerView recyclerViewNews;

    UserRealm userRealm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_news, container, false);
    }

    public static ListNewsFragment newInstance() {
        return new ListNewsFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        recyclerViewNews = (RecyclerView) view.findViewById(R.id.recyclerViewNews);

        ManageNews manageNews=new ManageNews(getContext(),getActivity());

        userRealm=MenuActivity.getUser(getActivity());

        manageNews.getAllNews(userRealm);


        /*
         final AdapterExo adapterExo = new AdapterExo(exos);

        adapterExo.setOnArticleClickedListener(new AdapterExo.OnArticleClickedListener() {
            @Override
            public void onArticleClicked(String exo, View articleView) {
                System.out.println("nom "+exo);
                nom_exo.edit().putString("nom",exo).apply();
                ft.replace(R.id.frame_container, TrainingFragment.newInstance());
                ft.commit();

            }
        });
         */
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNews.setLayoutManager(layoutManager);
        recyclerViewNews.setHasFixedSize(true);



    }


    public static void setRecycler(ArrayList<New> news, Activity activity){
        System.out.println("in fragment : "+news.size());
        final NewAdapter newAdapter = new NewAdapter(news);


        recyclerViewNews.setAdapter(newAdapter);

        Realm realm = RealmController.with(activity).getRealm();

        System.out.println("nbr de news dans realm : "+ realm.where(NewRealm.class).count());

    }


    public static void setRecyclerOffline(ArrayList<New> news, Activity activity){
        System.out.println("in fragment : "+news.size());
        final NewAdapter newAdapter = new NewAdapter(news);


        recyclerViewNews.setAdapter(newAdapter);

        Realm realm = RealmController.with(activity).getRealm();

        System.out.println("nbr de news dans realm : "+ realm.where(NewRealm.class).count());

    }


}