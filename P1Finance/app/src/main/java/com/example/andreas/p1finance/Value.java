package com.example.andreas.p1finance;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andreas on 2016-09-16.
 */
public class Value implements Parcelable{

        private String title;
        private String date;
        private String amount;
        private String category;

        public Value(String title, String date, String amount, String category) {
            this.title = title;
            this.date = date;
            this.amount = amount;
            this.category = category;
        }
        //Get, set- methods for alla values needed
        public String getTitle() {return title;}

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCategory() { return category; }

        public void setCategory(String category) { this.category = category; }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(date);
            dest.writeString(amount);
            dest.writeString(category);
        }

        public static final Creator<Value> CREATOR = new Creator<Value>() {
            public Value createFromParcel(Parcel source) {
                return new Value(source.readString(),source.readString(),source.readString(),source.readString());
            }

            public Value[] newArray(int size) {return new Value [size];}
        };
    }


