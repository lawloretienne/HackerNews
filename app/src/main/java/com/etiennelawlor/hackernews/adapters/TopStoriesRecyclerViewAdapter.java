package com.etiennelawlor.hackernews.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.etiennelawlor.hackernews.R;
import com.etiennelawlor.hackernews.network.models.TopStory;
import com.etiennelawlor.hackernews.utils.HackerNewsUtility;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

            final String title = topStory.getTitle();
            final String url = topStory.getUrl();
            final int score = topStory.getScore();
            final String by = topStory.getBy();
            final long time = topStory.getTime();
            final int descendants = topStory.getDescendants();

            if(!TextUtils.isEmpty(url)){
                Uri uri = Uri.parse(url);
                String host = uri.getHost();
                SpannableString shortUrlSS = new SpannableString(String.format("(%s)", host));
                shortUrlSS.setSpan( new ForegroundColorSpan(mContext.getResources().getColor(R.color.grey_600)), 0, shortUrlSS.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                shortUrlSS.setSpan(new RelativeSizeSpan(0.875f), 0, shortUrlSS.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                holder.mTitleTextView.setText(TextUtils.concat(title, " ", shortUrlSS));
            } else {
                holder.mTitleTextView.setText(title);
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time * 1000);
            String date = HackerNewsUtility.getRelativeDate(calendar);

            String commentCount = "";
            if(descendants > 0){
                commentCount = mContext.getResources().getQuantityString(R.plurals.comment_count, descendants, descendants);
            } else {
                commentCount = "discuss";
            }

            holder.mSubTitleTextView.setText(String.format("%d points by %s %s | %s", score, by, date, commentCount));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HackerNewsUtility.openWebPage(mContext, url);
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
        @InjectView(R.id.subtitle_tv) TextView mSubTitleTextView;

        TopStoryViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    // endregion

}
