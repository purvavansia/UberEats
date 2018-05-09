package com.example.purva.ubereats.database;

import com.example.purva.ubereats.model.Cart;

import java.util.List;

public interface IDbHelper {

    List<Cart.CartBean> getAllCart();
    public int getTCartCount();
    public int insertCartRecord(String foodId, String foodName,String foodPrice, String foodFlavor, String quantity, String image);
    public void deleteCartById(Integer cartId);
    public boolean clearCartTable();
}