package com.entreprise.davfou.projetandroidesgi.data.topicservice;

/**
 * Created by davidfournier on 28/02/2017.
 */


import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.Topic;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.TopicCreate;
import com.entreprise.davfou.projetandroidesgi.data.utils.IServiceResultListener;

import java.util.ArrayList;


public interface ITopicsInterface {

    void create(TopicCreate topicCreate, UserRealm userRealm, final IServiceResultListener<String>
            resultListener);

    void getAll(UserRealm userRealm, final IServiceResultListener<ArrayList<Topic>>
            resultListener);

    void deleteTopic(UserRealm userRealm, Topic topic, final IServiceResultListener<String> resultListener);

    void updateTopic(UserRealm userRealm, TopicCreate topicCreate,Topic topic, final IServiceResultListener<String> resultListener);

}