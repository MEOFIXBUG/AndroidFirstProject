package com.ygaps.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.Tour;

import java.util.ArrayList;

import static com.ygaps.travelapp.extras.converter.createDate;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    private Context context;
    ArrayList<Tour> tourArrayList;
    ItemAdapter.OnItemClickListener mItemClickListener;
    //constructor
    public TourAdapter(Context context, ArrayList<Tour> articleArrayList) {
        this.context = context;
        this.tourArrayList = articleArrayList;
    }
    @NonNull
    @Override
    // set item view trong list
    public TourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_tour,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    //set gia tri cho tung itemview trog list voi gia tri thu i trong list tour tuong ung
    public void onBindViewHolder(@NonNull TourAdapter.ViewHolder viewHolder, int i) {
        Tour tour=tourArrayList.get(i);
        viewHolder.mPriceTv.setText(tour.getMinCost()+ " - "+ tour.getMaxCost() + "id: "+ tour.getID());
        //holder.mDescTv.setText(modellist.get(i).getDesc());
        viewHolder.mPeopleTv.setText(tour.getAdults()+"  adults"+ "           "+tour.getChilds() + " childs");
        viewHolder.mLocationTv.setText(tour.getName());
        viewHolder.mTimeTv.setText(createDate(tour.getStartDate())+ "  -  "+ createDate(tour.getEndDate()));
        if(tour.getAvatar()!= null)
        {
            if(!tour.getAvatar().isEmpty())
            {
                Glide.with(context)
                        .load(tour.getAvatar())
                        .into(viewHolder.mPlaceIv);
            }

        }

    }
    public void setOnItemClicklListener(final ItemAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    @Override
    public int getItemCount() {
        return tourArrayList.size();
    }

    // class ViewHOlder la item_list_tour
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mPlaceIv;;
        private final TextView mPriceTv;
        private final TextView mPeopleTv;
        private final TextView mLocationTv;
        private final TextView mTimeTv;
        //private final TextView mHiddenID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mLocationTv =(TextView) itemView.findViewById(R.id.locationDescription);
            mTimeTv = (TextView)itemView.findViewById(R.id.dateDescription);
            mPeopleTv = (TextView)itemView.findViewById(R.id.peopleDescription);
            mPriceTv=(TextView)itemView.findViewById(R.id.moneyDescription);
//            mHiddenID = itemView.findViewById(R.id.hiddenID);
            mPlaceIv=(ImageView) itemView.findViewById(R.id.ImgPlace);
        }
    }
}
