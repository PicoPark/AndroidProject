package com.entreprise.davfou.projetandroidesgi.ui.fragment.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.bussiness.news.ManageNews;
import com.entreprise.davfou.projetandroidesgi.data.method.realm.RealmController;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserInfoRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.News;

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


        //Faire la récupération de l'author d'abord en local puis via le réseau en les récupérant tous

        UserInfoRealm userRealm = RealmController.getInstance().getUserById(news.getAuthor());

        System.out.println(news.get_id());

        detailNews_tv_author.setText(getContext().getString(R.string.writeBy)+userRealm.getFirstName()+" "+userRealm.getLastName());
        detailNews_tv_content.setText(news.getContent());

    }


    @OnClick(R.id.btn_delete_news)
    public  void clickbtn_delete_news(){
        manageNews.deleteNews(news,userRealm);

    }

    @OnClick(R.id.btn_update_news)
    public  void clickbtn_update_news(){
        manageNews.updateNews(news,userRealm);

    }

}

