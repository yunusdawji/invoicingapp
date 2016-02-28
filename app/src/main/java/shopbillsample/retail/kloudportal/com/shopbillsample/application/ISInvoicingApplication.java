package shopbillsample.retail.kloudportal.com.shopbillsample.application;

import android.app.Application;

import shopbillsample.retail.kloudportal.com.shopbillsample.database.DbHelper;

/**
 * Created by yunusdawji on 16-01-10.
 */
public class ISInvoicingApplication extends Application {

    private static DbHelper mDBHelper;

    public static String PACKAGE_NAME;


    @Override
    public void onCreate() {
        super.onCreate();

        mDBHelper = new DbHelper(this);

        PACKAGE_NAME = getApplicationContext().getPackageName();

    }


    public static DbHelper getDBHelper(){
        return mDBHelper;
    }

}
