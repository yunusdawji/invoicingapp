package shopbillsample.retail.kloudportal.com.shopbillsample;

import com.kloudportal.shop.quickbill.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import shopbillsample.retail.kloudportal.com.shopbillsample.preferences.PreferenceConstants;


public class HomeFragment extends Fragment {
    Context mActivity;

    public HomeFragment() {
    }

    DataBaseHelper dbhelper;
    EditText shopnameeditText, websiteeditText, phonenumbereditText, currencyeditext,pineditText,addresseditText, cityeditext, stateedittext, countyedittext;
    Button register, cancel;
    Spinner weight,liquidWeight;



    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mActivity = getActivity();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(mActivity, "rake", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        dbhelper = new DataBaseHelper(mActivity);
        register = (Button) rootView.findViewById(R.id.register);
        cancel = (Button) rootView.findViewById(R.id.cancel);
        sharedpreferences = mActivity.getSharedPreferences(PreferenceConstants.MyPREFERENCES, Context.MODE_PRIVATE);

        shopnameeditText = (EditText) rootView.findViewById(R.id.shopnameeditText);
        websiteeditText = (EditText) rootView.findViewById(R.id.websiteeditText);
        phonenumbereditText = (EditText) rootView.findViewById(R.id.phonenumbereditText);
        addresseditText = (EditText) rootView.findViewById(R.id.addresseditText);
        cityeditext = (EditText) rootView.findViewById(R.id.cityeditext);
        stateedittext = (EditText) rootView.findViewById(R.id.stateedittext);
        countyedittext = (EditText) rootView.findViewById(R.id.countyedittext);
        pineditText= (EditText) rootView.findViewById(R.id.pineditText);
        currencyeditext= (EditText) rootView.findViewById(R.id.currencyeditText);

        String[] state = { "KiloGrams","Pounds" };
        weight = (Spinner) rootView.findViewById(R.id.weighteditText);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(mActivity,
                android.R.layout.simple_spinner_item, state);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight.setAdapter(adapter_state);

        String[] state1 = { "Liters","Gallons" };
        liquidWeight = (Spinner) rootView.findViewById(R.id.liquideditText);
        ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(mActivity,
                android.R.layout.simple_spinner_item, state1);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liquidWeight.setAdapter(adapter_state1);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(shopnameeditText.getText())) {
                    shopnameeditText.setError("This field must not be empty");

                } else if (TextUtils.isEmpty(websiteeditText.getText())) {
                    websiteeditText.setError("This field must not be empty");

                } else if (TextUtils.isEmpty(phonenumbereditText.getText())) {

                    phonenumbereditText.setError("This field must not be empty");

                } else if (TextUtils.isEmpty(addresseditText.getText())) {
                    addresseditText.setError("This field must not be empty");

                } else if (TextUtils.isEmpty(cityeditext.getText())) {
                    cityeditext.setError("This field must not be empty");

                } else if (TextUtils.isEmpty(stateedittext.getText())) {
                    stateedittext.setError("This field must not be empty");

                } else if (TextUtils.isEmpty(countyedittext.getText())) {
                    countyedittext.setError("This field must not be empty");

                }//else if (TextUtils.isEmpty(pineditText.getText())) {
                //    pineditText.setError("This field must not be empty");
                //}
                else {
                    DbShop data = new DbShop();
                    data.setShopname(shopnameeditText.getText().toString());
                    data.setWebsite(websiteeditText.getText().toString());
                    data.setPhonenumber(phonenumbereditText.getText().toString());
                    data.setAddress(addresseditText.getText().toString());
                    data.setCity(cityeditext.getText().toString());
                    data.setState(stateedittext.getText().toString());
                    data.setCountry(countyedittext.getText().toString());
                    data.setPin(pineditText.getText().toString());
                    data.setCurrency("$");
                    //data.setWeight(weight.getSelectedItem().toString());
                    //data.setLiquidweight(liquidWeight.getSelectedItem().toString());
                    long todo1_id = dbhelper.createToDo(data);
                    Log.d("Tag Count", "" + dbhelper.getToDoCount());
                    Log.d("DAta Storeere", "" + todo1_id);
                    dbhelper.close();
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    //editor.putString("weight", weight.getSelectedItem().toString());
                    //editor.putString("LiquidWeight", liquidWeight.getSelectedItem().toString());
                    editor.putString(PreferenceConstants.CURRENCY, "$");

                    editor.commit();

                    FragmentManager fragmentManager = ((MainActivity) mActivity).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mainContent, new BillingListFragment());
                    fragmentTransaction.commit();

                }


            }
        });



        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
