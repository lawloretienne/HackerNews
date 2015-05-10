package com.etiennelawlor.hackernews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.etiennelawlor.hackernews.R;
import com.etiennelawlor.hackernews.network.models.TopStory;
import com.etiennelawlor.hackernews.utils.HackerNewsUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by etiennelawlor on 3/21/15.
 */
public class TopStoriesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // region Member Variables
    private Context mContext;
    private List<TopStory> mTopStories;
    // endregion

    // region Listeners
    // endregion

    // region Constructors
    public TopStoriesRecyclerViewAdapter(Context context) {
        mContext = context;
        mTopStories = new ArrayList<>();
    }
    // endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_story_row, parent, false);

        TopStoryViewHolder vh = new TopStoryViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        TopStoryViewHolder holder = (TopStoryViewHolder) viewHolder;

        final TopStory topStory = mTopStories.get(position);

        if(topStory != null){
            holder.mTitleTextView.setText(topStory.getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HackerNewsUtility.openWebPage(mContext, topStory.getUrl());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mTopStories.size();
    }

    // region Helper Methods

    public void add(int position, TopStory item) {
        mTopStories.add(position, item);
        notifyItemInserted(position);
    }

    public TopStory getItem(int position){
        return mTopStories.get(position);
    }

    // endregion

    // region Inner Classes

    public static class TopStoryViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.title_tv) TextView mTitleTextView;

        TopStoryViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    // endregion

}
