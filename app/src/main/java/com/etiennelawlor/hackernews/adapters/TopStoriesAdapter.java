package com.etiennelawlor.hackernews.adapters;

import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etiennelawlor.hackernews.R;
import com.etiennelawlor.hackernews.network.models.TopStory;
import com.etiennelawlor.hackernews.utilities.DateUtility;
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
public class TopStoriesAdapter extends BaseAdapter<TopStory> {

    // region Member Variables
    private FooterViewHolder footerViewHolder;
    // endregion

    // region Constructors
    public TopStoriesAdapter() {
        super();
    }
    // endregion

    @Override
    public int getItemViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ? FOOTER : ITEM;
    }

    @Override
    protected RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_story_row, parent, false);

        final TopStoryViewHolder holder = new TopStoryViewHolder(v);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if(adapterPos != RecyclerView.NO_POSITION){
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(adapterPos, holder.itemView);
                    }
                }
            }
        });

        return holder;
    }

    @Override
    protected RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_footer, parent, false);

        final FooterViewHolder holder = new FooterViewHolder(v);
        holder.reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onReloadClickListener != null){
                    onReloadClickListener.onReloadClick();
                }
            }
        });

        return holder;
    }

    @Override
    protected void bindHeaderViewHolder(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final TopStoryViewHolder holder = (TopStoryViewHolder) viewHolder;

        final TopStory topStory = getItem(position);
        if (topStory != null) {
            setUpTitle(holder.titleTextView, topStory);
            setUpSubtitle(holder.subTitleTextView, topStory);
        }
    }

    @Override
    protected void bindFooterViewHolder(RecyclerView.ViewHolder viewHolder) {
        FooterViewHolder holder = (FooterViewHolder) viewHolder;
        footerViewHolder = holder;
    }

    @Override
    protected void displayLoadMoreFooter() {
        if(footerViewHolder!= null){
            footerViewHolder.errorRelativeLayout.setVisibility(View.GONE);
            footerViewHolder.loadingFrameLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void displayErrorFooter() {
        if(footerViewHolder!= null){
            footerViewHolder.loadingFrameLayout.setVisibility(View.GONE);
            footerViewHolder.errorRelativeLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addFooter() {
        isFooterAdded = true;
        add(new TopStory());
    }

    // region Helper Methods
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
        String date = DateUtility.getFormattedDateAndTime(calendar, DateUtility.FORMAT_RELATIVE);

        String commentCount;
        if (descendants > 0) {
            commentCount = tv.getContext().getResources().getQuantityString(R.plurals.comment_count, descendants, descendants);
        } else {
            commentCount = tv.getContext().getString(R.string.discuss);
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

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        // region Views
        @Bind(R.id.loading_fl)
        FrameLayout loadingFrameLayout;
        @Bind(R.id.error_rl)
        RelativeLayout errorRelativeLayout;
        @Bind(R.id.pb)
        ProgressBar progressBar;
        @Bind(R.id.reload_btn)
        Button reloadButton;
        // endregion

        // region Constructors
        public FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        // endregion
    }

    // endregion

}
