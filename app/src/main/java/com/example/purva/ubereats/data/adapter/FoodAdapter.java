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
import com.example.purva.ubereats.model.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.myViewHolder>{

Context  context;
List<Food>  foodList;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
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
    public void onBindViewHolder(@NonNull FoodAdapter.myViewHolder holder, int position) {

        holder.foodName.setText(foodList.get(position).getFoodName());
        holder.foodRecepiee.setText(foodList.get(position).getRecepiee());
        holder.foodPrice.setText("$" + foodList.get(position).getPrice());
        Picasso.with(context).load(foodList.get(position).getFoodThumb()).into(holder.foodimage);

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
