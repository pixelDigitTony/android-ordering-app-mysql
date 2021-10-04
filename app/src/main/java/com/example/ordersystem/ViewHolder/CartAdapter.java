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
import com.example.ordersystem.Model.Order;
import com.example.ordersystem.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtCartName, txtPrice;
    public ImageView img_cart_count;

    private ItemClickListener itemClickListener;


    public void setTxtCartName(TextView txtCartName) {
        this.txtCartName = txtCartName;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtCartName = (TextView)itemView.findViewById(R.id.cart_item_name);
        txtPrice = (TextView)itemView.findViewById(R.id.cart_item_Price);
        img_cart_count = (ImageView)itemView.findViewById(R.id.cart_item_count);
    }

    @Override
    public void onClick(View v) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData;
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+listData.get(position).getFoodQuantity(), Color.RED);
        holder.img_cart_count.setImageDrawable(drawable);
        try {
            double price = (listData.get(position).getFoodPrice()) * (Double.parseDouble(listData.get(position).getFoodQuantity()));
            holder.txtPrice.setText(String.valueOf(price));
        }catch (NumberFormatException e){

        }
        holder.txtCartName.setText(listData.get(position).getFoodName());
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }
}
