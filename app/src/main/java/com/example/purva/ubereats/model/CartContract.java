package com.example.purva.ubereats.model;

import android.provider.BaseColumns;

public class CartContract {

    public CartContract() {

    }

    public abstract class CartEntry implements BaseColumns{

        public static final String TABLE_NAME ="cart_table";
        public static final String CART_ID = "cart_id";
        public static final String COLUMN_NAME_FOOD_ID = "food_id";
        public static final String COLUMN_NAME_FOOD_NAME = "food_name";
        public static final String COLUMN_NAME_FOOD_PRICE= "food_price";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_FLAVOR = "flavor";
        public static final String COLUMN_NAME_IMAGE = "image";
    }
}