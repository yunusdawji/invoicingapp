package shopbillsample.retail.kloudportal.com.shopbillsample;

import com.kloudportal.shop.quickbill.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import shopbillsample.retail.kloudportal.com.shopbillsample.model.ProductBean;

public class ContactFragment extends Fragment {
	
	//Layouts
    ListView lv;
    Context context;
    List<ProductBean> data=new ArrayList<ProductBean>();
    ArrayList prgmName;
    View parentView;
    CustomAdapter madapter;
    public static int [] prgmImages={R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_contact, container, false);

        context=getActivity();
//        ActionBarActivity activity = (ActionBarActivity) getActivity();
//        activity.setSupportActionBar(toolbar);
//

//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//
//        ImageView image = (ImageView) toolbar.findViewById(R.id.toolbar_image);
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //((MainActivity)getActivity()).openorclose();
//                try{
//                sendGet();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });

//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//
//                showDialog();
//                return true;
//            }
//        });

        for(int i=0;i<10;i++){

            ProductBean pb=new ProductBean();
            pb.setProductname("ttttt");
            pb.setProductPrice(1);
            pb.setTotalprice(2);
            pb.setProductquantitiy(1);
            data.add(pb);
        }

        lv=(ListView) parentView.findViewById(R.id.listView);
        madapter=new CustomAdapter(context, data);
        lv.setAdapter(madapter);
    	
        return parentView;
    }

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
    private static void sendGet() throws Exception {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://192.168.1.3:8080/RajeshSample/service/allcountries";


                try{
                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                    // optional default is GET
                    con.setRequestMethod("GET");

                    //add request header

                    int responseCode = con.getResponseCode();
                    System.out.println("\nSending 'GET' request to URL : " + url);
                    System.out.println("Response Code : " + responseCode);

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    Log.d("rajesh","response");

                    in.close();

                    //print result
                    System.out.println(response.toString());
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("rajesh", "response");
                }

            }
        }).start();



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
       /*
        // setup a dialog window
        Button positiveButton = alertD.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
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
