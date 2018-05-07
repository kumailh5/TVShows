package com.kumail.tvshows.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumail.tvshows.R;
import com.kumail.tvshows.db.entity.ShowEntity;
import com.kumail.tvshows.db.entity.WatchedEntity;
import com.kumail.tvshows.showdetails.ExpandedShowActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;
import timber.log.Timber;

/**
 * Created by kumail on 11/11/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    //	private ArrayList<Show> shows;
    private int rowLayout;
    private List<ShowEntity> shows;
    private List<WatchedEntity> watchedShows;
    private Context mContext;


    public HomeAdapter(List<WatchedEntity> list, Context mContext) {
        this.watchedShows = list;
        this.mContext = mContext;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView progressText;
        ImageView backgroundImage;

        public ViewHolder(View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.text_title);
            progressText = itemView.findViewById(R.id.text_progress);
            backgroundImage = itemView.findViewById(R.id.image_background);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_card, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        WatchedEntity we = watchedShows.get(position);
        String url = we.getBackdropUrl();
        Timber.d(we.getName());
//		Timber.d("Title", sdr.getName());
        viewHolder.titleText.setText(we.getName());
        int missed = we.getTotalEpisodes() - we.getPlays();
        viewHolder.progressText.setText(String.format("%d missed", missed));

        Timber.d("Here bind");

        Picasso.with(mContext)

                .load(url)
                .fit()
                .centerCrop()
                .transform(new VignetteFilterTransformation(
                        mContext, new PointF(0.5f, 0.5f),
                        new float[]{0.0f, 0.0f, 0.0f}, 0.0f, 0.9f))
                .into(viewHolder.backgroundImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, ExpandedShowActivity.class);

                i.putExtra("EXTRA_SHOW_TMDB_ID", we.getTmdbId());
                mContext.startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        Timber.d(String.valueOf(watchedShows.size()));
        return watchedShows.size();
    }

    public void addItems(List<WatchedEntity> lwe) {
        this.watchedShows = lwe;
        Timber.d("Here");
        notifyDataSetChanged();
    }


}
