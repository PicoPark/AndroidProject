package com.entreprise.davfou.projetandroidesgi.data.newsservice;

/**
 * Created by davidfournier on 28/02/2017.
 */


import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.News;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.NewsCreate;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;

import java.util.ArrayList;


public interface INewsInterface {

    void create(NewsCreate newsCreate,UserRealm userRealm, final IServiceResultListener<String>
            resultListener);

    void getAll(UserRealm userRealm, final IServiceResultListener<ArrayList<News>>
            resultListener);

    void deleteNews(UserRealm userRealm,News news,final IServiceResultListener<String> resultListener);

    void updateNews(UserRealm userRealm,News news,final IServiceResultListener<String> resultListener);

}