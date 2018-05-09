package com.example.purva.ubereats.data;

import com.example.purva.ubereats.data.model.Cart;

import java.util.List;

public class DbManager implements IDbManager {
    @Override
    public List<Cart.CartBean> getAllCart() {
        return null;
    }

    @Override
    public int getTCartCount() {
        return 0;
    }

    @Override
    public int insertCartRecord(String foodId, String foodName, String foodPrice, String foodFlavor, String quantity, String image) {
        return 0;
    }

    @Override
    public void deleteCartById(Integer cartId) {

    }

    @Override
    public boolean clearCartTable() {
        return false;
    }
}
