package com.michaelsvit.feedler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Michael on 4/21/2017.
 */

class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private Context context;
    private List<Article> articles;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.article_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articles.get(position);
        //TODO: set image
        holder.title.setText(article.getTitle());
        holder.content.setText(article.getDescription());
        //TODO: set publisher
        holder.publisher.setText("Publisher");
        //TODO: change to time from now
        holder.pubTime.setText(article.getPubDate());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView title;
        private TextView content;
        private TextView publisher;
        private TextView pubTime;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.article_item_image);
            this.title = (TextView) itemView.findViewById(R.id.article_item_title);
            this.content = (TextView) itemView.findViewById(R.id.article_item_content);
            this.publisher = (TextView) itemView.findViewById(R.id.article_item_publisher);
            this.pubTime = (TextView) itemView.findViewById(R.id.article_item_pub_time);
        }
    }
}
