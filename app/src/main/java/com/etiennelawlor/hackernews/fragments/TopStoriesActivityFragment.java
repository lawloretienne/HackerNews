package com.etiennelawlor.hackernews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class TopStoriesActivityFragment extends Fragment {

    // region Member Variables
    private LinearLayoutManager mLayoutManager;
    private TopStoriesRecyclerViewAdapter mTopStoriesRecyclerViewAdapter;

    @InjectView(R.id.top_stories_rv) RecyclerView mTopStoriesRecyclerView;
    @InjectView(R.id.pb) ProgressBar mProgressBar;
    // endregion

    // region Callbacks
    // endregion

    // region Constructors
    public static TopStoriesActivityFragment newInstance() {
        TopStoriesActivityFragment fragment = new TopStoriesActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TopStoriesActivityFragment() {
    }
    // endregion

    // region Lifecycle Methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mTopStoriesRecyclerView.setLayoutManager(mLayoutManager);
        mTopStoriesRecyclerViewAdapter = new TopStoriesRecyclerViewAdapter(getActivity());

        mTopStoriesRecyclerView.setAdapter(mTopStoriesRecyclerViewAdapter);

        Api.getService(Api.getEndpointUrl()).getTopStoryIds()
                .concatMap(new Func1<List<Long>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<Long> storyIds) {
                        return Observable.from(storyIds);
                    }
                })
                .concatMap(new Func1<Object, Observable<TopStory>>() {
                    @Override
                    public Observable<TopStory> call(Object o) {
                        Long storyId = (Long)o;
                        return Api.getService(Api.getEndpointUrl()).getTopStory(storyId);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TopStory>() {
                    @Override
                    public void call(TopStory topStory) {
                        if(topStory != null){
                            Timber.d("getTopStory : success()");
                            mProgressBar.setVisibility(View.GONE);
                            mTopStoriesRecyclerViewAdapter.add(mTopStoriesRecyclerViewAdapter.getItemCount(), topStory);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.d("getTopStory : failure()");
                    }
                });
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    // endregion
}
