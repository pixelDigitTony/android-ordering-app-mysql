package com.example.ordersystem.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ordersystem.Model.Dish;
import com.example.ordersystem.R;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private Context ctx;
    private List<Dish> dishList;
    ItemClickListener itemClickListener;

    public DishAdapter(Context ctx, List<Dish> dishList, ItemClickListener itemClickListener){
        this.ctx = ctx;
        this.dishList = dishList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public DishAdapter.DishViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.dish_item, null);
        return  new DishViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DishAdapter.DishViewHolder dishViewHolder, int position) {
        Dish dish = (Dish) dishList.get(position);

        dishViewHolder.textView.setText(dish.getDishName());
        dishViewHolder.imageView.setImageDrawable(ctx.getResources().getDrawable(dish.getDishImage(), null));
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    class DishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;
        RelativeLayout relativeLayout;
        ItemClickListener itemClickListener;

        public DishViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.dish_image);
            textView = (TextView) itemView.findViewById(R.id.dish_name);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.dish_relative);
            this.itemClickListener = itemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getAdapterPosition());
        }
    }
    public interface ItemClickListener {
        void onClick(int position);
    }
}
