package com.experiment.wangld.listviewexperience;

public class foodListEvent {
    private int foodIndex;
    foodListEvent(int foodIndex){
        this.foodIndex = foodIndex;
    }

    public int getFoodIndex() {
        return foodIndex;
    }
}
