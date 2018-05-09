package com.example.purva.ubereats.data.model;

import java.util.List;

public class Cart {

    private List<CartBean> cartBeans;

    public List<CartBean> getTransaction() {
        return cartBeans;
    }

    public void setTransaction(List<CartBean> cart) {
        this.cartBeans = cart;
    }

    public static class CartBean{
        Integer cartId;
        String foodId, foodName, foodPrice, quantity,image,flavor;

        public CartBean(Integer cartId, String foodId, String foodName, String foodPrice, String quantity, String image, String flavor) {
            this.cartId = cartId;
            this.foodId = foodId;
            this.foodName = foodName;
            this.foodPrice = foodPrice;
            this.quantity = quantity;
            this.image = image;
            this.flavor = flavor;
        }

        public CartBean(String foodId, String foodName, String foodPrice, String quantity, String image, String flavor) {
            this.foodId = foodId;
            this.foodName = foodName;
            this.foodPrice = foodPrice;
            this.quantity = quantity;
            this.image = image;
            this.flavor = flavor;
        }

        public Integer getCartId() {
            return cartId;
        }

        public void setCartId(Integer cartId) {
            this.cartId = cartId;
        }

        public String getfoodId() {
            return foodId;
        }

        public void setfoodId(String foodId) {
            this.foodId = foodId;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getFoodPrice() {
            return foodPrice;
        }

        public void setFoodPrice(String foodPrice) {
            this.foodPrice = foodPrice;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getFlavor() {
            return flavor;
        }

        public void setFlavor(String flavor) {
            this.flavor = flavor;
        }
    }


}
