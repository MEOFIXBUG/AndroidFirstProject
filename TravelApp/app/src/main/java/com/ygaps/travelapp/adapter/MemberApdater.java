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
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.extras.CircleImageView;
import com.ygaps.travelapp.model.StopPoint;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.model.UserInfo;

import java.util.ArrayList;

import static com.ygaps.travelapp.extras.converter.createDate;

public class MemberApdater extends RecyclerView.Adapter<MemberApdater.ViewHolder>{
    private Context mContext;
    private ArrayList<UserInfo> mMembers;

    public MemberApdater(Context context, ArrayList<UserInfo> param)
    {
        this.mContext=context;
        this.mMembers=param;
    }
    @NonNull
    @Override
    // set item view trong list
    public MemberApdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_member,viewGroup,false);
        return new MemberApdater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        UserInfo member=mMembers.get(position);
        viewHolder.mNameTv.setText(member.getName());
        //holder.mDescTv.setText(modellist.get(i).getDesc());
        viewHolder.mPhoneTv.setText(member.getPhone());
        if(member.getIsHost())
        {
            viewHolder.mHostIv.setVisibility(View.VISIBLE);
        }


//        if(member.getAvatar()!= null)
//        {
//
//            if(!member.getAvatar().isEmpty())
//            {
//
//                Glide.with(mContext)
//                        .load(member.getAvatar())
//                        .into(viewHolder.mAvatarIv);
//            }
//
//        }

    }

    @Override
    public int getItemCount() {
        return mMembers.size();
    }

    // class ViewHOlder la item_list_tour
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView mAvatarIv;
        private final TextView mNameTv;
        private final TextView mPhoneTv;
        private final ImageView mHostIv;
        //private final TextView mHiddenID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameTv =(TextView) itemView.findViewById(R.id.tvUserName);
            mPhoneTv = (TextView)itemView.findViewById(R.id.tvPhone);

            mHostIv=(ImageView)itemView.findViewById(R.id.host);
//            mHiddenID = itemView.findViewById(R.id.hiddenID);
            mAvatarIv=(CircleImageView) itemView.findViewById(R.id.profile_image);
        }
    }
}
