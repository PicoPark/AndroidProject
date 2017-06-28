package com.entreprise.davfou.projetandroidesgi.ui.fragment.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.bussiness.news.ManageNews;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.News;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidfournier on 28/06/2017.
 */

public class NewsDetailsFragment extends Fragment {


    UserRealm userRealm;
    ManageNews manageNews;
    static News news;
    @BindView(R.id.textViewTitleNewsDetail)
    TextView textViewTitleNewsDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_details, container, false);
    }

    public static NewsDetailsFragment newInstance(News _news) {
        news = _news;
        return new NewsDetailsFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        textViewTitleNewsDetail.setText(news.getTitle());
    }
}

