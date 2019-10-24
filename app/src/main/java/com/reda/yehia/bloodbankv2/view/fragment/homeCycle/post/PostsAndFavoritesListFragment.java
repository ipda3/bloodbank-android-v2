package com.reda.yehia.bloodbankv2.view.fragment.homeCycle.post;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.adapter.PostsAndFavouritesAdapter;
import com.reda.yehia.bloodbankv2.adapter.SpinnerAdapter;
import com.reda.yehia.bloodbankv2.data.model.client.ClientData;
import com.reda.yehia.bloodbankv2.data.model.post.posts.Posts;
import com.reda.yehia.bloodbankv2.data.model.post.posts.PostsData;
import com.reda.yehia.bloodbankv2.utils.OnEndLess;
import com.reda.yehia.bloodbankv2.view.fragment.BaseFragment;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.MoreFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.reda.yehia.bloodbankv2.data.api.RetrofitClient.getClient;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.loadUserData;
import static com.reda.yehia.bloodbankv2.utils.GeneralRequest.getSpinnerData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.disappearKeypad;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.replaceFragment;
import static com.reda.yehia.bloodbankv2.utils.network.InternetState.isConnected;

public class PostsAndFavoritesListFragment extends BaseFragment {

    @BindView(R.id.posts_favourites_list_Fragment_sp_categories)
    Spinner postsFavouritesListFragmentSpCategories;
    @BindView(R.id.posts_favourites_list_Fragment_iv_search)
    ImageView postsFavouritesListFragmentIvSearch;
    @BindView(R.id.Sfl_ShimmerFrameLayout)
    ShimmerFrameLayout SflShimmerFrameLayout;
    @BindView(R.id.posts_favourites_list_Fragment_rv_list)
    RecyclerView postsFavouritesListFragmentRvList;
    @BindView(R.id.load_more)
    RelativeLayout loadMore;
    @BindView(R.id.error_sub_view)
    LinearLayout errorSubView;
    Unbinder unbinder;
    @BindView(R.id.posts_favourites_list_Fragment_sr_refresh_posts)
    SwipeRefreshLayout postsFavouritesListFragmentSrRefreshPosts;
    @BindView(R.id.error_image)
    ImageView errorImage;
    @BindView(R.id.error_title)
    TextView errorTitle;
    @BindView(R.id.error_action)
    TextView errorAction;
    @BindView(R.id.posts_favourites_list_Fragment_et_keyword)
    EditText postsFavouritesListFragmentEtKeyword;
    @BindView(R.id.posts_favourites_list_Fragment_tv_no_results)
    TextView postsFavouritesListFragmentTvNoResults;
    @BindView(R.id.posts_favourites_list_Fragment_lin_toolbar)
    LinearLayout postsFavouritesListFragmentLinToolbar;

    private ClientData clientData;
    private SpinnerAdapter categoriesAdapter;
    private LinearLayoutManager linearLayout;
    private Integer maxPage = 0;
    private OnEndLess onEndLess;
    private boolean Filter = false;
    private String keyword = "";
    public boolean favourites = false;
    public List<PostsData> postsData = new ArrayList<>();
    public PostsAndFavouritesAdapter postsAndFavouritesAdapter;

