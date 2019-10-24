package com.reda.yehia.bloodbankv2.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.reda.yehia.bloodbankv2.R;
import com.reda.yehia.bloodbankv2.data.api.ApiService;
import com.reda.yehia.bloodbankv2.data.model.client.ClientData;
import com.reda.yehia.bloodbankv2.data.model.donation.donationRequests.DonationRequests;
import com.reda.yehia.bloodbankv2.data.model.post.postToggleFavourite.PostToggleFavourite;
import com.reda.yehia.bloodbankv2.data.model.post.posts.Posts;
import com.reda.yehia.bloodbankv2.data.model.post.posts.PostsData;
import com.reda.yehia.bloodbankv2.utils.HelperMethod;
import com.reda.yehia.bloodbankv2.view.activity.HomeCycleActivity;
import com.reda.yehia.bloodbankv2.view.fragment.homeCycle.post.PostDetailsFragment;
import com.reda.yehia.mirtoast.ToastCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.reda.yehia.bloodbankv2.data.api.RetrofitClient.getClient;
import static com.reda.yehia.bloodbankv2.data.local.SharedPreferencesManger.loadUserData;
import static com.reda.yehia.bloodbankv2.utils.HelperMethod.onLoadImageFromUrl;

public class PostsAndFavouritesAdapter extends RecyclerView.Adapter<PostsAndFavouritesAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<PostsData> postsDataList = new ArrayList<>();
    private boolean favourites;
    private ClientData clientData;
    private ApiService apiService;

    public PostsAndFavouritesAdapter(Context context,
                                     Activity activity,
                                     List<PostsData> postsDataList,
                                     boolean favourites) {
        this.context = context;
        this.activity = activity;
        this.postsDataList = postsDataList;
        this.favourites = favourites;
        clientData = loadUserData(activity);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_posts,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        try {

            onLoadImageFromUrl(holder.postsItemIvPostPhoto, postsDataList.get(position).getThumbnailFullPath(), context);
            holder.postsItemTvTitle.setText(postsDataList.get(position).getTitle());
            if (postsDataList.get(position).getIsFavourite()) {
                holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_heart_solid);
            } else {
                holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_heart_regular);
            }

        } catch (Exception e) {

        }

    }

    private void setAction(final ViewHolder holder, final int position) {
        holder.postsItemIvIsFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavourite(holder, position);
            }
        });


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeCycleActivity navigationActivity = (HomeCycleActivity) activity;
                PostDetailsFragment postDetails = new PostDetailsFragment();
                postDetails.postsData = postsDataList.get(position);
                HelperMethod.replaceFragment(navigationActivity.getSupportFragmentManager(), R.id.home_cycle_activity_fl_home_frame, postDetails);

            }
        });

    }
    private void toggleFavourite(final ViewHolder holder, final int position) {
        final PostsData postsData = postsDataList.get(position);

        postsDataList.get(position).setIsFavourite(!postsDataList.get(position).getIsFavourite());

        if (postsDataList.get(position).getIsFavourite()) {
            holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_heart_solid);
            ToastCreator.onCreateSuccessToast(activity,context.getResources().getString(R.string.add_to_favourite));


        } else {
            holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_heart_regular);

            ToastCreator.onCreateSuccessToast(activity,context.getResources().getString(R.string.remove_from_favourite));
            if (favourites) {
                postsDataList.remove(position);
                notifyDataSetChanged();
//                if (postsDataList.size() == 0) {
//                    articlesFragmentTvNoItems.setVisibility(View.VISIBLE);
//                }
            }
        }
        Call<PostToggleFavourite> call = getClient().getPostToggleFavourite(postsData.getId(), clientData.getApiToken());
        call.enqueue(new Callback<PostToggleFavourite>() {
            @Override
            public void onResponse(Call<PostToggleFavourite> call, Response<PostToggleFavourite> response) {
                try {
                    if (response.body().getStatus() == 1) {

                    } else {
                        postsDataList.get(position).setIsFavourite(!postsDataList.get(position).getIsFavourite());
                        if (postsDataList.get(position).getIsFavourite()) {
                            holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_heart_solid);
                            if (favourites) {
                                postsDataList.add(position, postsData);
                                notifyDataSetChanged();
                            }
                        } else {
                            holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_heart_regular);
                        }
                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<PostToggleFavourite> call, Throwable t) {
                try {
                    postsDataList.get(position).setIsFavourite(!postsDataList.get(position).getIsFavourite());
                    if (postsDataList.get(position).getIsFavourite()) {
                        holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_heart_solid);
                        if (favourites) {
                            postsDataList.add(position, postsData);
                            notifyDataSetChanged();
                        }
                    } else {
                        holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_heart_regular);
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.posts_item_iv_post_photo)
        PorterShapeImageView postsItemIvPostPhoto;
        @BindView(R.id.posts_item_tv_title)
        TextView postsItemTvTitle;
        @BindView(R.id.posts_item_iv_is_favourite)
        ImageView postsItemIvIsFavourite;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
