package com.entreprise.davfou.projetandroidesgi.ui.fragment.news;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.business.news.ManageNews;
import com.entreprise.davfou.projetandroidesgi.data.model.local.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.model.api.News;
import com.entreprise.davfou.projetandroidesgi.data.model.api.NewsCreate;
import com.entreprise.davfou.projetandroidesgi.ui.activity.MenuActivity;
import com.entreprise.davfou.projetandroidesgi.ui.adapters.news.NewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davidfournier on 26/06/2017.
 */

public class ListNewsFragment extends Fragment {

    static FragmentTransaction ft;

    static  RecyclerView recyclerViewNews;
    static  NewAdapter newAdapter;
    static FloatingActionButton btnAddNews;

    static UserRealm userRealm;
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
        btnAddNews = (FloatingActionButton) view.findViewById(R.id.btnAddNews);
        recyclerViewNews = (RecyclerView) view.findViewById(R.id.recyclerViewNews);

        manageNews=new ManageNews(getContext(),getActivity());

        userRealm=MenuActivity.getUser(getActivity());

        manageNews.getAllNews(userRealm);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNews.setLayoutManager(layoutManager);
        recyclerViewNews.setHasFixedSize(true);

        recyclerViewNews.setAdapter(newAdapter);


    }




    public static void setRecycler(ArrayList<News> newses, Activity activity){
        newAdapter = new NewAdapter(newses);
        newAdapter.setOnArticleClickedListener(new NewAdapter.OnArticleClickedListener() {
            @Override
            public void onArticleClicked(News newRealm, View articleView) {
                clickItemRecyclerView(newRealm);

            }
        });
        recyclerViewNews.setAdapter(newAdapter);
        btnAddNews.setVisibility(View.VISIBLE);

    }



    public static void clickItemRecyclerView(News news){
        ft.replace(R.id.frame_container, NewsDetailsFragment.newInstance(news,userRealm));
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