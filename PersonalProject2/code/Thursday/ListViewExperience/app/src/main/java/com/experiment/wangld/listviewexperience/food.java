package com.experiment.wangld.listviewexperience;

import android.os.Parcel;
import android.os.Parcelable;

public class food implements Parcelable{
    private String name;
    private String type;
    private String label;
    private String cover;
    private String nutrition;
    private int color;
    public food(String name, String type, String label, String nutrition, String cover, int color){
        this.name = name;
        this.type = type;
        this.nutrition = nutrition;
        this.color = color;
        this.cover = cover;
        this.label = label;
    }
    protected food(Parcel in){
        String data[] = new String[5];
        in.readStringArray(data);
        this.name = data[0];
        this.type = data[1];
        this.label = data[2];
        this.cover = data[3];
        this.nutrition = data[4];
        this.color = in.readInt();
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeStringArray(new String[]{name, type, label, cover, nutrition});
        parcel.writeInt(color);
    }
    public static final Creator<food> CREATOR = new Creator<food>() {
        @Override
        public food createFromParcel(Parcel in) {
            return new food(in);
        }

        @Override
        public food[] newArray(int size) {
            return new food[size];
        }
    };

    public String getCover() {
        return cover;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getNutrition() {
        return nutrition;
    }

    public String getType() {
        return type;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLabel(){
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
