package com.entreprise.davfou.projetandroidesgi.ui.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.New;

import java.util.ArrayList;

/**
 * Created by davidfournier on 28/06/2017.
 */

public class NewAdapter extends RecyclerView.Adapter<MyViewHolderNews> {

    ArrayList<New> news;

    private NewAdapter.OnArticleClickedListener mOnArticleClickedListener;

    //ajouter un constructeur prenant en entrée une liste
    public NewAdapter(ArrayList<New> news) {


        this.news = news;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public MyViewHolderNews onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_new,viewGroup,false);
        return new MyViewHolderNews(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(MyViewHolderNews myViewHolder, final int position) {
        New myObject = news.get(position);
        myViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (mOnArticleClickedListener != null) {
                    mOnArticleClickedListener.onArticleClicked(news.get(position), view);
                }
            }
        });
        myViewHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
    /**
     * Sets on article clicked listener.
     *
     * @param onArticleClickedListener Beneficiaire clicked listener.
     */
    public void setOnArticleClickedListener(final NewAdapter.OnArticleClickedListener onArticleClickedListener) {
        this.mOnArticleClickedListener = onArticleClickedListener;
    }

    public interface OnArticleClickedListener {

        void onArticleClicked(New newRealm, View articleView);

    }
}

class MyViewHolderNews extends RecyclerView.ViewHolder{
    public final View rootView;

    private TextView textViewAuthorNews;

    //itemView est la vue correspondante à 1 cellule
    public MyViewHolderNews(View itemView) {
        super(itemView);
        this.rootView = itemView;


        //c'est ici que l'on fait nos findView

        textViewAuthorNews = (TextView) itemView.findViewById(R.id.textViewAuthorNews);
    }

    public void bind(New newRealm){
        System.out.println(newRealm.getAuthor());
        textViewAuthorNews.setText(newRealm.getTitle());
    }
}
