package com.etiennelawlor.hackernews.adapters;

import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etiennelawlor.hackernews.R;
import com.etiennelawlor.hackernews.network.models.TopStory;
import com.etiennelawlor.hackernews.utilities.HackerNewsUtility;
import com.etiennelawlor.trestle.library.Span;
import com.etiennelawlor.trestle.library.Trestle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by etiennelawlor on 3/21/15.
 */
public class TopStoriesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // region Member Variables
    private final List<TopStory> topStories;
    // endregion

    // region Constructors
    public TopStoriesRecyclerViewAdapter() {
        topStories = new ArrayList<>();
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

        final TopStory topStory = topStories.get(position);

        if (topStory != null) {

            setUpTitle(holder.titleTextView, topStory);
            setUpSubtitle(holder.subTitleTextView, topStory);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String url = topStory.getUrl();
                    HackerNewsUtility.openWebPage(v.getContext(), url);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return topStories.size();
    }

    // region Helper Methods

    public void add(int position, TopStory item) {
        topStories.add(position, item);
        notifyItemInserted(position);
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(0);
        }
    }

    public void remove(int position) {
        topStories.remove(position);
        notifyItemRemoved(position);
    }

    public TopStory getItem(int position) {
        return topStories.get(position);
    }

    private void setUpTitle(TextView tv, TopStory topStory) {
        final String url = topStory.getUrl();
        final String title = topStory.getTitle();

        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            String host = uri.getHost();

            List<Span> spans = new ArrayList<>();
            spans.add(new Span.Builder(title)
                    .build());
            spans.add(new Span.Builder(" ")
                    .build());
            spans.add(new Span.Builder(String.format("(%s)", host))
                    .foregroundColor(ContextCompat.getColor(tv.getContext(), R.color.secondary_text))
                    .relativeSize(0.875f)
                    .build());
            tv.setText(Trestle.getFormattedText(spans));
        } else {
            if (!TextUtils.isEmpty(title))
                tv.setText(title);
        }
    }

    private void setUpSubtitle(TextView tv, TopStory topStory) {
        final int score = topStory.getScore();
        final String by = topStory.getBy();
        final long time = topStory.getTime();
        final int descendants = topStory.getDescendants();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        String date = HackerNewsUtility.getRelativeDate(calendar);

        String commentCount;
        if (descendants > 0) {
            commentCount = tv.getContext().getResources().getQuantityString(R.plurals.comment_count, descendants, descendants);
        } else {
            commentCount = "discuss";
        }

        tv.setText(String.format("%d points by %s • %s • %s", score, by, date, commentCount));
    }

    // endregion

    // region Inner Classes

    public static class TopStoryViewHolder extends RecyclerView.ViewHolder {

        // region Views
        @Bind(R.id.title_tv)
        TextView titleTextView;
        @Bind(R.id.subtitle_tv)
        TextView subTitleTextView;
        // endregion

        // region Constructors
        public TopStoryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        // endregion
    }

    // endregion

}
