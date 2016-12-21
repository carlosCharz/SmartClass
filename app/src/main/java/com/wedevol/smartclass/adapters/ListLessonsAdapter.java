package com.wedevol.smartclass.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wedevol.smartclass.R;
import com.wedevol.smartclass.models.Lesson;
import com.wedevol.smartclass.utils.interfaces.Constants;

import java.util.List;

/** Created by paolo on 12/20/16.*/
public class ListLessonsAdapter extends RecyclerView.Adapter<ListLessonsAdapter.ViewHolder> {
    private final List<Lesson> mItems;
    private final Activity context;
    private final boolean type;

    public ListLessonsAdapter(Activity context, List<Lesson> list, boolean type) {
        super();
        this.context = context;
        mItems = list;
        this.type = type;
    }

    @Override
    public ListLessonsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_student_request, viewGroup, false);
        return new ListLessonsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ListLessonsAdapter.ViewHolder viewHolder, int i) {
        final Lesson classy = mItems.get(i);
        viewHolder.tv_request_counsult_data.setText("Richard - IMU - S/.20");
        viewHolder.tv_request_date_time.setText("12/12/16 - 20 a 24 horas");
        viewHolder.tv_counselor_rating.setText("4.7");

        if(type == Constants.REQUEST_TYPE){
            viewHolder.b_cancel.setVisibility(View.VISIBLE);
            viewHolder.b_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Call web service to cancel it
                }
            });

        }else{
            viewHolder.b_cancel.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        final TextView tv_request_date_time;
        final TextView tv_request_counsult_data;
        final TextView tv_counselor_rating;
        final Button b_cancel;

        ViewHolder(View itemView) {
            super(itemView);
            tv_request_date_time = (TextView) itemView.findViewById(R.id.tv_request_date_time);
            tv_request_counsult_data = (TextView) itemView.findViewById(R.id.tv_request_counsult_data);
            tv_counselor_rating = (TextView) itemView.findViewById(R.id.tv_counselor_rating);
            b_cancel = (Button) itemView.findViewById(R.id.b_cancel);
        }
    }
}