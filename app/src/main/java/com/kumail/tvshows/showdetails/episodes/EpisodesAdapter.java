package com.kumail.tvshows.showdetails.episodes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kumail.tvshows.R;
import com.kumail.tvshows.db.entity.EpisodesEntity;

import java.util.List;

import timber.log.Timber;

public class EpisodesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<ItemObject> itemObjects;
    private List<EpisodesEntity> episodes;
    private Context mContext;

    public EpisodesAdapter(List<EpisodesEntity> episodes, Context mContext) {
        this.episodes = episodes;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_episodes_card, parent, false);
            return new HeaderViewHolder(layoutView);
        } else if (viewType == TYPE_ITEM) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episodes_card, parent, false);
            return new ItemViewHolder(layoutView);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
//        ItemObject mObject = itemObjects.get(position);
//        if (viewHolder instanceof HeaderViewHolder) {
//            ((HeaderViewHolder) viewHolder).headerTitle.setText(mObject.getContents());
//        } else if (viewHolder instanceof ItemViewHolder) {
//            ((ItemViewHolder) viewHolder).itemContent.setText(mObject.getContents());
//        }
        EpisodesEntity ee = episodes.get(position);
        if (viewHolder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) viewHolder).titleText.setText(String.format("Season: %d", ee.getNumber()));
            ((HeaderViewHolder) viewHolder).progressText.setText(String.format("%d/%d Watched", ee.getCompleted(), ee.getAired()));
        } else if (viewHolder instanceof ItemViewHolder) {
            ((ItemViewHolder) viewHolder).numberText.setText(String.format("%d", ee.getNumber()));
            if (!ee.getEpisodeCompleted()) {
//                ((ItemViewHolder) viewHolder).numberText.setBackgroundColor(Color.TRANSPARENT);
                ((ItemViewHolder) viewHolder).numberText.setTextColor(Color.BLACK);
                ((ItemViewHolder) viewHolder).numberText.setBackground(mContext.getDrawable(R.drawable.circle_hollow));
            }

            ((ItemViewHolder) viewHolder).titleText.setText(ee.getTitle());
            ((ItemViewHolder) viewHolder).overviewText.setText(ee.getOverview());
            ((ItemViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ee.getEpisodeCompleted()) {
                        ((ItemViewHolder) viewHolder).numberText.setTextColor(Color.BLACK);
                        ((ItemViewHolder) viewHolder).numberText.setBackground(mContext.getDrawable(R.drawable.circle_hollow));
                    }
                    if (!ee.getEpisodeCompleted()) {
                        ((ItemViewHolder) viewHolder).numberText.setTextColor(Color.WHITE);
                        ((ItemViewHolder) viewHolder).numberText.setBackground(mContext.getDrawable(R.drawable.circle));
                    }

                }
            });
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;
        private TextView progressText;

        HeaderViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.text_title);
            progressText = itemView.findViewById(R.id.text_progress);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView numberText;
        private TextView titleText;
        private TextView overviewText;

        ItemViewHolder(View itemView) {
            super(itemView);
            numberText = itemView.findViewById(R.id.text_number);
            titleText = itemView.findViewById(R.id.text_title);
            overviewText = itemView.findViewById(R.id.text_overview);
        }
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (episodes.get(position).getType() == 0)
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    public void addItems(List<EpisodesEntity> lee) {
        this.episodes = lee;
        Timber.d("Here");
        notifyDataSetChanged();
    }
}
