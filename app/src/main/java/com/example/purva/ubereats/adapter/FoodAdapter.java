package com.example.purva.ubereats.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.purva.ubereats.R;
import com.example.purva.ubereats.model.Food;
import com.example.purva.ubereats.ui.fooddetail.FoodDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.myViewHolder>{

    SharedPreferences sharedPreferences;
    Context  context;
    List<Food>  foodList;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        sharedPreferences = context.getSharedPreferences("myfile",Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public FoodAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodAdapter.myViewHolder mVH;

        View v = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);

        mVH = new FoodAdapter.myViewHolder(v);
        return mVH;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.myViewHolder holder, final int position) {

        holder.foodName.setText(foodList.get(position).getFoodName());
        holder.foodRecepiee.setText(foodList.get(position).getRecepiee());
        holder.foodPrice.setText("$" + foodList.get(position).getPrice());
        Picasso.with(context).load(foodList.get(position).getFoodThumb()).into(holder.foodimage);

        holder.foodimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FoodDetailActivity.class);
                context.startActivity(intent);
                sharedPreferences.edit().putString("foodimage", foodList.get(position).getFoodThumb()).commit();
                sharedPreferences.edit().putString("foodrecepiee",foodList.get(position).getRecepiee()).commit();
                sharedPreferences.edit().putString("foodprice", foodList.get(position).getPrice()).commit();
                sharedPreferences.edit().putString("foodname",foodList.get(position).getFoodName()).commit();
                sharedPreferences.edit().putString("foodid", foodList.get(position).getFoodId()).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
     ImageView foodimage;
     TextView foodName;
     TextView foodPrice;
     TextView foodRecepiee;

        public myViewHolder(View itemView) {
            super(itemView);
            foodimage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            foodRecepiee = itemView.findViewById(R.id.foodReceipiee);

           // itemView.setOnClickListener(this);
        }

    }
}
