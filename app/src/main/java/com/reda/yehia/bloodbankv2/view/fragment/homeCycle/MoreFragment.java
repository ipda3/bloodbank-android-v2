package com.reda.yehia.bloodbankv2.view.fragment.homeCycle;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.model.post.posts.Posts;
import com.reda.yehia.bloodbankv2.utils.HelperMethod;
import com.reda.yehia.bloodbankv2.utils.LogOutDialog;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.notification.NotificationsSettingFragment;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.post.PostsAndFavoritesListFragment;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.staticScreen.AboutAppFragment;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.staticScreen.ContactUsFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.reda.yehia.bloodbankv2.utils.HelperMethod.replaceFragment;

public class MoreFragment extends BaseFragment {


    Unbinder unbinder;

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        unbinder = ButterKnife.bind(this, view);
        setUpActivity();

        homeCycleActivity.setNavigation(View.VISIBLE,R.id.home_cycle_activity_rb_home);
        homeCycleActivity.setToolBar(View.VISIBLE, getString(R.string.more), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.more_fragment_rel_favourites, R.id.more_fragment_rel_contact_us, R.id.more_fragment_rel_about_app, R.id.more_fragment_rel_rate, R.id.more_fragment_rel_notifications_settings, R.id.more_fragment_rel_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more_fragment_rel_favourites:
                PostsAndFavoritesListFragment postsAndFavoritesList = new PostsAndFavoritesListFragment();
                postsAndFavoritesList.favourites = true;
                replaceFragment(getFragmentManager(), R.id.home_cycle_activity_fl_home_frame, postsAndFavoritesList);

                break;
            case R.id.more_fragment_rel_contact_us:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_cycle_activity_fl_home_frame
                        , new ContactUsFragment());
                break;
            case R.id.more_fragment_rel_about_app:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_cycle_activity_fl_home_frame
                        , new AboutAppFragment());
                break;
            case R.id.more_fragment_rel_rate:
                break;
            case R.id.more_fragment_rel_notifications_settings:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_cycle_activity_fl_home_frame
                        , new NotificationsSettingFragment());
                break;
            case R.id.more_fragment_rel_sign_out:
                LogOutDialog dialog = new LogOutDialog();
                dialog.showDialog(getActivity());

                break;
        }
    }
}
