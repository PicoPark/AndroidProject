package com.entreprise.davfou.projetandroidesgi.data.services.newsservice;

/**
 * Created by davidfournier on 28/02/2017.
 */


import com.entreprise.davfou.projetandroidesgi.data.model.local.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.model.api.News;
import com.entreprise.davfou.projetandroidesgi.data.model.api.NewsCreate;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;

import java.util.ArrayList;


public interface INewsInterface {

    void create(NewsCreate newsCreate,UserRealm userRealm, final IServiceResultListener<String>
            resultListener);

    void getAll(UserRealm userRealm, final IServiceResultListener<ArrayList<News>>
            resultListener);

    void deleteNews(UserRealm userRealm,News news,final IServiceResultListener<String> resultListener);

    void updateNews(UserRealm userRealm,NewsCreate newsCreate,News news,final IServiceResultListener<String> resultListener);

}