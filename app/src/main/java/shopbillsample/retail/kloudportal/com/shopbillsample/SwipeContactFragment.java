package shopbillsample.retail.kloudportal.com.shopbillsample;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kloudportal.shop.quickbill.R;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import shopbillsample.retail.kloudportal.com.shopbillsample.model.ProductBean;

public class SwipeContactFragment extends Fragment {

	//Layouts
    ListView lv;
    Context context;
    List<ProductBean> data=new ArrayList<ProductBean>();
    ArrayList prgmName;
    View parentView;
    CustomAdapter madapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    private ArrayList<String> mDataSet;
    public static int [] prgmImages={R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.recyclerview, container, false);

        context=getActivity();
//        ActionBarActivity activity = (ActionBarActivity) getActivity();
//        activity.setSupportActionBar(toolbar);
//

        recyclerView = (RecyclerView) parentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Item Decorator:
        recyclerView.addItemDecoration(new DividerItemDecoration(context.getResources().getDrawable(R.drawable.divider)));
        recyclerView.setItemAnimator(new FadeInLeftAnimator());

        // Adapter:
        String[] adapterData = new String[]{"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
        mDataSet = new ArrayList<String>(Arrays.asList(adapterData));
        mAdapter = new RecyclerViewAdapter(context, mDataSet,recyclerView);
        ((RecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(mAdapter);

        /* Listeners */
        recyclerView.setOnScrollListener(onScrollListener);


        return parentView;
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("ListView", "onScrollStateChanged");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Could hide open views here if you wanted. //
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
    	  ((MainActivity)getActivity()).openorclose();
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	//	setHasOptionsMenu(true);

    }
    public void showDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final  View promptView = layoutInflater.inflate(R.layout.prompt, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setPositiveButton(android.R.string.ok, null);
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, null);
        final AlertDialog alertD = alertDialogBuilder.create();
        alertD.setCanceledOnTouchOutside(false);
        /*final EditText et_productna = (EditText) promptView.findViewById(R.id.et_productname);
        final RadioGroup radioSexGroup = (RadioGroup) promptView.findViewById(R.id.radioSex);
        final EditText et_price = (EditText) promptView.findViewById(R.id.et_price);
        final EditText et_quantity = (EditText) promptView.findViewById(R.id.et_quantity);
        String selectedRadioString;
        alertD.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_productna, InputMethodManager.SHOW_IMPLICIT);
            }
        });


        alertD.show();

        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                RadioButton radioButton;

                // find the radiobutton by returned id
                radioButton = (RadioButton) promptView.findViewById(selectedId);
                et_quantity.setHint("Enter Quantity "+radioButton.getText());
                et_quantity.setVisibility(View.VISIBLE);

            }
        });

        // setup a dialog window

        Button positiveButton = alertD.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View onClick) {

                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_quantity.getWindowToken(), 0);

                if (TextUtils.isEmpty(et_productna.getText())) {
                    et_productna.setError("Enter Product name");
                } else if (TextUtils.isEmpty(et_price.getText())) {
                    et_price.setError("Enter Price of Product");
                } else if (TextUtils.isEmpty(et_quantity.getText())) {
                    et_quantity.setError("Enter Quantity");
                }else{
                    ProductBean pb = new ProductBean();
                    pb.setProductname(et_productna.getText().toString());
                    double d = Double.parseDouble(et_price.getText().toString() );
                    pb.setProductPrice(d);
                    pb.setProductquantitiy(Integer.parseInt(et_quantity.getText().toString()));
                    pb.setTotalprice(Integer.parseInt(et_quantity.getText().toString()) * d);
                    data.add(pb);
                    //  mAdapter.refreshAdapter(listData);
                    //mAdapter.refreshAdapter(listData);
                    alertD.dismiss();

                    madapter.notifyDataSetChanged();


                }
            }
        });


*/

    }

    
}
