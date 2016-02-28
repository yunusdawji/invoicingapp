package shopbillsample.retail.kloudportal.com.shopbillsample.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by yunusdawji on 16-01-12.
 */
public class Items implements Parcelable {

    public ArrayList<Item> items = new ArrayList<Item>();

    public Items(Parcel in) {
    }

    public static final Creator<Items> CREATOR = new Creator<Items>() {
        @Override
        public Items createFromParcel(Parcel in) {
            return new Items(in);
        }

        @Override
        public Items[] newArray(int size) {
            return new Items[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public static class Item {
        public String category;
        public String item;
        public String price;
    }
}
