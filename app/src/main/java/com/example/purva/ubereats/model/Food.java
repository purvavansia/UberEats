package com.example.purva.ubereats.model;

public class Food {
    String foodId;
    String foodName;
    String recepiee;
    String price;
    String foodThumb;

    public Food(String foodId, String foodName, String recepiee, String price, String foodThumb) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.recepiee = recepiee;
        this.price = price;
        this.foodThumb = foodThumb;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRecepiee() {
        return recepiee;
    }

    public void setRecepiee(String recepiee) {
        this.recepiee = recepiee;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFoodThumb() {
        return foodThumb;
    }

    public void setFoodThumb(String foodThumb) {
        this.foodThumb = foodThumb;
    }
}
