package com.entreprise.davfou.projetandroidesgi.ui.adapters.topics;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.method.realm.UserController;
import com.entreprise.davfou.projetandroidesgi.data.model.local.UserInfoRealm;
import com.entreprise.davfou.projetandroidesgi.data.model.local.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.model.api.Topic;

import java.util.ArrayList;

import static android.graphics.Color.BLACK;

/**
 * Created by davidfournier on 28/06/2017.
 */

public class TopicAdapter extends RecyclerView.Adapter<MyViewHolderTopics> {

    ArrayList<Topic> topics;

    private TopicAdapter.OnArticleClickedListener mOnArticleClickedListener;

    //ajouter un constructeur prenant en entrée une liste
    public TopicAdapter(ArrayList<Topic> topics) {


        this.topics = topics;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public MyViewHolderTopics onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_new,viewGroup,false);
        return new MyViewHolderTopics(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(MyViewHolderTopics myViewHolder, final int position) {
        final Topic myObject = topics.get(position);
        myViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (mOnArticleClickedListener != null) {
                    mOnArticleClickedListener.onArticleClicked(myObject, view);
                }
            }
        });
        myViewHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }
    /**
     * Sets on article clicked listener.
     *
     * @param onArticleClickedListener Beneficiaire clicked listener.
     */
    public void setOnArticleClickedListener(final TopicAdapter.OnArticleClickedListener onArticleClickedListener) {
        this.mOnArticleClickedListener = onArticleClickedListener;
    }

    public interface OnArticleClickedListener {

        void onArticleClicked(Topic topic, View articleView);



    }
}

class MyViewHolderTopics extends RecyclerView.ViewHolder{
    public final View rootView;

    private TextView textViewTitleNews;
    private TextView textViewContentNews;
    private TextView textViewAuhtor;

    //itemView est la vue correspondante à 1 cellule
    public MyViewHolderTopics(View itemView) {
        super(itemView);
        this.rootView = itemView;
        //c'est ici que l'on fait nos findView
        textViewTitleNews = (TextView) itemView.findViewById(R.id.textViewTitleNews);
        textViewContentNews = (TextView) itemView.findViewById(R.id.textViewContentNews);
        textViewAuhtor = (TextView) itemView.findViewById(R.id.textViewAuhtor);

    }

    public void bind(Topic topic){
        textViewTitleNews.setText(topic.getTitle());
        textViewContentNews.setText(topic.getContent());

        UserRealm userRealm= UserController.getInstance().getUserConnected(true);
        UserInfoRealm userInfoRealm= UserController.getInstance().getUserById(topic.getAuthor());



        if(new String(""+userInfoRealm.get_id()).equals(topic.getAuthor())&&userInfoRealm.getEmail().equals(userRealm.getEmail())&&userInfoRealm.getLastName().equals(userRealm.getLastName())&&userInfoRealm.getFirstName().equals(userRealm.getFirstName()))
            textViewAuhtor.setTextColor(Color.BLUE);
        else
            textViewAuhtor.setTextColor(BLACK);

        textViewAuhtor.setText(userInfoRealm.getFirstName()+" "+userInfoRealm.getLastName());
    }
}
