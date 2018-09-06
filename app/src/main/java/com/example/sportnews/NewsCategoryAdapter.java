package com.example.sportnews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sportnews.R;
import com.example.sportnews.TopStoriesFragment;

public class NewsCategoryAdapter extends FragmentPagerAdapter {

    private String [] categories = {"football", "hockey", "tennis", "basketball", "volleyball", "cybersport"};
    private Resources resources;

    NewsCategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        resources = context.getResources();
    }

    /** Instantiate fragment based on user horizontal scroll position */
    @Override
    public Fragment getItem(int position) {
        return TopStoriesFragment.newInstance(categories[position]);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return resources.getString(R.string.football_news_title);
            case 1:
                return resources.getString(R.string.hockey_news_title);
            case 2:
                return resources.getString(R.string.tennis_news_title);
            case 3:
                return resources.getString(R.string.basketball_news_title);
            case 4:
                return resources.getString(R.string.volleyball_news_title);
            case 5:
                return resources.getString(R.string.cybersport_news_title);
            default:
                return null;
        }
    }
}
