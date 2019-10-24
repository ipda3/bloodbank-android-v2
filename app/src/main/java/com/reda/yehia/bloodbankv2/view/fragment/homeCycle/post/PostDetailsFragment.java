package com.reda.yehia.bloodbankv2.view.fragment.homeCycle.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.model.client.ClientData;
import com.reda.yehia.bloodbankv2.data.model.post.posts.PostsData;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.loadUserData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.onLoadImageFromUrl;

public class PostDetailsFragment extends BaseFragment {

    public PostsData postsData;
    @BindView(R.id.post_details_fragment_iv_photo)
    ImageView postDetailsFragmentIvPhoto;
    @BindView(R.id.post_details_fragment_iv_is_favourite)
    ImageView postDetailsFragmentIvIsFavourite;
    @BindView(R.id.post_details_fragment_tv_title)
    TextView postDetailsFragmentTvTitle;
    @BindView(R.id.post_details_fragment_tv_description)
    TextView postDetailsFragmentTvDescription;
    Unbinder unbinder;
    private ClientData client;

    public PostDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        client = loadUserData(getActivity());
        getPostDetails();
        setUpActivity();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void getPostDetails() {
        try {
            onLoadImageFromUrl(postDetailsFragmentIvPhoto, postsData.getThumbnailFullPath(), getActivity());
            postDetailsFragmentTvTitle.setText(postsData.getTitle());
            postDetailsFragmentTvDescription.setText(postsData.getContent());

            if (postsData.getIsFavourite()) {
                postDetailsFragmentIvIsFavourite.setImageResource(R.drawable.ic_heart_solid);

            } else {
                postDetailsFragmentIvIsFavourite.setImageResource(R.drawable.ic_heart_regular);

            }
        } catch (Exception e) {

        }

    }

}
