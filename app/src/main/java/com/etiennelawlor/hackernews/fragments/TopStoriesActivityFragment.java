package com.etiennelawlor.hackernews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.etiennelawlor.hackernews.R;
import com.etiennelawlor.hackernews.adapters.TopStoriesRecyclerViewAdapter;
import com.etiennelawlor.hackernews.network.Api;
import com.etiennelawlor.hackernews.network.models.TopStory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class TopStoriesActivityFragment extends Fragment {

    // region Member Variables
    private TopStoriesRecyclerViewAdapter mTopStoriesRecyclerViewAdapter;
    private boolean mIsRefreshing = false;
    private long mStoryIdCount = 0;

    @Bind(R.id.top_stories_rv)
    RecyclerView mTopStoriesRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.pb)
    ProgressBar mProgressBar;
    // endregion

    // region Listeners
    private final SwipeRefreshLayout.OnRefreshListener mSwipeRefreshLayoutOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mIsRefreshing = true;
            mTopStoriesRecyclerViewAdapter.clear();
            // Refresh items
            reloadTopStories();
        }
    };
    // endregion

    // region Constructors
    public TopStoriesActivityFragment() {
    }
    // endregion

    // region Factory Methods
    public static TopStoriesActivityFragment newInstance() {
        TopStoriesActivityFragment fragment = new TopStoriesActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mTopStoriesRecyclerView.setLayoutManager(layoutManager);
        mTopStoriesRecyclerViewAdapter = new TopStoriesRecyclerViewAdapter();

        mTopStoriesRecyclerView.setAdapter(mTopStoriesRecyclerViewAdapter);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark);

        mSwipeRefreshLayout.setOnRefreshListener(mSwipeRefreshLayoutOnRefreshListener);

        loadTopStories();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    // endregion

    // region Helper Methods
    private void loadTopStories() {
        Api.getService(Api.getEndpointUrl()).getTopStoryIds()
                .concatMap(new Func1<List<Long>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<Long> storyIds) {
                        mStoryIdCount = storyIds.size();

                        return Observable.from(storyIds);
                    }
                })
                .concatMap(new Func1<Object, Observable<TopStory>>() {
                    @Override
                    public Observable<TopStory> call(Object o) {
                        Long storyId = (Long) o;
                        return Api.getService(Api.getEndpointUrl()).getTopStory(storyId);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TopStory>() {
                    @Override
                    public void call(TopStory topStory) {
                        if (!mIsRefreshing && topStory != null) {
                            Timber.d("getTopStory : success()");
                            mProgressBar.setVisibility(View.GONE);
                            mTopStoriesRecyclerViewAdapter.add(mTopStoriesRecyclerViewAdapter.getItemCount(), topStory);

                            if (mTopStoriesRecyclerViewAdapter.getItemCount() == mStoryIdCount) {
                                mSwipeRefreshLayout.setRefreshing(false);
                                mIsRefreshing = false;
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.d("getTopStory : failure()");
                    }
                });
    }

    private void reloadTopStories() {
        Api.getService(Api.getEndpointUrl()).getTopStoryIds()
                .concatMap(new Func1<List<Long>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<Long> storyIds) {
                        mStoryIdCount = storyIds.size();

                        return Observable.from(storyIds);
                    }
                })
                .concatMap(new Func1<Object, Observable<TopStory>>() {
                    @Override
                    public Observable<TopStory> call(Object o) {
                        Long storyId = (Long) o;
                        return Api.getService(Api.getEndpointUrl()).getTopStory(storyId);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TopStory>() {
                    @Override
                    public void call(TopStory topStory) {
                        if (topStory != null) {
                            Timber.d("getTopStory : success()");
                            mProgressBar.setVisibility(View.GONE);
                            mTopStoriesRecyclerViewAdapter.add(mTopStoriesRecyclerViewAdapter.getItemCount(), topStory);

                            if (mTopStoriesRecyclerViewAdapter.getItemCount() == mStoryIdCount) {
                                mSwipeRefreshLayout.setRefreshing(false);
                                mIsRefreshing = false;
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.d("getTopStory : failure()");
                    }
                });
    }
    // endregion
}
