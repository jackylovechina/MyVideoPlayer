package com.my.myvideoplayer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.my.myvideoplayer.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/8 0008.
 */

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public ItemAdapter() {
    }

    public ItemAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.item_videolist, null);
        TextView mTV_itemDir = (TextView) view.findViewById(R.id.tv_videoItem_dir);

        mTV_itemDir.setText(list.get(i));


        return view;
    }
}
