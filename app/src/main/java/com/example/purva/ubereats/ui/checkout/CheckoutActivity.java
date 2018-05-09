package com.example.purva.ubereats.ui.checkout;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.purva.ubereats.R;
import com.example.purva.ubereats.data.adapter.CartAdapter;
import com.example.purva.ubereats.data.adapter.FoodListAdapter;
import com.example.purva.ubereats.data.database.DbHelper;
import com.example.purva.ubereats.data.database.IDbHelper;
import com.example.purva.ubereats.data.model.Cart;
import com.example.purva.ubereats.data.model.FoodList;
import com.example.purva.ubereats.model.Food;
import com.example.purva.ubereats.network.ApiService;
import com.example.purva.ubereats.network.RetrofitInstance;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SharedPreferences sharedPreferences;
    TextView deliveryAddress;
    String streetAddr;
    RecyclerView recyclerView,checkoutRecyclerView;
    ArrayList<FoodList> foodList;
    List<Cart.CartBean> foods;
    String city;
    IDbHelper iDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapShort);
        mapFragment.getMapAsync(this);


        sharedPreferences = getSharedPreferences("myfile",MODE_PRIVATE);
        streetAddr = sharedPreferences.getString("deliveryaddress","");
        String[] vals = streetAddr.split(",");
        city = vals[vals.length -3];
        deliveryAddress = findViewById(R.id.streetAdrress);
        recyclerView = findViewById(R.id.AlsoView_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        checkoutRecyclerView = findViewById(R.id.checkout_recyclerView);
        checkoutRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        checkoutRecyclerView.setItemAnimator(new DefaultItemAnimator());
        checkoutRecyclerView.setHasFixedSize(true);

        iDbHelper = new DbHelper(this);
        foodList = new ArrayList<>();
        foods = new ArrayList<>();
        ApiService apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
        Call<FoodList> foodCall = apiService.getFoodDetails("delhi");
        foodCall.enqueue(new Callback<FoodList>() {
            @Override
            public void onResponse(Call<FoodList> call, Response<FoodList> response) {
                Log.i("response", "SIZE: " + response.body().getFood());
                FoodListAdapter foodListAdapter = new FoodListAdapter(CheckoutActivity.this,response.body().getFood());
                recyclerView.setAdapter(foodListAdapter);
            }

            @Override
            public void onFailure(Call<FoodList> call, Throwable t) {

            }
        });

        foods = iDbHelper.getAllCart();
        CartAdapter cartAdapter = new CartAdapter(this,foods);
        checkoutRecyclerView.setAdapter(cartAdapter);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng address = getLocationFromAddress(this, streetAddr);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(address, 15.0f));
        deliveryAddress.setText(streetAddr);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

    }
}
