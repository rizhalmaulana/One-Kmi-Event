package com.rizal.onekmievent.ui.news;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rizal.onekmievent.DetailNews;
import com.rizal.onekmievent.MainActivity;
import com.rizal.onekmievent.R;
import com.rizal.onekmievent.adapter.NewsAdapter;
import com.rizal.onekmievent.base.BaseFragment;
import com.rizal.onekmievent.model.DataIndonesia;
import com.rizal.onekmievent.model.News;
import com.rizal.onekmievent.model.NewsResponses;
import com.rizal.onekmievent.utils.ApiClient;
import com.rizal.onekmievent.utils.ApiCovid;
import com.rizal.onekmievent.utils.ApiInterface;
import com.rizal.onekmievent.utils.Constant;
import com.rizal.onekmievent.utils.DataSource;
import com.rizal.onekmievent.utils.NewsSourceCallback;
import com.rizal.onekmievent.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends BaseFragment implements NewsSourceCallback {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "fa5dd2a41a2945a1a309f5d96c8baa17";
    private ProgressBar loading;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private Context context;
    private ArrayList<News> news = new ArrayList<>();
    private static final String KEY_NEWS = "news";
    private LinearLayout lnMenuNews, lnKasusCovid;
    private TextView positif, sembuh, meninggal;

    public NewsFragment(){
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        newsAdapter = new NewsAdapter(context, news);

        lnKasusCovid = view.findViewById(R.id.lnKasus);
        lnMenuNews = view.findViewById(R.id.menuNews);
        loading = view.findViewById(R.id.progressNews);
        recyclerView = view.findViewById(R.id.rv_news);

        positif = view.findViewById(R.id.datapositif);
        sembuh = view.findViewById(R.id.datasembuh);
        meninggal = view.findViewById(R.id.datameninggal);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(newsAdapter);

        Call<List<DataIndonesia>> dataIndonesiaCall = ApiCovid.service().getKasus();
        dataIndonesiaCall.enqueue(new Callback<List<DataIndonesia>>() {
            @Override
            public void onResponse(@NonNull Call<List<DataIndonesia>> call, @NonNull Response<List<DataIndonesia>> response) {
                assert response.body() != null;
                lnKasusCovid.setVisibility(View.VISIBLE);
                positif.setText(response.body().get(0).getPositif());
                sembuh.setText(response.body().get(0).getSembuh());
                meninggal.setText(response.body().get(0).getMeninggal());
            }

            @Override
            public void onFailure(@NonNull Call<List<DataIndonesia>> call, @NonNull Throwable t) {
                Toast.makeText(context, "Gagal Mengambil Data!", Toast.LENGTH_SHORT).show();
            }
        });

        if (savedInstanceState == null) {
            getDataSources().getNews(DataSource.URL_NEWS_NOW, this);
            ambildataNews();
        } else {
            news = savedInstanceState.getParcelableArrayList(KEY_NEWS);
            newsAdapter.refill(news);
            newsAdapter.notifyDataSetChanged();
        }

        return view;
    }

        private void ambildataNews() {
        loading.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getNews().create(ApiInterface.class);
        Call<NewsResponses> call = apiService.getNewsNow(API_KEY);
        call.enqueue(new Callback<NewsResponses>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponses> call, @NonNull Response<NewsResponses> response) {
                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    news = response.body().getResults();
                    recyclerView.setAdapter(new NewsAdapter(getContext(), news));
                    newsAdapter.notifyDataSetChanged();
                    initDataIntent(news);
                    lnMenuNews.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponses> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
                loading.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDataIntent(final List<News> semuaNewsItems) {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                News news = new News();
                String img = semuaNewsItems.get(position).getUrlToImage();
                String title = semuaNewsItems.get(position).getTitle();
                String desc = semuaNewsItems.get(position).getDescription();
                String content = semuaNewsItems.get(position).getContent();

                news.setUrlToImage(img);
                news.setTitle(title);
                news.setDescription(desc);
                news.setContent(content);

                Intent detailNews = new Intent(getContext(), DetailNews.class);
                detailNews.putExtra(Constant.KEY_GAMBAR_NEWS, img);
                detailNews.putExtra(Constant.KEY_NAMA_NEWS, title);
                detailNews.putExtra(Constant.KEY_DESC_NEWS, desc);
                detailNews.putExtra(Constant.KEY_CONTENT_NEWS, desc);
            }
        }));
    }

    @Override
    public void onSuccess(NewsResponses newsResponses) {
        news = newsResponses.getResults();
        newsAdapter.refill(news);
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
