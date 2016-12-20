package com.wedevol.smartclass.adapters;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wedevol.smartclass.R;
import com.wedevol.smartclass.utils.dialogs.ChangePriceDialogFragment;

import java.util.List;

/** Created by paolo on 12/15/16.*/
public class ListCounselorCourseStateAdapter  extends RecyclerView.Adapter{
    private final List<Pair<String,String>> mItems;
    private final Activity context;
    private final String headerName;
    private final String headerExplanation;
    private final boolean showHourlyRate;

    public ListCounselorCourseStateAdapter(Activity context, List<Pair<String,String>> list, String headerName, String headerExplanation, boolean showHourlyRate) {
        super();
        this.context = context;
        mItems = list;
        this.headerName = headerName;
        this.headerExplanation = headerExplanation;
        this.showHourlyRate = showHourlyRate;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v;
        if(i==0){
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_course_state_header, viewGroup, false);
            return new ListCounselorCourseStateAdapter.HeadViewHolder(v);
        }else{
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_course_state_item, viewGroup, false);
            return new ListCounselorCourseStateAdapter.ItemViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position==0?0:1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if(getItemViewType(i)==0) {
            ((ListCounselorCourseStateAdapter.HeadViewHolder)viewHolder).tv_header_name.setText(headerName);
            ((ListCounselorCourseStateAdapter.HeadViewHolder)viewHolder).tv_header_explanation.setText(headerExplanation);
        } else {
            final Pair<String,String> courseAndRate = mItems.get(i-1);
            ((ListCounselorCourseStateAdapter.ItemViewHolder)viewHolder).tv_course_name.setText(courseAndRate.first);
            if(showHourlyRate){
                ((ListCounselorCourseStateAdapter.ItemViewHolder)viewHolder).tv_course_hourly_rate.setVisibility(View.VISIBLE);
                ((ListCounselorCourseStateAdapter.ItemViewHolder)viewHolder).tv_course_hourly_rate.setText(courseAndRate.second);
            }else{
                ((ListCounselorCourseStateAdapter.ItemViewHolder)viewHolder).tv_course_hourly_rate.setVisibility(View.GONE);
            }

            ((ListCounselorCourseStateAdapter.ItemViewHolder)viewHolder).rl_course_holder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChangePriceDialogFragment suggestCourseDialogFragment = ChangePriceDialogFragment.newInstance(R.layout.dialog_suggest_price);
                    suggestCourseDialogFragment.show(((FragmentActivity)context).getSupportFragmentManager(), "Cambiar Precio");

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size()+1;
    }

    private class HeadViewHolder extends RecyclerView.ViewHolder{
        final TextView tv_header_name;
        final TextView tv_header_explanation;

        HeadViewHolder(View itemView) {
            super(itemView);
            tv_header_name = (TextView) itemView.findViewById(R.id.tv_header_name);
            tv_header_explanation = (TextView) itemView.findViewById(R.id.tv_header_explanation);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder{
        final TextView tv_course_name;
        final TextView tv_course_hourly_rate;
        final RelativeLayout rl_course_holder;

        ItemViewHolder(View itemView) {
            super(itemView);
            tv_course_name = (TextView) itemView.findViewById(R.id.tv_course_name);
            tv_course_hourly_rate = (TextView) itemView.findViewById(R.id.tv_course_hourly_rate);
            rl_course_holder = (RelativeLayout) itemView.findViewById(R.id.rl_course_holder);
        }
    }
}