package com.example.sportnews.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sportnews.Adapters.NewsCategoryAdapter;
import com.example.sportnews.R;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = findViewById(R.id.sliding_tabs);
        viewPager = findViewById(R.id.view_pager);

        /*Настройка TabLayout*/
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorWhite));
        tabLayout.setTabTextColors(
                ContextCompat.getColor(this, android.R.color.black),
                ContextCompat.getColor(this, android.R.color.white)
        );
        tabLayout.setupWithViewPager(viewPager);

        NewsCategoryAdapter adapter = new NewsCategoryAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
