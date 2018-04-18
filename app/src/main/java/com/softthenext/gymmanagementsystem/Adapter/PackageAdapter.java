package com.softthenext.gymmanagementsystem.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softthenext.gymmanagementsystem.Model.PackgModel;
import com.softthenext.gymmanagementsystem.R;

import java.util.List;

/**
 * Created by AABHALI on 04-01-2018.
 */

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {

    List<PackgModel> packgModelList;
    public PackageAdapter( List<PackgModel> packgModelList)
    {
        this.packgModelList=packgModelList;
    }
    @Override
    public PackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_list_item,parent,false);
       return new PackageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PackageViewHolder holder, int position) {
        holder.txtpid.setText(packgModelList.get(position).getPid());
        holder.txtpname.setText(packgModelList.get(position).getPname());
        holder.txtpamount.setText(packgModelList.get(position).getPamount());
        holder.txtplaunchdate.setText(packgModelList.get(position).getPlaunchdate());
        holder.txtpduration.setText(packgModelList.get(position).getPduration());

    }

    @Override
    public int getItemCount() {
        return packgModelList.size();
    }

    public class PackageViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtpid,txtpname,txtpduration,txtplaunchdate,txtpamount;
        public PackageViewHolder(View v)
        {
            super(v);
            txtpid = v.findViewById(R.id.package_id);
            txtpname = v.findViewById(R.id.package_name);
            txtpamount = v.findViewById(R.id.package_amount);
            txtpduration = v.findViewById(R.id.package_duration);
            txtplaunchdate = v.findViewById(R.id.package_launchdate);
        }

    }
}
