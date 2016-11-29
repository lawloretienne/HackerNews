package com.etiennelawlor.hackernews.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.etiennelawlor.hackernews.R;
import com.etiennelawlor.hackernews.fragments.TopStoriesActivityFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TopStoriesActivity extends AppCompatActivity {

    // region Member Variables
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    // endregion

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_stories);

        ButterKnife.bind(this);

//        mToolbar.setNavigationIcon(R.drawable.ic_up_navigation);
        setSupportActionBar(toolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_fl, TopStoriesActivityFragment.newInstance(), "")
                .commit();
    }
    // endregion
}
