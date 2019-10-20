package com.reda.yehia.bloodbankv2.view.fragment.homeCycle;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.adapter.ViewPagerWithFragmentAdapter;
import com.reda.yehia.bloodbankv2.utils.CustomViewPager;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.donation.DonationsListFragment;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.post.PostsAndFavoritesListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.reda.yehia.bloodbankv2.utils.HelperMethod.replaceFragment;

public class HomeContainerFragment extends BaseFragment {


    @BindView(R.id.home_container_fragment_tl_tab)
    TabLayout homeContainerFragmentTlTab;
    @BindView(R.id.home_container_fragment_vp_view_pager)
    CustomViewPager homeContainerFragmentVpViewPager;
    Unbinder unbinder;

    public PostsAndFavoritesListFragment postsAndFavoritesListFragment;
    public DonationsListFragment donationsListFragment;

    public HomeContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home_container, container, false);
        unbinder = ButterKnife.bind(this, view);

        ViewPagerWithFragmentAdapter adapter = new ViewPagerWithFragmentAdapter(getChildFragmentManager());
        postsAndFavoritesListFragment = new PostsAndFavoritesListFragment();
        donationsListFragment = new DonationsListFragment();

        donationsListFragment.homeContainerFragment = this;

        adapter.addPager(postsAndFavoritesListFragment, getString(R.string.articles));
        adapter.addPager(donationsListFragment, getString(R.string.donations));

        homeContainerFragmentVpViewPager.setAdapter(adapter);
        homeContainerFragmentTlTab.setupWithViewPager(homeContainerFragmentVpViewPager);

        donationsListFragment.homeContainerFragmentVpViewPager = homeContainerFragmentVpViewPager;
        homeContainerFragmentVpViewPager.setPagingEnabled(true);

        setUpActivity();
        homeCycleActivity.setNavigation(R.id.home_cycle_activity_rb_home);
        homeCycleActivity.setToolBar(View.GONE, null, null);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}