    public PostsAndFavoritesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts_favourites_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        clientData = loadUserData(getActivity());
        setUpActivity();
        if (favourites) {
            postsFavouritesListFragmentLinToolbar.setVisibility(View.GONE);

            homeCycleActivity.setNavigation(View.GONE, R.id.home_cycle_activity_rb_home);
            homeCycleActivity.setToolBar(View.VISIBLE, getString(R.string.favourites), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBack();
                }
            });
        }
        categoriesAdapter = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), postsFavouritesListFragmentSpCategories, categoriesAdapter, getString(R.string.select_category),
                getClient().getCategories(), null, 0, false);
        init();
        return view;
    }

    private void init() {
        linearLayout = new LinearLayoutManager(getActivity());
        postsFavouritesListFragmentRvList.setLayoutManager(linearLayout);

        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        loadMore.setVisibility(View.VISIBLE);
                        if (Filter) {
                            onFilter(current_page);
                        } else {
                            getPosts(current_page);
                        }

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        postsFavouritesListFragmentRvList.addOnScrollListener(onEndLess);

        postsAndFavouritesAdapter = new PostsAndFavouritesAdapter(getActivity(), getActivity(), postsData, favourites);
        postsFavouritesListFragmentRvList.setAdapter(postsAndFavouritesAdapter);

        getPosts(1);

        postsFavouritesListFragmentSrRefreshPosts.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Filter) {
                    onFilter(1);
                } else {
                    getPosts(1);
                }

            }
        });
    }

    private void getPosts(int page) {
        Call<Posts> call;

        if (!favourites) {
            call = getClient().getPosts(clientData.getApiToken(), page);
        } else {
            call = getClient().getFavouritesList(clientData.getApiToken(), page);
        }

        startCall(call, page);
    }

    private void onFilter(int page) {
        keyword = postsFavouritesListFragmentEtKeyword.getText().toString().trim();

        Filter = true;

        Call<Posts> call = getClient().getPostFilter(
                clientData.getApiToken(), keyword, page, categoriesAdapter.selectedId);

        startCall(call, page);
    }

    private void startCall(Call<Posts> call, int page) {
        errorSubView.setVisibility(View.GONE);
        if (page == 1) {
            reInit();
            SflShimmerFrameLayout.startShimmer();
            SflShimmerFrameLayout.setVisibility(View.VISIBLE);
            postsFavouritesListFragmentTvNoResults.setVisibility(View.GONE);

        }

        if (isConnected(getActivity())) {

            call.enqueue(new Callback<Posts>() {
                @Override
                public void onResponse(Call<Posts> call, Response<Posts> response) {
                    try {
                        SflShimmerFrameLayout.stopShimmer();
                        SflShimmerFrameLayout.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        postsFavouritesListFragmentSrRefreshPosts.setRefreshing(false);
                        postsFavouritesListFragmentTvNoResults.setVisibility(View.GONE);

                        if (response.body().getStatus() == 1) {
                            maxPage = response.body().getData().getLastPage();

                            if (response.body().getData().getTotal() != 0) {
                                postsData.addAll(response.body().getData().getData());
                                postsAndFavouritesAdapter.notifyDataSetChanged();

                            } else {
                                postsFavouritesListFragmentTvNoResults.setVisibility(View.VISIBLE);
                            }


                        } else {
                            setError(getString(R.string.error_list));
                        }

                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<Posts> call, Throwable t) {
                    try {
                        SflShimmerFrameLayout.stopShimmer();
                        SflShimmerFrameLayout.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        postsFavouritesListFragmentSrRefreshPosts.setRefreshing(false);
                        setError(getString(R.string.error_list));
                    } catch (Exception e) {

                    }

                }
            });

        } else {
            try {
                SflShimmerFrameLayout.onDetachedFromWindow();
                SflShimmerFrameLayout.stopShimmer();
                SflShimmerFrameLayout.setVisibility(View.GONE);
                loadMore.setVisibility(View.GONE);
                postsFavouritesListFragmentSrRefreshPosts.setRefreshing(false);
                setError(getString(R.string.error_inter_net));
            } catch (Exception e) {

            }

        }

    }


    private void reInit() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        postsData = new ArrayList<>();
        postsAndFavouritesAdapter = new PostsAndFavouritesAdapter(getActivity(), getActivity(), postsData, favourites);
        postsFavouritesListFragmentRvList.setAdapter(postsAndFavouritesAdapter);
    }

    private void setError(String errorTitleTxt) {
        View.OnClickListener action = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Filter) {
                    onFilter(1);
                } else {
                    getPosts(1);
                }

            }
        };
        postsFavouritesListFragmentTvNoResults.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.posts_favourites_list_Fragment_iv_search)
    public void onViewClicked() {
        disappearKeypad(getActivity(), getView());
        if (categoriesAdapter.selectedId == 0 && keyword == "" && Filter) {
            Filter = false;
            getPosts(1);
        } else {
            onFilter(1);
        }
    }

    @Override
    public void onBack() {
        if (!favourites) {
            getActivity().finish();
        } else {
            replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_cycle_activity_fl_home_frame
                    , new MoreFragment());
        }
    }

}
