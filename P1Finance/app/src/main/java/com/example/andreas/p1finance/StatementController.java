package com.example.andreas.p1finance;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Andreas on 2016-09-17.
 */
public class StatementController {
    private StatementActivity activity;
    private Value[] values;
    private Parcelable[] parcelables;

    public StatementController(StatementActivity activity, Intent intent) {
        this.activity = activity;

        parcelables = intent.getParcelableArrayExtra(MainController.VALUES);
        values = new Value[parcelables.length];
        for(int i = 0; i< values.length; i++) {
            values[i] = (Value) parcelables[i];
        }
        activity.setAdapter(new ListAdapter(activity, values));
    }

    private class ListAdapter extends ArrayAdapter<Value> {
        private LayoutInflater inflater;

        public ListAdapter(Context context, Value[] values) {
            super(context,R.layout.value_row, values);
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Value value = getItem(position);
            ViewHolder holder;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.value_row,parent,false);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
                holder.tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);
                holder.tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tvTitle.setText(value.getTitle());
            holder.tvDate.setText(value.getDate());
            holder.tvCategory.setText(value.getCategory());
            holder.tvAmount.setText(value.getAmount());
            return convertView;
        }
    }

    private class ViewHolder {
        private TextView tvTitle;
        private TextView tvDate;
        private TextView tvCategory;
        private TextView tvAmount;
    }
}

