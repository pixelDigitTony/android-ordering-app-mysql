package com.example.ordersystem.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.ordersystem.Interface.ItemClickListener;
import com.example.ordersystem.Model.Bill;
import com.example.ordersystem.R;

import java.util.List;

class BillViewHolder extends RecyclerView.ViewHolder{

    public TextView txtBillName, txtBillPrice, txtBillTotal;
    public ImageView img_bill_count;




    public void setTxtBillName(TextView txtBillName) {
        this.txtBillName = txtBillName;
    }

    public BillViewHolder(@NonNull View itemView) {
        super(itemView);
        txtBillName = (TextView)itemView.findViewById(R.id.bill_item_name);
        txtBillPrice = (TextView)itemView.findViewById(R.id.bill_item_Price);
        txtBillTotal = (TextView)itemView.findViewById(R.id.bill_item_Total);
        img_bill_count = (ImageView)itemView.findViewById(R.id.bill_item_count);

    }

}

public class BillAdapter extends RecyclerView.Adapter<BillViewHolder> {

    private List<Bill> listBill;
    private Context context;
    public BillAdapter(Context context, List<Bill> listBill) {
        this.listBill = listBill;
        this.context = context;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.bill_layout,parent,false);
        return new BillViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill bill = (Bill) listBill.get(position);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+listBill.get(position).getBillQuantity(), Color.RED);
        holder.img_bill_count.setImageDrawable(drawable);

        Double price = (listBill.get(position).getBillPrice())*(Integer.parseInt(listBill.get(position).getBillQuantity()));
        holder.txtBillPrice.setText(listBill.get(position).getBillPrice().toString());
        holder.txtBillName.setText(listBill.get(position).getBillName());
        holder.txtBillTotal.setText(String.valueOf(price));
    }


    @Override
    public int getItemCount() {
        return listBill.size();
    }
}
