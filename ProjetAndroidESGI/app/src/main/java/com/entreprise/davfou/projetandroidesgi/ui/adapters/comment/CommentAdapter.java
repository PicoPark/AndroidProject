package com.entreprise.davfou.projetandroidesgi.ui.adapters.comment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.data.model.api.Comment;

import java.util.ArrayList;

/**
 * Created by davidfournier on 28/06/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<MyViewHolderComment> {

    ArrayList<Comment> comments;

    private CommentAdapter.OnArticleClickedListener mOnArticleClickedListener;

    //ajouter un constructeur prenant en entrée une liste
    public CommentAdapter(ArrayList<Comment> comments) {


        this.comments = comments;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public MyViewHolderComment onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_new,viewGroup,false);
        return new MyViewHolderComment(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(MyViewHolderComment myViewHolder, final int position) {
        Comment myObject = comments.get(position);
        myViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (mOnArticleClickedListener != null) {
                    mOnArticleClickedListener.onArticleClicked(comments.get(position), view);
                }
            }
        });
        myViewHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
    /**
     * Sets on article clicked listener.
     *
     * @param onArticleClickedListener Beneficiaire clicked listener.
     */
    public void setOnArticleClickedListener(final CommentAdapter.OnArticleClickedListener onArticleClickedListener) {
        this.mOnArticleClickedListener = onArticleClickedListener;
    }

    public interface OnArticleClickedListener {

        void onArticleClicked(Comment comment, View articleView);



    }
}

class MyViewHolderComment extends RecyclerView.ViewHolder{
    public final View rootView;

    private TextView textViewTitleNews;
    private TextView textViewContentNews;

    //itemView est la vue correspondante à 1 cellule
    public MyViewHolderComment(View itemView) {
        super(itemView);
        this.rootView = itemView;
        //c'est ici que l'on fait nos findView
        textViewTitleNews = (TextView) itemView.findViewById(R.id.textViewTitleNews);
        textViewContentNews = (TextView) itemView.findViewById(R.id.textViewContentNews);
    }

    public void bind(Comment comment){
        textViewTitleNews.setText(comment.getTitle());
        textViewContentNews.setText(comment.getContent());
    }
}
