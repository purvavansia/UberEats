package com.example.purva.ubereats.data.model;

import java.util.List;

public class FoodList {


    private List<FoodBean> Food;

    public List<FoodBean> getFood() {
        return Food;
    }

    public void setFood(List<FoodBean> Food) {
        this.Food = Food;
    }

    public static class FoodBean {
        /**
         * FoodId : 701
         * FoodName : Tadka Vegetables
         * FoodRecepiee : tadka vegetables, baby spinach roasted sesam
         * FoodPrice : 1400
         * FoodThumb : http://rjtmobile.com/ansari/fos/admin/uploads/food_menu_pics/tadka_veg.jpg
         */

        private String FoodId;
        private String FoodName;
        private String FoodRecepiee;
        private String FoodPrice;
        private String FoodThumb;

        public String getFoodId() {
            return FoodId;
        }

        public void setFoodId(String FoodId) {
            this.FoodId = FoodId;
        }

        public String getFoodName() {
            return FoodName;
        }

        public void setFoodName(String FoodName) {
            this.FoodName = FoodName;
        }

        public String getFoodRecepiee() {
            return FoodRecepiee;
        }

        public void setFoodRecepiee(String FoodRecepiee) {
            this.FoodRecepiee = FoodRecepiee;
        }

        public String getFoodPrice() {
            return FoodPrice;
        }

        public void setFoodPrice(String FoodPrice) {
            this.FoodPrice = FoodPrice;
        }

        public String getFoodThumb() {
            return FoodThumb;
        }

        public void setFoodThumb(String FoodThumb) {
            this.FoodThumb = FoodThumb;
        }
    }
}
