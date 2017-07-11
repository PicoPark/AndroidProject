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
import com.entreprise.davfou.projetandroidesgi.bussiness.news.ManageNews;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.News;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.NewsCreate;
import com.entreprise.davfou.projetandroidesgi.ui.activity.MenuActivity;
import com.entreprise.davfou.projetandroidesgi.ui.adapters.news.NewAdapter;

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

public class ListNewsFragment extends Fragment {

    static FragmentTransaction ft;

    static  RecyclerView recyclerViewNews;
    static  NewAdapter newAdapter;
    @BindView(R.id.btnAddNews)
    FloatingActionButton btnAddNews;

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

        recyclerViewNews = (RecyclerView) view.findViewById(R.id.recyclerViewNews);

        manageNews=new ManageNews(getContext(),getActivity());

        userRealm=MenuActivity.getUser(getActivity());

        manageNews.getAllNews(userRealm);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNews.setLayoutManager(layoutManager);
        recyclerViewNews.setHasFixedSize(true);


        /*ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {


                final int position = viewHolder.getAdapterPosition();

                if (position==0)
                    return ;

                //Remove swiped item from list and notify the RecyclerView
                manageNews.deleteNews(newAdapter.getNews(position),userRealm,newAdapter,position);

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewNews);*/

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