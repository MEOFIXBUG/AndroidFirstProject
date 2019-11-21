package com.kumeo.traveltour.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kumeo.traveltour.R;
import com.kumeo.traveltour.model.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TourAdapter extends BaseAdapter {

    //variables
    Context mContext;
    LayoutInflater inflater;
    List<Tour> modellist;
    ArrayList<Tour> arrayList;

    //constructor
    public TourAdapter(Context context, List<Tour> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<Tour>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder{
        TextView mPriceTv, mPeopleTv,mLocationTv,mTimeTv,mHiddenID;
        ImageView mPlaceIv;
    }


    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int postition, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_list_tour, null);

            //locate the views in content_main.xml
            holder.mLocationTv = view.findViewById(R.id.locationDescription);
            holder.mTimeTv = view.findViewById(R.id.dateDescription);
            holder.mPeopleTv = view.findViewById(R.id.peopleDescription);
            holder.mPriceTv=view.findViewById(R.id.moneyDescription);
//            holder.mHiddenID = view.findViewById(R.id.hiddenID);
            holder.mPlaceIv=view.findViewById(R.id.ImgPlace);
            view.setTag(holder);

        }
        else {
            holder = (ViewHolder)view.getTag();
        }
        //set the results into textviews
        final int i=getCount()-1-postition;
        holder.mPriceTv.setText(modellist.get(i).getMinCost()+ " - "+ modellist.get(i).getMaxCost());
        //holder.mDescTv.setText(modellist.get(i).getDesc());
        holder.mPeopleTv.setText(modellist.get(i).getAdults()+"  adults"+ "           "+modellist.get(i).getChilds() + " childs");
        holder.mLocationTv.setText(modellist.get(i).getName());
        holder.mTimeTv.setText(modellist.get(i).getStartDate()+ " - "+ modellist.get(i).getEndDate());
        //set the result in imageview
        Glide.with(mContext)
                .load(modellist.get(i).getAvatar())
                .into(holder.mPlaceIv);
        //listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code later


            }
        });

        return view;
    }
    //filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        modellist.clear();
        if (charText.length()==0){
            modellist.addAll(arrayList);
        }
        else {
            for (Tour model : arrayList){
                if (model.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }
}
