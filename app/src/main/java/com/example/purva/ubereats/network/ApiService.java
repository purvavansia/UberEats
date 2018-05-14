package com.example.purva.ubereats.network;

import com.example.purva.ubereats.data.model.FoodList;
import com.example.purva.ubereats.data.model.Order;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


    //http://rjtmobile.com/ansari/fos/fosapp/fos_food_loc.php?city=delhi

    @GET("fos_food_loc.php")
    Call<FoodList> getFoodDetails(@Query("city") String city);

    @GET("order_recent.php")
    Call<Order> getOrderDetails(@Query("user_phone") String phone);
}
