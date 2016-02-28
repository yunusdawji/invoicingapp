package shopbillsample.retail.kloudportal.com.shopbillsample;


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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import com.kloudportal.shop.quickbill.R;


public class EditUserDAta extends Fragment {
    Context mActivity;

    public EditUserDAta() {
    }
    Spinner weight,liquidWeight;

    DataBaseHelper dbhelper;
    EditText shopnameeditText,currencyeditText, websiteeditText, phonenumbereditText, pineditText,addresseditText, cityeditext, stateedittext, countyedittext;
    Button register, cancel;
    List<DbShop> dataSqlite;
    SharedPreferences sharedpreferences;
    Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edituser, container, false);
        mActivity = getActivity();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                menuItem.setVisible(false);
                Toast.makeText(mActivity, "rake", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        dbhelper=new DataBaseHelper(mActivity);

        dataSqlite=dbhelper.getAllData();
        register = (Button) rootView.findViewById(R.id.register);
        cancel = (Button) rootView.findViewById(R.id.cancel);

        sharedpreferences = mActivity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        shopnameeditText = (EditText) rootView.findViewById(R.id.shopnameeditText);

        websiteeditText = (EditText) rootView.findViewById(R.id.websiteeditText);
        phonenumbereditText = (EditText) rootView.findViewById(R.id.phonenumbereditText);
        addresseditText = (EditText) rootView.findViewById(R.id.addresseditText);
        cityeditext = (EditText) rootView.findViewById(R.id.cityeditext);
        stateedittext = (EditText) rootView.findViewById(R.id.stateedittext);
        countyedittext = (EditText) rootView.findViewById(R.id.countyedittext);
        pineditText= (EditText) rootView.findViewById(R.id.pineditText);
        currencyeditText= (EditText) rootView.findViewById(R.id.currencyeditText);

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

        if(dataSqlite != null && dataSqlite.size() >0) {
            register.setText("Update");
            // shopnameeditText.setFocusable(false);
            DbShop dbshop = dataSqlite.get(0);
            shopnameeditText.setText(dbshop.getShopname());
            websiteeditText.setText(dbshop.getWebsite());
            phonenumbereditText.setText(dbshop.getPhonenumber());
            addresseditText.setText(dbshop.getAddress());
            cityeditext.setText(dbshop.getCity());
            stateedittext.setText(dbshop.getState());
            countyedittext.setText(dbshop.getCountry());
            pineditText.setText(dbshop.getPin());
            currencyeditText.setText(dbshop.getCurrency());
            weight.setSelection(adapter_state.getPosition(dbshop.getWeight()));
            liquidWeight.setSelection(adapter_state1.getPosition(dbshop.getLiquidweight()));

        }

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

                } else {
                    if(register.getText().toString().equalsIgnoreCase("Register")){
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
                        long todo1_id = dbhelper.createToDo(data);
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                       // editor.putString("weight", weight.getSelectedItem().toString());
                        //editor.putString("LiquidWeight", liquidWeight.getSelectedItem().toString());
                        editor.putString("currency", "$");

                        editor.commit();
                        Log.d("Tag Count", "" + dbhelper.getToDoCount());
                        Log.d("Insert Storeere", "" + todo1_id);
                        dbhelper.close();

                    }else{
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
                        long todo1_id = dbhelper.updateToDo(data);

                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        //editor.putString("weight", weight.getSelectedItem().toString());
                        //editor.putString("LiquidWeight", liquidWeight.getSelectedItem().toString());
                        editor.putString("currency", "$");

                        editor.commit();

                        Log.d("Tag Count", "" + dbhelper.getToDoCount());
                        Log.d("Update sucessfully", "" + todo1_id);
                        dbhelper.close();

                    }



                    FragmentManager fragmentManager = ((MainActivity)mActivity).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mainContent, new BillingListFragment());
                    fragmentTransaction.commit();

                }


            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((MainActivity)mActivity).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainContent, new BillingListFragment());
                fragmentTransaction.commit();
            }
        });


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {

        inflater.inflate(R.menu.menu_main, menu);
        //menu.getItem(R.id.action_search).setVisible(false);

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
