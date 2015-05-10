package com.etiennelawlor.hackernews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etiennelawlor.hackernews.R;
import com.etiennelawlor.hackernews.adapters.TopStoriesRecyclerViewAdapter;
import com.etiennelawlor.hackernews.network.Api;
import com.etiennelawlor.hackernews.network.models.TopStory;

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
import rx.schedulers.Schedulers;
import timber.log.Timber;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopStoriesActivityFragment extends Fragment {


    // region Member Variables
    private LinearLayoutManager mLayoutManager;
    private TopStoriesRecyclerViewAdapter mTopStoriesRecyclerViewAdapter;
    private TopStory mTopStory;

    @InjectView(R.id.top_stories_rv) RecyclerView mTopStoriesRecyclerView;
    // endregion


    // region Callbacks

    private Callback<List<Long>> mGetTopStoryIdsCallback = new Callback<List<Long>>() {
        @Override
        public void success(List<Long> storyIds, Response response) {
            if(isAdded() && isResumed()) {
                if(storyIds != null){
                    Timber.d("mGetTopStoryIdsCallback : success()");

//

//                    for(Long storyId : storyIds){
//                        Api.getService(Api.getEndpointUrl()).getTopStory(storyId, mGetTopStoryCallback);
//                    }

                    // TODO This implementation is not exactly correct, since the response to these endpoints come
                    // back in an arbitraty order.  In order to properly handle this situation I would need to use
                    // Reactive Extension (https://github.com/ReactiveX/RxJava)

                    for(int i=0; i<5; i++){
                        long storyId = storyIds.get(i);
                        Api.getService(Api.getEndpointUrl()).getTopStory(storyId, mGetTopStoryCallback);

//                        Api.getService(Api.getEndpointUrl())
//                                .getTopStory(storyId)
////                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .flatMap()
//                                .subscribe(new Action1<TopStory>() {
//                                    @Override
//                                    public void call(TopStory topStory) {
//                                        if (topStory != null) {
//                                            mTopStoriesRecyclerViewAdapter.add(mTopStoriesRecyclerViewAdapter.getItemCount(), topStory);
//                                        }
//                                    }
//                                }, new Action1<Throwable>() {
//                                    @Override
//                                    public void call(Throwable throwable) {
//                                        //todo: error handle this
//                                        Timber.e(throwable, "HackerNews API Error");
//                                    }
//                                });

                    }


                }
            }
        }

        @Override
        public void failure(RetrofitError error) {
            if(isAdded() && isResumed()) {

            }
        }
    };

    private Callback<TopStory> mGetTopStoryCallback =  new Callback<TopStory>() {
        @Override
        public void success(TopStory topStory, Response response) {
            if(isAdded() && isResumed()) {
                if(topStory != null){
                    Timber.d("mGetTopStoryCallback : success()");

                    mTopStory = topStory;

                    mTopStoriesRecyclerViewAdapter.add(mTopStoriesRecyclerViewAdapter.getItemCount(), mTopStory);

                }
            }
        }

        @Override
        public void failure(RetrofitError error) {
            if(isAdded() && isResumed()) {

            }
        }
    };
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

        Api.getService(Api.getEndpointUrl()).getTopStoryIds(mGetTopStoryIdsCallback);

//        Api.getService(Api.getEndpointUrl()).getTopStoryIds()
//                .flatMap(new Func1<List<Long>, Observable<Long>>() {
//                    @Override
//                    public Observable<Long> call(List<Long> storyIds) {
//                        return Observable.from(storyIds);
//                    }
//                })
//                .subscribe(
//                        Api.getService(Api.getEndpointUrl()).getTopStory(storyId)
//                );


    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    // endregion
}
