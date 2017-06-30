package com.entreprise.davfou.projetandroidesgi.ui.fragment.news;

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
import com.entreprise.davfou.projetandroidesgi.bussiness.news.ManageNews;
import com.entreprise.davfou.projetandroidesgi.data.method.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.NewsRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.News;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.NewsCreate;
import com.entreprise.davfou.projetandroidesgi.ui.activity.MenuActivity;
import com.entreprise.davfou.projetandroidesgi.ui.recycler.NewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by davidfournier on 26/06/2017.
 */

public class ListNewsFragment extends Fragment {

    static FragmentTransaction ft;

    static  RecyclerView recyclerViewNews;

    @BindView(R.id.btnAddNews)
    Button btnAddNews;

    UserRealm userRealm;
    ManageNews manageNews;

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
        ft = getFragmentManager().beginTransaction();

        recyclerViewNews = (RecyclerView) view.findViewById(R.id.recyclerViewNews);

        manageNews=new ManageNews(getContext(),getActivity());

        userRealm=MenuActivity.getUser(getActivity());

        manageNews.getAllNews(userRealm);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNews.setLayoutManager(layoutManager);
        recyclerViewNews.setHasFixedSize(true);



    }




    public static void setRecycler(ArrayList<News> newses, Activity activity){
        System.out.println("in fragment : "+ newses.size());
        final NewAdapter newAdapter = new NewAdapter(newses);
        newAdapter.setOnArticleClickedListener(new NewAdapter.OnArticleClickedListener() {
            @Override
            public void onArticleClicked(News newRealm, View articleView) {
                clickItemRecyclerView(newRealm);

            }
        });

        recyclerViewNews.setAdapter(newAdapter);

        Realm realm = RealmController.with(activity).getRealm();

        System.out.println("nbr de newses dans realm : "+ realm.where(NewsRealm.class).count());

    }


    public static void setRecyclerOffline(ArrayList<News> newses, Activity activity){
        System.out.println("in fragment : "+ newses.size());
        final NewAdapter newAdapter = new NewAdapter(newses);

        newAdapter.setOnArticleClickedListener(new NewAdapter.OnArticleClickedListener() {
            @Override
            public void onArticleClicked(News newRealm, View articleView) {
                clickItemRecyclerView(newRealm);
            }
        });

        recyclerViewNews.setAdapter(newAdapter);

        Realm realm = RealmController.with(activity).getRealm();

        System.out.println("nbr de newses dans realm : "+ realm.where(NewsRealm.class).count());

    }
    public static void clickItemRecyclerView(News news){
        System.out.println(news.getTitle());
        ft.replace(R.id.frame_container, NewsDetailsFragment.newInstance(news));
        ft.commit();
    }


    @OnClick(R.id.btnAddNews)
    public void clickbtnAddNews(){
        //create news

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertdialog_add_news, null);
        final EditText editTitleNews = (EditText)dialogView.findViewById(R.id.editTitleNews);
        final EditText editContentNews = (EditText)dialogView.findViewById(R.id.editContentNews);

        dialog.setView(dialogView);


        dialog.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String date = df.format(Calendar.getInstance().getTime());
                manageNews.createNew(new NewsCreate(editContentNews.getText().toString(),editTitleNews.getText().toString(),date),userRealm);



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