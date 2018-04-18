package com.softthenext.gymmanagementsystem.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softthenext.gymmanagementsystem.Model.Customers;
import com.softthenext.gymmanagementsystem.R;

import java.util.List;

/**
 * Created by AABHALI on 01-01-2018.
 */

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHolder>{
    List<Customers> customersList;
    public MemberListAdapter( List<Customers> customersList)
    {
        this.customersList=customersList;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.memberlist_item,parent,false);

        return new MemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        holder.txtmembername.setText(customersList.get(position).getCustname());

    }

    @Override
    public int getItemCount() {
        return customersList.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtmembername;

        public MemberViewHolder(View itemView) {
            super(itemView);

            txtmembername = itemView.findViewById(R.id.txt_mem_name);
        }
    }

}
