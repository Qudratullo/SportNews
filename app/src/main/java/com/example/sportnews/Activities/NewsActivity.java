package com.example.sportnews.Activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportnews.Api;
import com.example.sportnews.Classes.Article;
import com.example.sportnews.Classes.News;
import com.example.sportnews.Constants;
import com.example.sportnews.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsActivity extends AppCompatActivity {

    ProgressBar progressSpinner;
    TextView titleGame;
    TextView timeGame;
    TextView placeGame;
    TextView tournamentGame;
    TextView predictionGame;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        /*Настройка вернуться на главную страницу*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*Получить данные из интента*/
        final String title = getIntent().getStringExtra("title");
        final String time = getIntent().getStringExtra("time");
        final String place = getIntent().getStringExtra("place");
        String article = getIntent().getStringExtra("article");

        titleGame = findViewById(R.id.title_game);
        timeGame = findViewById(R.id.time_game);
        placeGame = findViewById(R.id.place_game);
        tournamentGame = findViewById(R.id.tournament_game);
        linearLayout = findViewById(R.id.linear_layout);

        /*создать запрос*/
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);

        progressSpinner = findViewById(R.id.progressBar);
        progressSpinner.setIndeterminate(true);
        progressSpinner.getIndeterminateDrawable()
                .setColorFilter(
                        ContextCompat.getColor(this, R.color.colorPrimary),
                        PorterDuff.Mode.SRC_IN
                );

        progressSpinner.setVisibility(View.VISIBLE);

        /*запрос к серверу*/
        api.getNews(article).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    /*Отображать данные*/
                    News news = response.body();
                    titleGame.setText(title);
                    timeGame.setText(time);
                    placeGame.setText(place);
                    tournamentGame.setText(news.getTournament());
                    List <Article> articles = news.getArticle();
                    LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    /*добавить статьи на LinearLayout*/
                    for (Article item : articles) {
                        if (item.getHeader() != "") {
                            TextView header = new TextView(getApplicationContext());
                            header.setText(item.getHeader());
                            header.setTextColor(Color.BLUE);
                            header.setTextSize(17f);
                            llParams.setMargins(0, 16, 0, 0);
                            linearLayout.addView(header, llParams);
                        }
                        if (item.getText() != "") {
                            llParams.setMargins(0, 8, 0, 0);
                            TextView text = new TextView(getApplicationContext());
                            text.setText(item.getText());
                            text.setTextColor(Color.BLACK);
                            text.setTextSize(16f);
                            linearLayout.addView(text, llParams);
                        }
                    }
                    TextView header = new TextView(getApplicationContext());
                    header.setText("Прогнозирование");
                    header.setTextColor(Color.BLUE);
                    header.setTextSize(17f);
                    llParams.setMargins(0, 16, 0, 0);
                    linearLayout.addView(header, llParams);
                    llParams.setMargins(0, 16, 0, 0);
                    predictionGame = new TextView(getApplicationContext());
                    predictionGame.setText(news.getPrediction());
                    predictionGame.setTextColor(Color.BLACK);
                    predictionGame.setTextSize(16f);;
                    linearLayout.addView(predictionGame, llParams);
                    progressSpinner.setVisibility(View.GONE);
                }
                else {
                    makeToast("Ошибка обмена данными.");
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                makeToast("Не удалось подключиться к серверу.");
                progressSpinner.setVisibility(View.GONE);
            }
        });
    }

    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
