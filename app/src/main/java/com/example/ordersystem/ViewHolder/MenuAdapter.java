package com.example.ordersystem.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordersystem.Home;
import com.example.ordersystem.Model.Category;
import com.example.ordersystem.R;
import com.example.ordersystem.orderDish;
import com.example.ordersystem.orderMenu;

import java.util.List;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

   private Context mCtx;
   private List<Category> categoryList;
   private  ItemClickListener itemClickListener;


   public MenuAdapter(Context mCtx, List<Category> categoryList, ItemClickListener itemClickListener) {
      this.mCtx = mCtx;
      this.categoryList = categoryList;
      this.itemClickListener = itemClickListener;
   }

   @NonNull
   @Override
   public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      LayoutInflater inflater = LayoutInflater.from(mCtx);
      View view = inflater.inflate(R.layout.menu_item, null);
      return  new MenuViewHolder(view, itemClickListener);

   }

   @Override
   public void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, final int position) {
      Category category = (Category) categoryList.get(position);

      menuViewHolder.textView.setText(category.getName());
      menuViewHolder.imageView.setImageDrawable(mCtx.getResources().getDrawable(category.getImage(), null));

   }

   @Override
   public int getItemCount() {
      return categoryList.size();
   }

   class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

      ImageView imageView;
      TextView textView;
      RelativeLayout relativeLayout;
      ItemClickListener itemClickListener;


      public MenuViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
         super(itemView);

         imageView = (ImageView) itemView.findViewById(R.id.menu_image);
         textView = (TextView) itemView.findViewById(R.id.menu_name);
         relativeLayout = (RelativeLayout) itemView.findViewById(R.id.menu_relative);
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
