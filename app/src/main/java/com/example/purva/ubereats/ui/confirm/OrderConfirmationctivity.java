package com.example.purva.ubereats.ui.confirm;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.purva.ubereats.R;

import com.example.purva.ubereats.data.database.DbHelper;
import com.example.purva.ubereats.data.database.IDbHelper;
import com.example.purva.ubereats.data.model.Cart;
import com.example.purva.ubereats.model.OrderConfirmed;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class OrderConfirmationctivity extends AppCompatActivity {
    IDbHelper iDbHelper;
    List<Cart.CartBean> foods;
    SharedPreferences sharedPreferences;
    String streetAddr;
    List<OrderConfirmed> confirmedOrderList;
    RecyclerView confirmedOrder_rv;
    String getOrderId;
    String formattedDate;

    TextView foodNameTxt, foodNumTxt, foodAddrTxt, foodTotalTxt, foodDateTxt;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmationctivity);





        sharedPreferences = getSharedPreferences("myfile",MODE_PRIVATE);
        streetAddr = sharedPreferences.getString("deliveryaddress","");





        foodNameTxt = findViewById(R.id.foodName);
        foodNumTxt = findViewById(R.id.foodNum);
        foodAddrTxt = findViewById(R.id.foodAddr);
        foodTotalTxt = findViewById(R.id.foodTotal);
        foodDateTxt = findViewById(R.id.foodDate);
        imageView = findViewById(R.id.iv_qr);


        iDbHelper = new DbHelper(this);
        foods = new ArrayList<>();


        foods = iDbHelper.getAllCart();


        initRecyclerView();

        /*Calendar datetime = Calendar.getInstance();
        String currenttime = datetime.getTime().toString();
       // Log.i(TAG, "confirmed ticket: $currenttime")*/


        iDbHelper.clearCartTable();


    }


    public void initRecyclerView(){

        for (int i = 0; i < foods.size(); i++) {

            Cart.CartBean food = foods.get(i);
            String foodid = food.getfoodId();
            Log.i("test", "Foodid is " + foodid);
            String foodname = food.getFoodName();
            Log.i("test", "Foodname is " + foodname);
            String price = food.getFoodPrice();
            Log.i("test", "FoodPrice is " + price);
            String quantity = food.getQuantity();
            Log.i("test", "quantity is " + quantity);

            int priceint = Integer.valueOf(price);
            int quantityint = Integer.valueOf(quantity);

            int totalint = priceint * quantityint;

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             formattedDate= df.format(c.getTime());

            StringRequest request = new StringRequest(Request.Method.GET, "http://rjtmobile.com/ansari/fos/fosapp/order_request.php?&order_category=veg_nonveg" +
                    "&order_name=" + foodname + "&order_quantity=" + quantity + "&total_order=" + String.valueOf(totalint) + "&order_delivery_add="+streetAddr+
                    "&order_date="+formattedDate+"&user_phone=6174879092",
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.contains("confirmed")) {
                                Toast.makeText(OrderConfirmationctivity.this, "Order is Placed!", Toast.LENGTH_LONG).show();

                                String[] responsesplit = response.split(" ");
                                getOrderId= responsesplit[responsesplit.length -1];

                                Log.i("test", "order id is "+ getOrderId);

                                secondCall();
                            }
                            }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("error1",error.toString());
                }
            });

            Volley.newRequestQueue(OrderConfirmationctivity.this).add(request);

        }




       /* //adapter?

        ConfirmationAdapter confirmationAdapter = new ConfirmationAdapter(OrderConfirmationctivity.this,confirmedOrderList);
        confirmationAdapter.notifyDataSetChanged();
        confirmedOrder_rv.setAdapter(confirmationAdapter);*/


    }


    public void secondCall(){


        JsonObjectRequest confirmationrequest = new JsonObjectRequest("http://rjtmobile.com/ansari/fos/fosapp/order_confirmation.php?&order_id="+getOrderId
                , null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray result = response.getJSONArray("Order Detail");
                    JSONObject confirmedorder = (JSONObject) result.get(0);
                    String orderId = confirmedorder.getString("OrderId");

                    String ordername = confirmedorder.getString("OrderName");
                    Log.i("test","order name:"+ordername);
                    String orderquantity = confirmedorder.getString("OrderQuantity");
                    Log.i("test","order quantity is"+ orderquantity);
                    String orderTotal = confirmedorder.getString("TotalOrder");
                    String orderAddr = confirmedorder.getString("OrderDeliverAdd");
                    String orderDate = confirmedorder.getString("OrderDate");

//                    OrderConfirmed oc = new OrderConfirmed(orderId,ordername,orderquantity,orderTotal,orderAddr,orderDate);
//                    confirmedOrderList.add(oc);


                    foodNameTxt.setText(ordername);
                    foodNumTxt.setText("Quantity: "+ orderquantity);
                    foodAddrTxt.setText("Address: " + orderAddr);
                    foodDateTxt.setText("Date: "+ orderDate);
                    foodTotalTxt.setText("Total: "+ orderTotal);
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                    BitMatrix bitMatrix = null;
                    try {
                        bitMatrix = multiFormatWriter.encode(formattedDate+" "+getOrderId, BarcodeFormat.QR_CODE, 200, 200);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                    imageView.setImageBitmap(bitmap);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(OrderConfirmationctivity.this).add(confirmationrequest);


    }



    }


