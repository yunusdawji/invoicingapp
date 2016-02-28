package shopbillsample.retail.kloudportal.com.shopbillsample.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunusdawji on 16-01-12.
 */
public class CategoriesResponse implements Parcelable {

    public List<String> category = new ArrayList<String>();

    protected CategoriesResponse(Parcel in) {
    }

    public static final Creator<CategoriesResponse> CREATOR = new Creator<CategoriesResponse>() {
        @Override
        public CategoriesResponse createFromParcel(Parcel in) {
            return new CategoriesResponse(in);
        }

        @Override
        public CategoriesResponse[] newArray(int size) {
            return new CategoriesResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
