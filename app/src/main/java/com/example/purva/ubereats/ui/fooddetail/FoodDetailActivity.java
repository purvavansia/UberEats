package com.example.purva.ubereats.ui.fooddetail;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.purva.ubereats.R;
import com.example.purva.ubereats.database.DbHelper;
import com.example.purva.ubereats.database.IDbHelper;
import com.squareup.picasso.Picasso;

public class FoodDetailActivity extends AppCompatActivity implements IFoodDetailView{
ImageView imageView;
TextView recepieeTxt, totalTxt;
NumberPicker numberPicker;
SharedPreferences sharedPreferences;
Button addtocartBtn;
int totalnum, quantity, foodpri;
String recepiee,image,price,foodid,foodname;
RadioGroup radioGroup;
IFoodDetailPresenter iPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);



        imageView = findViewById(R.id.foodImage);
        recepieeTxt = findViewById(R.id.receipieedetail);
        numberPicker = findViewById(R.id.numberPicker);
        totalTxt = findViewById(R.id.total);
        addtocartBtn = findViewById(R.id.addtocart_btn);
        radioGroup = findViewById(R.id.flavor);


        sharedPreferences = getSharedPreferences("myfile",MODE_PRIVATE);
        recepiee = sharedPreferences.getString("foodrecepiee","");
        image = sharedPreferences.getString("foodimage","");
        price = sharedPreferences.getString("foodprice","");
        foodid = sharedPreferences.getString("foodid","");
        foodname = sharedPreferences.getString("foodname","");
        foodpri = Integer.valueOf(price);


        iPresenter = new FoodDetailPresenter(this,this);



        addtocartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioButton rb = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId()));

                final String flavor = rb.getText().toString();

                iPresenter.addTocart(foodid,foodname, String.valueOf(foodpri),flavor,String.valueOf(quantity),image);

            }
        });

    }



    @Override
    public void showFoodDetail() {

        Picasso.with(this).load(image).into(imageView);
        recepieeTxt.setText(recepiee);
    }

    @Override
    public void setNumberPicker() {
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(20);
        NumberPicker.OnValueChangeListener onValueChangeListener =
                new 	NumberPicker.OnValueChangeListener(){
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        quantity = numberPicker.getValue();
                        totalnum = quantity * foodpri;
                        totalTxt.setText("Total: "+totalnum);
                    }
                };
        numberPicker.setOnValueChangedListener(onValueChangeListener);


    }
}
