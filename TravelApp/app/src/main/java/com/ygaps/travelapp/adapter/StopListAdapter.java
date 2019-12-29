package com.ygaps.travelapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.vipulasri.timelineview.TimelineView;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.StopPoint;
import com.ygaps.travelapp.extras.ReadExcel;
import com.ygaps.travelapp.model.ServiceType;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.view.Activity.SplashActivity;
import com.ygaps.travelapp.view.Fragment.StopPointFragment;

import java.util.ArrayList;

import static com.ygaps.travelapp.extras.converter.createDate;

import java.util.List;
import java.util.Locale;

import static com.ygaps.travelapp.extras.converter.createDate;

public class StopListAdapter extends RecyclerView.Adapter<StopListAdapter.TimelineViewHolder> {
    private static final int VIEW_TYPE_HEADER = 100;
    private static final String TAG = StopListAdapter.class.getSimpleName();

    private Context mContext;
    List<StopPoint> modellist;
    private final ArrayList<StopPoint> mStopPoint;
    private static ClickListener mClickListener;
    public StopListAdapter(Context context, List<StopPoint> StopPoint) {
        this.mContext = context;
        modellist =StopPoint;
        mStopPoint = new ArrayList<StopPoint>(modellist.size());
        for (StopPoint a: StopPoint
             ) {
            mStopPoint.add(a);
        }

    }
    @NonNull
    @Override
    public StopListAdapter.TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_stop, parent, false);
        return new TimelineViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull StopListAdapter.TimelineViewHolder holder, int position) {
        StopPoint stop = modellist.get(position);
        holder.mstop=stop;
        holder.cityname.setText(stop.getName());
        //holder.mDescTv.setText(modellist.get(i).getDesc());

        //serviceType Name;
        if(stop.getServiceTypeId()>0) {
            List<ServiceType> list = ReadExcel.readServiceTypeExcelFile(mContext);
            holder.serviceName.setText(list.get(stop.getServiceTypeId() - 1).getService_name());
        }

        holder.date.setText(createDate(stop.getArrivalAt())+ "  -  "+ createDate(stop.getLeaveAt()));
        if(stop.getAvatar()!= null)
        {
            if(!stop.getAvatar().isEmpty())
            {
                Glide.with(mContext)
                        .load(stop.getAvatar())
                        .into(holder.city);
            }

        }
    }

    //filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        Log.d(TAG,"1"+modellist.toString());
        modellist.clear();
        Log.d(TAG,"2:"+mStopPoint.size()+mStopPoint.toString());
        if (charText.length()==0){
            modellist.addAll(mStopPoint);
            Log.d(TAG, "2"+modellist.toString());
        }
        else {
            for (StopPoint model : mStopPoint){
                Log.d(TAG,"3"+model.getName());
                if (model.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)){

                    modellist.add(model);
                }

            }
            Log.d(TAG,modellist.toString());
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return modellist.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) { StopListAdapter.mClickListener = clickListener;
    }



    public interface ClickListener {
        void onItemClick(StopPoint stopPoint);
    }

    public class TimelineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        StopPoint mstop;
        ImageView city;
        TextView cityname;
        TextView date;
        TimelineView timelineView;
        TextView serviceName;

        public TimelineViewHolder(View view) {
            super(view);

            city= (ImageView)view.findViewById(R.id.profile_image);
            cityname= (TextView)view.findViewById(R.id.tv);
            date= (TextView)view.findViewById(R.id.date);
            timelineView= (TimelineView)view.findViewById(R.id.timeline_view);
            timelineView.initLine(11);
            serviceName=(TextView)view.findViewById(R.id.serviceName);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(mstop);
        }
    }
}