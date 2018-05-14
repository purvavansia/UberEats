package com.example.purva.ubereats.ui.checkout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.purva.ubereats.R;
import com.example.purva.ubereats.data.adapter.CartAdapter;
import com.example.purva.ubereats.data.adapter.FoodListAdapter;
import com.example.purva.ubereats.data.database.DbHelper;
import com.example.purva.ubereats.data.database.IDbHelper;
import com.example.purva.ubereats.data.model.Cart;
import com.example.purva.ubereats.data.model.FoodList;
import com.example.purva.ubereats.network.ApiService;
import com.example.purva.ubereats.network.RetrofitInstance;
import com.example.purva.ubereats.ui.confirm.OrderConfirmationctivity;
import com.example.purva.ubereats.ui.foodlist.FoodListActivity;
import com.example.purva.ubereats.ui.orderhistory.OrderHistoryActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SharedPreferences sharedPreferences;
    TextView deliveryAddress,subtotal;
    String streetAddr;
    RecyclerView recyclerView,checkoutRecyclerView;
    ArrayList<FoodList> foodList;
    List<Cart.CartBean> foods;
    String city;
    IDbHelper iDbHelper;
    Button btn_placeorder;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

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
        btn_placeorder = findViewById(R.id.buttonPlaceOrder);
        subtotal = findViewById(R.id.valueSubTotal);
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
                Log.i("response", " " + response.body().getFood());
                FoodListAdapter foodListAdapter = new FoodListAdapter(CheckoutActivity.this,response.body().getFood());
                recyclerView.setAdapter(foodListAdapter);
            }

            @Override
            public void onFailure(Call<FoodList> call, Throwable t) {

            }
        });

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent intent = new Intent(CheckoutActivity.this, FoodListActivity.class);
                startActivity(intent);
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                Intent intent = new Intent(CheckoutActivity.this, CheckoutActivity.class);
                startActivity(intent);

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

                Intent intent = new Intent(CheckoutActivity.this, OrderHistoryActivity.class);
                startActivity(intent);

            }
        });


        foods = iDbHelper.getAllCart();
        if(foods.size()==0){
            Toast.makeText(CheckoutActivity.this,"You have No Items in your Cart right now",Toast.LENGTH_LONG).show();
        }
        else {
            CartAdapter cartAdapter = new CartAdapter(this, foods);
            checkoutRecyclerView.setAdapter(cartAdapter);
        }

        setSubtotal(updateSubTotal(foods));

        btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutActivity.this, OrderConfirmationctivity.class);
                startActivity(intent);
            }
        });

    }


    public void setSubtotal(int total){
        subtotal.setText(""+total);
    }
    public static int updateSubTotal(List<Cart.CartBean> foods){
        int subTotal = 0;
        for(int i=0; i< foods.size();i++){
            subTotal = subTotal + Integer.parseInt(foods.get(i).getFoodPrice());

        }
        Log.i("subtotal",""+subTotal);
        return subTotal;
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
