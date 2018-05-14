package com.example.purva.ubereats.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.purva.ubereats.R;

import com.example.purva.ubereats.data.model.Order;

import com.example.purva.ubereats.ui.reorder.ReorderActivity;
import com.example.purva.ubereats.ui.track.TrackOrderActivity;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder>{

    Context context;
    List<Order> orderList;
    SharedPreferences sharedPreferences;
    String streetAddr;

    public OrderHistoryAdapter(Context context, List<Order> orders){
        this.context = context;
        this.orderList = orders;
        sharedPreferences = context.getSharedPreferences("myfile",MODE_PRIVATE);



    }


    @NonNull
    @Override
    public OrderHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderHistoryAdapter.MyViewHolder mVh;
        View v = LayoutInflater.from(context).inflate(R.layout.orderhistroy_item,parent,false);
        mVh = new OrderHistoryAdapter.MyViewHolder(v);


        return mVh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        holder.foodname.setText(orderList.get(position).getOrderName());
        holder.fooddate.setText(orderList.get(position).getOrderDate());
        holder.foodtotal.setText("Total: $"+orderList.get(position).getTotalOrder());

        holder.btn_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TrackOrderActivity.class);
                intent.putExtra("orderid",orderList.get(position).getOrderId());
                v.getContext().startActivity(intent);

            }
        });
        holder.btn_reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences.edit().putString("item_name",orderList.get(position).getOrderName()).commit();
                int total = Integer.valueOf(orderList.get(position).getTotalOrder());
                int quantity = Integer.valueOf(orderList.get(position).getQuantity());
                int price = total/quantity;
                sharedPreferences.edit().putString("item_price",String.valueOf(price)).commit();



                Intent intent = new Intent(v.getContext(), ReorderActivity.class);
                v.getContext().startActivity(intent);

            }
        });


    }



    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView foodname, foodtotal, fooddate;
        Button btn_reorder, btn_track;

        public MyViewHolder(View itemView) {
            super(itemView);

            foodname = itemView.findViewById(R.id.foodNameHistroy);
            foodtotal = itemView.findViewById(R.id.foodTotalHistroy);
            fooddate = itemView.findViewById(R.id.foodDateHistroy);
            btn_reorder = itemView.findViewById(R.id.reorder_btn);
            btn_track =itemView.findViewById(R.id.track_btn);
        }
    }

}
