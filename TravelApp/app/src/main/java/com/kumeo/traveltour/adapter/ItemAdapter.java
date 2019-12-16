package com.kumeo.traveltour.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kumeo.traveltour.R;
import com.kumeo.traveltour.model.Tour;

import java.util.ArrayList;
import java.util.List;

import static com.kumeo.traveltour.extras.converter.createDate;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Tour> Items = new ArrayList<>();
    OnItemClickListener mItemClickListener;
    Context mContext;
    public ItemAdapter(Context context, ArrayList<Tour> tourArrayList) {
        this.mContext = context;
        this.Items = tourArrayList;
    }
    public ItemAdapter(Context context) {
        Items = new ArrayList<>();
        mContext = context;
    }

    public ItemAdapter(List<Tour> items) {
        this.Items = items;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mPlaceIv;;
        private final TextView mPriceTv;
        private final TextView mPeopleTv;
        private final TextView mLocationTv;
        private final TextView mTimeTv;
        //private final TextView mHiddenID;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mLocationTv =(TextView) itemView.findViewById(R.id.locationDescription);
            mTimeTv = (TextView)itemView.findViewById(R.id.dateDescription);
            mPeopleTv = (TextView)itemView.findViewById(R.id.peopleDescription);
            mPriceTv=(TextView)itemView.findViewById(R.id.moneyDescription);
//            mHiddenID = itemView.findViewById(R.id.hiddenID);
            mPlaceIv=(ImageView) itemView.findViewById(R.id.ImgPlace);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tour, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Tour tour = Items.get(position);

        holder.mPriceTv.setText(tour.getMinCost()+ " - "+ tour.getMaxCost());
        //holder.mDescTv.setText(modellist.get(i).getDesc());
        holder.mPeopleTv.setText(tour.getAdults()+"  adults"+ "           "+tour.getChilds() + " childs");
        holder.mLocationTv.setText(tour.getName()+ tour.getID() );
        holder.mTimeTv.setText(createDate(tour.getStartDate())+ "  -  "+ createDate(tour.getEndDate()));
        if(tour.getAvatar()!= null)
        {
            if(!tour.getAvatar().isEmpty())
            {
                Glide.with(mContext)
                        .load(tour.getAvatar())
                        .into(holder.mPlaceIv);
            }

        }
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClicklListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setItems(List<Tour> items){
        Items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<Tour> items){
        Items.addAll(items);
        notifyDataSetChanged();;
    }
}