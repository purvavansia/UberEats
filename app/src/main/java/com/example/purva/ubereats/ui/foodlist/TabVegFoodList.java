package com.example.purva.ubereats.ui.foodlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.purva.ubereats.R;
import com.example.purva.ubereats.data.adapter.FoodAdapter;
import com.example.purva.ubereats.data.model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TabVegFoodList extends Fragment {
    @BindView(R.id.veg_rv)
    RecyclerView veg_recyclerview;
    private Unbinder unbinder;
    SharedPreferences sharedPreferences;
    List<Food> foodList;
    String city;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.vegfood_layout, container, false);

        unbinder = ButterKnife.bind(this, v);

        foodList = new ArrayList<Food>();


        sharedPreferences = getActivity().getSharedPreferences("myfile", Context.MODE_PRIVATE);

        String address = sharedPreferences.getString("deliveryaddress","");

        //get city from saved address
        String[] vals = address.split(",");
        city = vals[vals.length -3];
        Log.i("test", city);

        initRecyclerView();

        return v;
    }


    private void initRecyclerView() {
        JsonObjectRequest request = new JsonObjectRequest("http://rjtmobile.com/ansari/fos/fosapp/fos_food.php?food_category=veg&city="+city
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray foodarray = response.getJSONArray("Food");

                    for(int i = 0; i < foodarray.length(); i ++)
                    {
                        JSONObject food = (JSONObject) foodarray.get(i);
                        String foodId = food.getString("FoodId");
                        String foodName = food.getString("FoodName");
                        String foodReceip = food.getString("FoodRecepiee");
                        String foodPrice = food.getString("FoodPrice");
                        String foodThumb = food.getString("FoodThumb");
                        Log.i("test", foodName);
                        Food newfood = new Food(foodId,foodName,foodReceip,foodPrice, foodThumb);

                        foodList.add(newfood);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                FoodAdapter foodAdapter = new FoodAdapter(getActivity(),foodList);
                veg_recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 1));

                foodAdapter.notifyDataSetChanged();
                veg_recyclerview.setAdapter(foodAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("test", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
