package com.example.sportnews;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sportnews.Classes.NewsItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CardViewHolder> {

    List<NewsItem> newsItems;
    Context context;

    NewsAdapter(List<NewsItem> newsItems, Context context) {
        this.newsItems = newsItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        final NewsItem item = newsItems.get(position);
        holder.title.setText(item.getTitle());
        holder.place.setText(item.getPlace());
        holder.coefficient.setText(item.getCoefficient());
        holder.time.setText(item.getTime());
        holder.preview.setText(item.getPreview());
        final String title = item.getTitle();
        int ind = title.indexOf(':');
        final int index_char = ind >= 0 ? ind : title.length();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtra("time", item.getTime());
                intent.putExtra("title", title.substring(0, index_char));
                intent.putExtra("place", item.getPlace());
                intent.putExtra("article", item.getArticle());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title;
        TextView place;
        TextView coefficient;
        TextView time;
        TextView preview;

        public CardViewHolder(View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.card_view);
            this.title = itemView.findViewById(R.id.title);
            this.place = itemView.findViewById(R.id.place);
            this.coefficient = itemView.findViewById(R.id.coefficient);
            this.time = itemView.findViewById(R.id.time);
            this.preview = itemView.findViewById(R.id.preview);
        }
    }
}
