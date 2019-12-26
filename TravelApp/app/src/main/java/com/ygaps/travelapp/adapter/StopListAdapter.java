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
import com.github.vipulasri.timelineview.TimelineView;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.StopPoint;

import java.util.ArrayList;

import static com.ygaps.travelapp.extras.converter.createDate;

public class StopListAdapter extends RecyclerView.Adapter<StopListAdapter.TimelineViewHolder> {
    private static final int VIEW_TYPE_HEADER = 100;

    private Context mContext;
    private ArrayList<StopPoint> mStopPoint;
    private static ClickListener mClickListener;
    public StopListAdapter(Context context, ArrayList<StopPoint> StopPoint) {
        this.mContext = context;
        this.mStopPoint = StopPoint;
    }
    @NonNull
    @Override
    public StopListAdapter.TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_stop, parent, false);
        return new TimelineViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull StopListAdapter.TimelineViewHolder holder, int position) {
        StopPoint stop = mStopPoint.get(position);
        holder.mstop=stop;
        holder.cityname.setText(stop.getName());
        //holder.mDescTv.setText(modellist.get(i).getDesc());

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


    @Override
    public int getItemCount() {
        return mStopPoint.size();
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

        public TimelineViewHolder(View view) {
            super(view);

            city= (ImageView)view.findViewById(R.id.profile_image);
            cityname= (TextView)view.findViewById(R.id.tv);
            date= (TextView)view.findViewById(R.id.date);
            timelineView= (TimelineView)view.findViewById(R.id.timeline_view);
            timelineView.initLine(11);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(mstop);
        }
    }
}