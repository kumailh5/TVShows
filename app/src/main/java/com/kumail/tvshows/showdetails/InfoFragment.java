package com.kumail.tvshows.showdetails;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kumail.tvshows.R;
import com.kumail.tvshows.db.AppDatabase;
import com.kumail.tvshows.db.entity.InfoEntity;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * Created by kumail on 23/03/2018.
 */

public class InfoFragment extends Fragment
{
//	private TrendingEntity show;
	private InfoEntity show;
	private AppDatabase db;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_info, container, false);
		TextView tileText;
		ImageButton addImgButton;
		if(show != null){
            String name = show.getName();
			tileText = view.findViewById(R.id.text_title);
			tileText.setText(name);

            String imgUrl = show.getPosterUrl();
            ImageView img = view.findViewById(R.id.iv_main);
            Picasso.with(getContext())
                    .load(imgUrl)
                    .into(img);

            TextView tvYear = view.findViewById(R.id.text_year);
            tvYear.setText(String.format(Locale.ENGLISH, "%s", show.getFirstAirDate().substring(0,4)));


            TextView tvOverview = view.findViewById(R.id.tv_overview);
            tvOverview.setText(show.getOverview());


			addImgButton =view.findViewById(R.id.img_btn_add);
			addImgButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String s = "Show Added";
					addImgButton.setColorFilter(ContextCompat.getColor(getContext(), R.color.green));
					Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
				}
			});


		}

//		Timber.d(String.format(Locale.ENGLISH, "onCreateView %d", ((ExpandedShowActivity) getActivity()).getTmdbId()));

		return view;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		db =  AppDatabase.getInstance(getActivity());
		AsyncTask.execute(() -> show = db.infoDao().loadShow(((ExpandedShowActivity) getActivity()).getTmdbId()));

	}
}
