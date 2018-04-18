package com.softthenext.gymmanagementsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softthenext.gymmanagementsystem.Model.FeesModel;
import com.softthenext.gymmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AABHALI on 04-01-2018.
 */

public class FeesAdapter extends RecyclerView.Adapter<FeesAdapter.FeesViewHolder> {

    Context context;
    List<FeesModel> feesModelList;
    public FeesAdapter(List<FeesModel> feesModelList)
    {
        this.feesModelList=feesModelList;
    }
    @Override
    public FeesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fees_list_item,parent,false);

        context = parent.getContext();
        return new FeesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeesViewHolder holder, int position) {
        holder.txtcustname.setText(feesModelList.get(position).getFeecustname());
        holder.txtfdate.setText(feesModelList.get(position).getFdate());
        holder.txtfamount.setText(feesModelList.get(position).getFamount());

    }

    @Override
    public int getItemCount() {
        return feesModelList.size();
    }

    public class FeesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView txtcustname,txtfdate,txtfamount;
        ImageView img_Whtsaap;


        public FeesViewHolder(View itemView) {
            super(itemView);

            txtcustname = itemView.findViewById(R.id.fees_custname);
            txtfdate = itemView.findViewById(R.id.fees_date);
            txtfamount = itemView.findViewById(R.id.fees_amount);

            img_Whtsaap = itemView.findViewById(R.id.img_whtsapp);
            img_Whtsaap.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.img_whtsapp:
                    launchWhatsApp();
                    break;
            }

        }

        public void launchWhatsApp()
        {
            List<String> arrayList = new ArrayList<String>();
            String CustName = txtcustname.getText().toString().trim();
            String text = "or Outstanding Fees ";
            String date = "Your Last Date To Pay Fees For The Gym is ";
            String outDate= txtfdate.getText().toString().trim();
            String amt = txtfamount.getText().toString().trim();
            String dear = "Dear Sir/Mam";
            arrayList.add(dear);
            arrayList.add(CustName);
            arrayList.add(date);
            arrayList.add(outDate);
            arrayList.add(text);
            arrayList.add(amt);
            Intent i = new Intent();
            i.setAction(android.content.Intent.ACTION_SEND);
            i.setType("text/html");
            i.putExtra(Intent.EXTRA_TEXT, String.valueOf(arrayList));
            /*i.putExtra(Intent.EXTRA_TEXT, txtcustname.getText().toString().trim());
            i.putExtra(Intent.EXTRA_STREAM, txtfdate.getText().toString().trim());
            i.putExtra(Intent.EXTRA_TEXT, txtfamount.getText().toString().trim());*/
            i.setPackage("com.whatsapp");
            context.startActivity(i);

        }
    }
}
