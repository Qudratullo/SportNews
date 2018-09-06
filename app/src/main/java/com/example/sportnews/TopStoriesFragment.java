package com.example.sportnews;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sportnews.Classes.AllNews;
import com.example.sportnews.Classes.NewsItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopStoriesFragment extends Fragment {

    private static final String NEWS_CATEGORY = "CATEGORY";
    private String newsCategory;
    private NewsAdapter adapter;
    private ProgressBar progressSpinner;

    public TopStoriesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newsCategory = getArguments().getString(NEWS_CATEGORY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);

        final RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NewsAdapter(new ArrayList<NewsItem>(), getActivity()));
//
        progressSpinner = rootView.findViewById(R.id.progress_spinner);
        progressSpinner.setIndeterminate(true);
        progressSpinner.getIndeterminateDrawable()
                .setColorFilter(
                        ContextCompat.getColor(getContext(), R.color.colorPrimary),
                        PorterDuff.Mode.SRC_IN
                );

        progressSpinner.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        api.getAllNews(newsCategory).enqueue(new Callback<AllNews>() {
            @Override
            public void onResponse(Call<AllNews> call, Response<AllNews> response) {
                if (response.isSuccessful()) {
                    adapter = new NewsAdapter(response.body().getEvents(), getActivity());
                    recyclerView.setAdapter(adapter);
                }
                else {
                    makeToast("Ошибка обмена данными.");
                }
                progressSpinner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<AllNews> call, Throwable t) {
                makeToast("Не удалось подключиться к серверу.");
                progressSpinner.setVisibility(View.GONE);
            }
        });

        return rootView;
    }

    private void makeToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    static TopStoriesFragment newInstance(String category) {
        TopStoriesFragment fragment = new TopStoriesFragment();

        Bundle bundle = new Bundle(1);

        bundle.putString(NEWS_CATEGORY, category);

        fragment.setArguments(bundle);

        return fragment;
    }
}
