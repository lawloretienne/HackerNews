package com.etiennelawlor.hackernews.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.etiennelawlor.hackernews.R;
import com.etiennelawlor.hackernews.adapters.TopStoriesRecyclerViewAdapter;
import com.etiennelawlor.hackernews.network.Api;
import com.etiennelawlor.hackernews.network.models.TopStory;
import com.etiennelawlor.hackernews.utilities.FontCache;
import com.etiennelawlor.hackernews.utilities.TrestleUtility;

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

    // region Views
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.top_stories_rv)
    RecyclerView topStoriesRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.pb)
    ProgressBar progressBar;
    // endregion

    // region Member Variables
    private TopStoriesRecyclerViewAdapter topStoriesRecyclerViewAdapter;
    private boolean isRefreshing = false;
    private long storyIdCount = 0;
    private Typeface font;
    // endregion

    // region Listeners
    private final SwipeRefreshLayout.OnRefreshListener swipeRefreshLayoutOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            isRefreshing = true;
            topStoriesRecyclerViewAdapter.clear();
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
    public static TopStoriesActivityFragment newInstance(Bundle extras) {
        TopStoriesActivityFragment fragment = new TopStoriesActivityFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    public static TopStoriesActivityFragment newInstance() {
        TopStoriesActivityFragment fragment = new TopStoriesActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        font = FontCache.getTypeface("Lato-Medium.ttf", getContext());
    }

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

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(TrestleUtility.getFormattedText("HackerNews", font));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        topStoriesRecyclerView.setLayoutManager(layoutManager);
        topStoriesRecyclerViewAdapter = new TopStoriesRecyclerViewAdapter();

        topStoriesRecyclerView.setAdapter(topStoriesRecyclerViewAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark);

        swipeRefreshLayout.setOnRefreshListener(swipeRefreshLayoutOnRefreshListener);

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
                        storyIdCount = storyIds.size();

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
                        if (!isRefreshing && topStory != null) {
                            Timber.d("getTopStory : success()");
                            progressBar.setVisibility(View.GONE);
                            topStoriesRecyclerViewAdapter.add(topStoriesRecyclerViewAdapter.getItemCount(), topStory);

                            if (topStoriesRecyclerViewAdapter.getItemCount() == storyIdCount) {
                                swipeRefreshLayout.setRefreshing(false);
                                isRefreshing = false;
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
                        storyIdCount = storyIds.size();

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
                            progressBar.setVisibility(View.GONE);
                            topStoriesRecyclerViewAdapter.add(topStoriesRecyclerViewAdapter.getItemCount(), topStory);

                            if (topStoriesRecyclerViewAdapter.getItemCount() == storyIdCount) {
                                swipeRefreshLayout.setRefreshing(false);
                                isRefreshing = false;
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
