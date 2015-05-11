package com.etiennelawlor.hackernews.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.etiennelawlor.hackernews.R;
import com.etiennelawlor.hackernews.fragments.TopStoriesActivityFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TopStoriesActivity extends AppCompatActivity {

    // region Member Variables
    @InjectView(R.id.toolbar)Toolbar mToolbar;
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_stories);

        ButterKnife.inject(this);

//        mToolbar.setNavigationIcon(R.drawable.ic_up_navigation);
        setSupportActionBar(mToolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_fl, TopStoriesActivityFragment.newInstance(), "")
                .commit();

    }
}
