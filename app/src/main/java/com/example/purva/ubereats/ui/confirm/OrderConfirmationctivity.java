package com.example.purva.ubereats.ui.confirm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.example.purva.ubereats.data.model.OrderConfirmed;
import com.example.purva.ubereats.ui.checkout.CheckoutActivity;
import com.example.purva.ubereats.ui.foodlist.FoodListActivity;
import com.example.purva.ubereats.ui.orderhistory.OrderHistoryActivity;
import com.github.clans.fab.FloatingActionButton;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.purva.ubereats.MainActivity;
import com.example.purva.ubereats.R;

import com.example.purva.ubereats.data.database.DbHelper;
import com.example.purva.ubereats.data.database.IDbHelper;
import com.example.purva.ubereats.data.model.Cart;
import com.example.purva.ubereats.data.model.OrderConfirmed;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
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

import me.lynnchurch.library.FloatingActionButtonMenu;


public class OrderConfirmationctivity extends AppCompatActivity {
    IDbHelper iDbHelper;
    List<Cart.CartBean> foods;
    SharedPreferences sharedPreferences;
    String streetAddr;
    List<OrderConfirmed> confirmedOrderList;
    RecyclerView confirmedOrder_rv;
    String getOrderId;
    String formattedDate;
    FloatingActionButtonMenu fabMenu;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    TextView foodNameTxt, foodNumTxt, foodAddrTxt, foodTotalTxt, foodDateTxt;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmationctivity);





        sharedPreferences = getSharedPreferences("myfile",MODE_PRIVATE);
        streetAddr = sharedPreferences.getString("deliveryaddress","");

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Intent intent = new Intent(OrderConfirmationctivity.this, FoodListActivity.class);
                startActivity(intent);
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                Intent intent = new Intent(OrderConfirmationctivity.this, CheckoutActivity.class);
                startActivity(intent);

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

                Intent intent = new Intent(OrderConfirmationctivity.this, OrderHistoryActivity.class);
                startActivity(intent);

            }
        });



        foodNameTxt = findViewById(R.id.foodName);
        foodAddrTxt = findViewById(R.id.foodAddr);
        foodTotalTxt = findViewById(R.id.foodTotal);
        foodDateTxt = findViewById(R.id.foodDate);
        imageView = findViewById(R.id.iv_qr);
       /* fabMenu = findViewById(R.id.fab_menu);

        fabMenu.setOnMenuItemClickListener(new FloatingActionButtonMenu.OnMenuItemClickListener()
        {
            @Override
            public void onMenuItemClick(FloatingActionButton button, int btnId)
            {
                switch (btnId)
                {
//
//                    case R.id.backup:
//                        Toast.makeText(OrderConfirmationctivity.this, "Go to cart", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.grade:
//                       Toast.makeText(OrderConfirmationctivity.this, "Go to Home screen", Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
                }
            }
        });*/




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

                    BarcodeDetector detector =
                            new BarcodeDetector.Builder(getApplicationContext())
                                    .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                                    .build();
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<Barcode> barcodes = detector.detect(frame);
                    Barcode thisCode = barcodes.valueAt(0);
                    TextView txtView = (TextView) findViewById(R.id.scannedQR);
                    txtView.setText(thisCode.rawValue);
                    if(!detector.isOperational()){
                        txtView.setText("Could not set up the detector!");
                        return;
                    }


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


