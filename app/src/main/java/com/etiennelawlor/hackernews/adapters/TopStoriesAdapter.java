package com.etiennelawlor.hackernews.adapters;

import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import timber.log.Timber;

/**
 * Created by etiennelawlor on 3/21/15.
 */
public class TopStoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // region Constants
    public static final int HEADER = 0;
    public static final int ITEM = 1;
    public static final int FOOTER = 2;
    // endregion

    // region Member Variables
    private final List<TopStory> topStories;
    private OnItemClickListener onItemClickListener;
    private OnReloadClickListener onReloadClickListener;
    private boolean isFooterAdded = false;
    private FooterViewHolder footerViewHolder;
    // endregion

    // region Interfaces
    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public interface OnReloadClickListener {
        void onReloadClick();
    }
    // endregion

    // region Constructors
    public TopStoriesAdapter() {
        topStories = new ArrayList<>();
    }
    // endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case HEADER:
                break;
            case ITEM:
                viewHolder = createTopStoryViewHolder(parent);
                break;
            case FOOTER:
                viewHolder = createFooterViewHolder(parent);
                break;
            default:
                Timber.e("[ERR] type is not supported!!! type is %d", viewType);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case HEADER:
                break;
            case ITEM:
                bindTopStoryViewHolder(viewHolder, position);
                break;
            case FOOTER:
                bindFooterViewHolder(viewHolder);
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return topStories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == topStories.size()-1 && isFooterAdded) ? FOOTER : ITEM;
    }

    // region Helper Methods

    private RecyclerView.ViewHolder createTopStoryViewHolder(ViewGroup parent) {
        // create a new view
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

    private RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
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

    private void bindTopStoryViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final TopStoryViewHolder holder = (TopStoryViewHolder) viewHolder;

        final TopStory topStory = topStories.get(position);
        if (topStory != null) {
            setUpTitle(holder.titleTextView, topStory);
            setUpSubtitle(holder.subTitleTextView, topStory);
        }
    }

    private void bindFooterViewHolder(RecyclerView.ViewHolder viewHolder) {
        FooterViewHolder holder = (FooterViewHolder) viewHolder;
        footerViewHolder = holder;
    }

    public void add(TopStory item) {
        topStories.add(item);
        notifyItemInserted(topStories.size()-1);
    }

    public void addAll(List<TopStory> topStories) {
        for (TopStory topStory : topStories) {
            add(topStory);
        }
    }

    public void remove(TopStory item) {
        int position = topStories.indexOf(item);
        if (position > -1) {
            topStories.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isFooterAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addFooter(){
        isFooterAdded = true;
        add(new TopStory());
    }

    public void removeFooter() {
        isFooterAdded = false;

        int position = topStories.size() - 1;
        TopStory item = getItem(position);

        if (item != null) {
            topStories.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void updateFooter(FooterType footerType){
        switch (footerType) {
            case LOAD_MORE:
                if(footerViewHolder!= null){
                    footerViewHolder.errorRelativeLayout.setVisibility(View.GONE);
                    footerViewHolder.loadingRelativeLayout.setVisibility(View.VISIBLE);
                }
                break;
            case ERROR:
                if(footerViewHolder!= null){
                    footerViewHolder.loadingRelativeLayout.setVisibility(View.GONE);
                    footerViewHolder.errorRelativeLayout.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    public TopStory getItem(int position) {
        return topStories.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnReloadClickListener(OnReloadClickListener onReloadClickListener) {
        this.onReloadClickListener = onReloadClickListener;
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

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        // region Views
        @Bind(R.id.loading_rl)
        RelativeLayout loadingRelativeLayout;
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


    public enum FooterType {
        LOAD_MORE,
        ERROR
    }

    // endregion

}
