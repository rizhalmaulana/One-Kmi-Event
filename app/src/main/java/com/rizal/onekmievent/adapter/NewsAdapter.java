package com.rizal.onekmievent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rizal.onekmievent.R;
import com.rizal.onekmievent.model.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private ArrayList<News> semuaNews;

    public NewsAdapter(Context mContext, ArrayList<News> news) {
        this.semuaNews = news;
    }

    public void setNewsList(ArrayList<News> newsList) {
        this.semuaNews = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.Title.setText(semuaNews.get(position).getTitle());
        holder.description.setText(semuaNews.get(position).getDescription());
//        holder.content.setText(semuaNews.get(position).getContent());
        String img = semuaNews.get(position).getUrlToImage();
        Picasso.get().load(img).into(holder.backbg);
    }

    @Override
    public int getItemCount() {
        return semuaNews.size();
    }

    public void refill(ArrayList<News> semuaNews) {
        this.semuaNews = new ArrayList<>();
        this.semuaNews.addAll(semuaNews);
    }

        static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        TextView description;
//        TextView content;
        ImageView backbg;
        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.txttitle);
            description = itemView.findViewById(R.id.txtdesc);
            backbg = itemView.findViewById(R.id.imgberita);
//            content = itemView.findViewById(R.id.txtcontent);
        }
    }
}
