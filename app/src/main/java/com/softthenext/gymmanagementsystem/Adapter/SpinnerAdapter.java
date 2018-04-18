package com.softthenext.gymmanagementsystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softthenext.gymmanagementsystem.Model.PackageModel;
import com.softthenext.gymmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AABHALI on 28-12-2017.
 */

public class SpinnerAdapter extends BaseAdapter{

    private LayoutInflater layoutInflater;
    private List<PackageModel> listData = new ArrayList<>();
    private Context context;
    public SpinnerAdapter(Context context, List<PackageModel> listData) {
        this.context = context;
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listData = listData;
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return (PackageModel)listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder spinnerHolder;
        if(convertView == null){
            spinnerHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.spinner_list, parent, false);
            spinnerHolder.spinnerItemList = (TextView)convertView.findViewById(R.id.spinner_list_item);
            convertView.setTag(spinnerHolder);
        }else{
            spinnerHolder = (ViewHolder)convertView.getTag();
        }
        spinnerHolder.spinnerItemList.setText(listData.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        TextView spinnerItemList;
    }
}
