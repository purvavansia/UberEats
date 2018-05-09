package com.example.purva.ubereats.data.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import com.example.purva.ubereats.R;
import com.example.purva.ubereats.data.model.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    List<Cart.CartBean> cartList;

    public CartAdapter(Context context, List<Cart.CartBean> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartAdapter.MyViewHolder mVH;

        View v = LayoutInflater.from(context).inflate(R.layout.checkout_cart_item, parent, false);

        mVH = new CartAdapter.MyViewHolder(v);
        return mVH;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.foodName.setText(cartList.get(position).getFoodName());
        holder.foodQuantity.setText(cartList.get(position).getQuantity());
        holder.foodPrice.setText("$" + cartList.get(position).getFoodPrice());
        Picasso.with(context).load(cartList.get(position).getImage()).into(holder.foodimage);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView foodimage;
        TextView foodName;
        TextView foodPrice,foodQuantity;


        public MyViewHolder(View itemView) {
            super(itemView);
            foodimage = itemView.findViewById(R.id.foodImageCart);
            foodName = itemView.findViewById(R.id.foodNameCart);
            foodPrice = itemView.findViewById(R.id.foodPriceCart);
            foodQuantity = itemView.findViewById(R.id.foodQuantityCart);

        }
    }
}
