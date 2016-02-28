package shopbillsample.retail.kloudportal.com.shopbillsample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.kloudportal.shop.quickbill.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import shopbillsample.retail.kloudportal.com.shopbillsample.application.ISInvoicingApplication;
import shopbillsample.retail.kloudportal.com.shopbillsample.database.DbHelper;
import shopbillsample.retail.kloudportal.com.shopbillsample.model.Invoice;
import shopbillsample.retail.kloudportal.com.shopbillsample.model.ProductBean;
import shopbillsample.retail.kloudportal.com.shopbillsample.preferences.PreferenceConstants;


public class SecondTimeFragment extends Fragment {

    View parentView;
    SharedPreferences sharedpreferences;

    public SecondTimeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragemnt_second, container, false);
        TextView cart=(TextView)parentView.findViewById(R.id.fragment_add_item);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(),Sample2.class));
                FragmentManager fragmentManager = ((MainActivity)getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainContent, new BillingListFragment());
                fragmentTransaction.commit();
            }
        });
        TextView description =(TextView)parentView.findViewById(R.id.fragment_description);
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a pop up
                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.alert_description, null);
                Dialog dialog = createDescriptionPopup(getActivity(), popupView);
            }
        });

        sharedpreferences = getActivity().getSharedPreferences(PreferenceConstants.MyPREFERENCES, Context.MODE_PRIVATE);
        return parentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("Create Invoice");

    }

    public Dialog createDescriptionPopup(Context mContext, final View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton(android.R.string.ok, null);
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, null);
        final AlertDialog alertD = alertDialogBuilder.create();
        alertD.setCanceledOnTouchOutside(false);
        alertD.show();

        Button positiveButton = alertD.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClick) {
                //take the textfield and save it somewhere

                try {

                    //add the constant to the sharedprefrence which will be used later
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    String text = ((EditText)view.findViewById(R.id.alert_description_text)).getText().toString();
                    editor.putString(PreferenceConstants.DESCRIPTION, text);
                    editor.commit();

                    alertD.dismiss();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return alertD;
    }
}
