package com.ygaps.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.vipulasri.timelineview.TimelineView;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.ReviewOfUser;
import com.ygaps.travelapp.extras.ReadExcel;
import com.ygaps.travelapp.model.ServiceType;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.view.Activity.SplashActivity;

import java.util.ArrayList;

import static com.ygaps.travelapp.extras.converter.createDate;

import java.util.List;

import static com.ygaps.travelapp.extras.converter.createDate;

public class ReviewTourAdapter extends RecyclerView.Adapter<ReviewTourAdapter.ItemViewHolder> {
    private static final int VIEW_TYPE_HEADER = 100;

    private Context mContext;
    private ArrayList<ReviewOfUser> mListReviewOfUser;
    OnItemClickListener mItemClickListener;
    private static ClickListener mClickListener;
    public ReviewTourAdapter(Context context, ArrayList<ReviewOfUser> mListReviewOfUser) {
        this.mContext = context;
        this.mListReviewOfUser = mListReviewOfUser;
    }
    @NonNull
    @Override
    public ReviewTourAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_review, parent, false);
        return new ItemViewHolder(view);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ReviewOfUser review;

        TextView tvUserName;
        RatingBar simpleRatingBar;
        TextView tvDate;
        TextView tvReview;

        public ItemViewHolder(View view) {
            super(view);

            tvUserName= (TextView)view.findViewById(R.id.tvUserName);
            simpleRatingBar=(RatingBar)view.findViewById(R.id.simpleRatingBar);
            tvDate= (TextView)view.findViewById(R.id.tvDate);
            tvReview=(TextView)view.findViewById(R.id.tvReview);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(review);
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(ReviewOfUser review);
    }



    @Override
    public void onBindViewHolder(@NonNull ReviewTourAdapter.ItemViewHolder holder, int position) {
        ReviewOfUser reviewAtIndex = mListReviewOfUser.get(position);
        holder.review=reviewAtIndex;

        if(reviewAtIndex.getName()==null){
            holder.tvUserName.setText("Unknown User");
        }
        else
        holder.tvUserName.setText(reviewAtIndex.getName());
        holder.tvDate.setText(createDate(reviewAtIndex.getCreatedOn()));
        holder.simpleRatingBar.setRating((float)reviewAtIndex.getPoint());
        holder.tvReview.setText(reviewAtIndex.getReview());
    }


    @Override
    public int getItemCount() {
        return mListReviewOfUser.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) { ReviewTourAdapter.mClickListener = clickListener;
    }



    public interface ClickListener {
        void onItemClick(ReviewOfUser ReviewOfUser);
    }


}