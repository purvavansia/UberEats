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
import com.example.purva.ubereats.data.model.FoodList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.MyViewHolder> {

    Context context;
    List<FoodList.FoodBean> foodList;

    public FoodListAdapter(Context context, List<FoodList.FoodBean> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodListAdapter.MyViewHolder mVH;

        View v = LayoutInflater.from(context).inflate(R.layout.foodlist_item, parent, false);

        mVH = new FoodListAdapter.MyViewHolder(v);
        return mVH;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FoodList.FoodBean food = foodList.get(position);
        holder.foodName.setText("Name: "+food.getFoodName());
        holder.foodPrice.setText("Price: $" + food.getFoodPrice());
        Picasso.with(context).load(food.getFoodThumb()).into(holder.foodimage);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView foodimage;
        TextView foodName;
        TextView foodPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            foodimage = itemView.findViewById(R.id.foodImageView);
            foodName = itemView.findViewById(R.id.foodNameView);
            foodPrice = itemView.findViewById(R.id.foodPriceView);

        }
    }

}
