package com.hankkin.mycartdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.hankkin.mycartdemo.chatview.HorzinonlChartView;
import java.util.List;

/**
 * author zaaach on 2016/1/26.
 */
public class ChartAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<MyBean> mCities;

    public ChartAdapter(Context mContext, List<MyBean> mCities) {
        this.mContext = mContext;
        this.mCities = mCities;
        this.inflater = LayoutInflater.from(mContext);
    }




    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public MyBean getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.lv_item, parent, false);
            holder = new CityViewHolder();
            holder.number = (TextView) view.findViewById(R.id.tv_number);
            holder.name = (TextView) view.findViewById(R.id.tv_name);
            holder.en = (HorzinonlChartView) view.findViewById(R.id.hor);
            view.setTag(holder);
        } else {
            holder = (CityViewHolder) view.getTag();
        }
        final MyBean city = mCities.get(position);
        holder.name.setText(city.getName());
        holder.en.setNumber(city.getNumber());
        holder.number.setText(city.getNumber()+"");
        return view;
    }

    public static class CityViewHolder {
        TextView number;
        TextView name;
        HorzinonlChartView en;
    }
}
