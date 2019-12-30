package com.ygaps.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.response.TourResponse;

import java.util.ArrayList;
import java.util.List;

public class NotifyAdapter  extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Tour> mTourList;

    public NotifyAdapter(Context context, ArrayList<Tour> param)
    {
        this.mContext=context;
        this.mTourList=param;
    }
    @NonNull
    @Override
    // set item view trong list
    public NotifyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification,viewGroup,false);
        return new NotifyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Tour tour=mTourList.get(position);
        viewHolder.mNameTv.setText(tour.getName());
        //holder.mDescTv.setText(modellist.get(i).getDesc());
        viewHolder.mHiddenTv.setText(Long.toString(tour.getID()));
        viewHolder.mMessageTv.setText("đừng đi chuyến này haha");
        if(tour.getAvatar()!= null)
        {

            if(!tour.getAvatar().isEmpty())
            {

                Glide.with(mContext)
                        .load(tour.getAvatar())
                        .into(viewHolder.mAvatarIv);
            }

        }

    }

    @Override
    public int getItemCount() {
        return mTourList.size();
    }
    public void addItems(List<Tour> items){
        mTourList.addAll(items);
        notifyDataSetChanged();;
    }
    // class ViewHOlder la item_list_tour
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mAvatarIv;
        private final TextView mNameTv;
        private final TextView mMessageTv;
        private final TextView mHiddenTv;
        //private final TextView mHiddenID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameTv =(TextView) itemView.findViewById(R.id.tourName);
            mAvatarIv = (ImageView) itemView.findViewById(R.id.imgTour);

            mMessageTv=(TextView) itemView.findViewById(R.id.message);
//            mHiddenID = itemView.findViewById(R.id.hiddenID);
            mHiddenTv=(TextView) itemView.findViewById(R.id.hiddenID);
        }
    }
}
