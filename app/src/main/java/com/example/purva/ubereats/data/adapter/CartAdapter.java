package com.example.purva.ubereats.data.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import com.example.purva.ubereats.R;
import com.example.purva.ubereats.data.database.DbHelper;
import com.example.purva.ubereats.data.database.IDbHelper;
import com.example.purva.ubereats.data.model.Cart;
import com.example.purva.ubereats.ui.checkout.CheckoutActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    List<Cart.CartBean> cartList;
    IDbHelper iDbHelper;

    public CartAdapter(Context context, List<Cart.CartBean> cartList) {
        this.context = context;
        this.cartList = cartList;
        iDbHelper = new DbHelper(context);
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final Cart.CartBean cartBean = cartList.get(position);
        holder.foodName.setText(cartBean.getFoodName());
        holder.foodQuantity.setText("Quantity: "+cartBean.getQuantity());
        holder.foodPrice.setText("Price: $" + cartBean.getFoodPrice());
        Picasso.with(context).load(cartBean.getImage()).into(holder.foodimage);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("b4 delete",""+iDbHelper.getTCartCount());
                iDbHelper.deleteCartById(cartBean.getCartId());
                //iDbHelper.clearCartTable();
                Log.i("delete",""+iDbHelper.getTCartCount());
                cartList.remove(position);
                CheckoutActivity.updateSubTotal(cartList);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView foodimage;
        TextView foodName;
        TextView foodPrice,foodQuantity;
        Button remove;

        public MyViewHolder(View itemView) {
            super(itemView);
            foodimage = itemView.findViewById(R.id.foodImageCart);
            foodName = itemView.findViewById(R.id.foodNameCart);
            foodPrice = itemView.findViewById(R.id.foodPriceCart);
            foodQuantity = itemView.findViewById(R.id.foodQuantityCart);
            remove = itemView.findViewById(R.id.buttonRemove);

        }
    }
}
