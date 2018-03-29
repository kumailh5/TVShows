package com.kumail.tvshows;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kumail.tvshows.tmdb.ShowDetailsResponse;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by kumail on 16/02/2018.
 */

public class ExpShowFrag extends Fragment
{
	public ExpShowFrag() {
		// Required empty public constructor

	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.frag_exp_show, container, false);
//		postponeEnterTransition();

		return view;
	}


	private static final String EXTRA_SHOW_RESP = "show_resp";
	private static final String EXTRA_IMG_URL = "transition_name";
	private static final String EXTRA_TRANSITION_NAME = "transition_name";


	public static ExpShowFrag newInstance(ShowDetailsResponse sdr, String imgUrl, String transitionName) {
		ExpShowFrag expShowFrag = new ExpShowFrag();
		Bundle bundle = new Bundle();
		bundle.putParcelable(EXTRA_SHOW_RESP, sdr);
		bundle.putString(EXTRA_TRANSITION_NAME, transitionName);
		expShowFrag.setArguments(bundle);
		return expShowFrag;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		ShowDetailsResponse showResp= getArguments().getParcelable(EXTRA_SHOW_RESP);
		String transitionName = getArguments().getString(EXTRA_TRANSITION_NAME);

//		TextView detailTextView = (TextView) view.findViewById(R.id.animal_detail_text);
//		detailTextView.setText(animalItem.detail);

		ImageView imageView = (ImageView) view.findViewById(R.id.main_image);
		imageView.setTransitionName(transitionName);


//		Picasso.with(getContext())
//				.load(showResp.imageUrl)
//				.noFade()
//				.into(imageView, new Callback() {
//
//					@Override
//					public void onSuccess() {
//						startPostponedEnterTransition();
//					}
//
//					@Override
//					public void onError() {
//						startPostponedEnterTransition();
//					}
//				});
	}
}
