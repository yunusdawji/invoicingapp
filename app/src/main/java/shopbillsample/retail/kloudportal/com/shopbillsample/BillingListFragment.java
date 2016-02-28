
package shopbillsample.retail.kloudportal.com.shopbillsample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.j256.ormlite.dao.Dao;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import harmony.java.awt.Color;
import shopbillsample.retail.kloudportal.com.shopbillsample.adapter.MyDropdownAdapter;
import shopbillsample.retail.kloudportal.com.shopbillsample.adapter.MyItemSpinnerAdapter;
import shopbillsample.retail.kloudportal.com.shopbillsample.application.ISInvoicingApplication;
import shopbillsample.retail.kloudportal.com.shopbillsample.database.DBQuery;
import shopbillsample.retail.kloudportal.com.shopbillsample.database.DbHelper;
import shopbillsample.retail.kloudportal.com.shopbillsample.model.Invoice;
import shopbillsample.retail.kloudportal.com.shopbillsample.model.Items;
import shopbillsample.retail.kloudportal.com.shopbillsample.model.ProductBean;
import shopbillsample.retail.kloudportal.com.shopbillsample.preferences.PreferenceConstants;
import shopbillsample.retail.kloudportal.com.shopbillsample.response.CategoriesResponse;

import android.widget.AdapterView.OnItemSelectedListener;


import com.kloudportal.shop.quickbill.R;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class BillingListFragment extends Fragment {

    private View parentView;
    public static ArrayList<ProductBean> listData = new ArrayList<ProductBean>();
    Context mActivity;
    ListViewAdapter mAdapter = null;
    ListView list;
    Toolbar toolbar;
    //TextView textView;
    private static final String LOG_TAG = "GeneratePDF";

    public static String customername;
    private EditText preparedBy;
    private File pdfFile;
    private String filename = "Sample.pdf";
    private String filepath = "MyInvoices";
    DataBaseHelper dbhelper;
    public static int counter=0;
    SharedPreferences sharedpreferences;
    private BaseFont bfBold;
    List<DbShop> dataSqlite;
    //ImageView cart;

    //hold the invoice data
    private Invoice mInvoice;
    private HashMap<String, Items> mItemsMap = new HashMap<String, Items>();
    ProgressDialog mDialog;

    private MyDropdownAdapter categoriesAdapter = null;
    private MyItemSpinnerAdapter itemsAdapter = null;
    private Button sendmail, continueShopping, doneshopping;


    public BillingListFragment(){
        super();
    }

    public BillingListFragment(Invoice invoice){
        super();
        mInvoice = invoice;
        listData.addAll(mInvoice.getItems());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();

        Time now = new Time();
        now.setToNow();

        Time twseven = new Time();
        twseven.set(31, 1, 2016);

        if(now.month == twseven.month && now.monthDay == twseven.monthDay && now.year == twseven.year){
            parentView = inflater.inflate(R.layout.billinglist_fragment, container, false);
        }
        else {
            parentView = inflater.inflate(R.layout.billinglist_fragment, container, false);
            dbhelper = new DataBaseHelper(mActivity);
            dataSqlite = dbhelper.getAllData();

            if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                Log.v(LOG_TAG, "External Storage not available or you don't have permission to write");
            } else {
                //path for the PDF file in the external storage
                pdfFile = new File(mActivity.getExternalFilesDir(filepath), filename);
            }

        /*cart=(ImageView)parentView.findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });
*/
            list = (ListView) parentView.findViewById(R.id.list);
            mAdapter = new ListViewAdapter(mActivity, list, listData, BillingListFragment.this);
            list.setAdapter(mAdapter);
            mAdapter.setMode(Attributes.Mode.Single);

            //textView=(TextView)parentView.findViewById(R.id.textView);

            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    showDialog();
                    return false;
                }
            });
            sendmail = (Button) parentView.findViewById(R.id.sendmail);
            continueShopping = (Button) parentView.findViewById(R.id.continuebutton);
            doneshopping = (Button) parentView.findViewById(R.id.doneshopButton);
            sharedpreferences = mActivity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

            continueShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog();

                }
            });

            doneshopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listData != null && listData.size() > 0) {

                        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);

                        final View promptView = layoutInflater.inflate(R.layout.mailorprintxml, null);

                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
                        alertDialogBuilder.setView(promptView);
                        alertDialogBuilder.setPositiveButton(android.R.string.ok, null);
                        alertDialogBuilder.setNegativeButton(android.R.string.cancel, null);
                        final AlertDialog alertD = alertDialogBuilder.create();
                        alertD.setCanceledOnTouchOutside(false);
                        alertD.show();
                        final EditText emailet = (EditText) promptView.findViewById(R.id.emailedittext);
                        final RadioGroup radioSexGroup = (RadioGroup) promptView.findViewById(R.id.radioSex);

                        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {

                                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                                RadioButton radioButton;

                                // find the radiobutton by returned id
                                if (selectedId == R.id.sendMail) {
                                    //email
                                    Button b = alertD.getButton(AlertDialog.BUTTON_POSITIVE);
                                    b.setText("Send Mail");
                                    radioButton = (RadioButton) promptView.findViewById(selectedId);
                                    emailet.setVisibility(View.VISIBLE);
                                } else {
                                    Button b = alertD.getButton(AlertDialog.BUTTON_POSITIVE);
                                    b.setText("Print");
                                    emailet.setVisibility(View.GONE);
                                }
                            }
                        });


                        final Button b = alertD.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (b.getText().toString().equalsIgnoreCase("Print")) {
                                    customername = "Print";
                                    updateDatabase();
                                    new TestAsync().execute(listData);
                                    alertD.dismiss();

                                    // doPrint();
                                } else {
                                    if (isValidEmail(emailet.getText().toString())) {
                                        customername = emailet.getText().toString();
                                        updateDatabase();
                                        new TestAsync().execute(listData);
                                        //doPrint();
                                        alertD.dismiss();
                                        alertD.cancel();


                                    } else {
                                        emailet.setError("Enter valid mail address");
                                    }
                                }

                            }
                        });

                    } else {
                        Toast.makeText(mActivity, "No data in list", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            sendmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //create a pop up
                    View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.alert_description, null);
                    ((EditText) popupView.findViewById(R.id.alert_description_text)).setText(sharedpreferences.getString(PreferenceConstants.DESCRIPTION, null));
                    Dialog dialog = createDescriptionPopup(getActivity(), popupView);
                }
            });

            if (mInvoice != null) {
                //textView.setVisibility(View.GONE);
                // cart.setVisibility(View.GONE);
                list.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
            }


            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_home);

            mDialog = ProgressDialog.show(getActivity(), "",
                    "Loading. Please wait...", true);

            new RequestTask().execute("http://stora.co/isinvoicing/getitems.php", "category 1");
        }
        return parentView;
    }

    class RequestTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {

                List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("category", uri[1]));
                response = httpclient.execute(new HttpGet(uri[0]+"?"+ URLEncodedUtils.format(params, "utf-8")));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result==null){
                continueShopping.setEnabled(false);
                doneshopping.setEnabled(false);
                sendmail.setEnabled(false);
                mDialog.dismiss();
                Toast.makeText(getActivity(), "Error Connecting Server", Toast.LENGTH_LONG).show();

            }else {
                Gson gson = new Gson();
                final Items className = gson.fromJson(result, Items.class);

                for (Items.Item i : className.items) {
                    if (mItemsMap.containsKey(i.category)) {
                        Items temp = mItemsMap.get(i.category);
                        temp.items.add(i);
                    } else {
                        Items temp = new Items(null);
                        temp.items.add(i);
                        mItemsMap.put(i.category, temp);
                    }
                }
                mDialog.dismiss();
            }

        }
    }



    class RequestCategoryTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {

                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Gson gson = new Gson();
            final CategoriesResponse className = gson.fromJson(result, CategoriesResponse.class);
            int currentTotal = className.category.size();

            for(String s: className.category){
                //we do calls to get each category
            }

            //Do anything with response..
        }
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
                    String text = ((EditText) view.findViewById(R.id.alert_description_text)).getText().toString();
                    editor.putString(PreferenceConstants.DESCRIPTION, text);
                    editor.commit();

                    alertD.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return alertD;
    }

    public List<String> getCategoriesData(){
        List<String> temp = new ArrayList<String>();
        temp.addAll(mItemsMap.keySet());
        return temp;
    }

    public List<Items.Item> generateItems(String categ){
        if(mItemsMap.containsKey(categ)){
            return mItemsMap.get(categ).items;
        }
        return null;
    }

    public void setItemSpinner(final View promptView, String categ){
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);

        if(itemsAdapter==null && categ != null){
            itemsAdapter = new MyItemSpinnerAdapter(getActivity());
            itemsAdapter.addItems(generateItems(categ));

            //add the dropdown to prompt view
            View spinnerContainer = LayoutInflater.from(getActivity()).inflate(R.layout.layout_items_spinner, null);
            ((LinearLayout)promptView.findViewById(R.id.prompt_dropdown_items)).addView(spinnerContainer);

            final Spinner spinner = (Spinner) spinnerContainer.findViewById(R.id.prompt_items_spinner);
            spinner.setAdapter(itemsAdapter);
            spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    Items.Item clickedObj = (Items.Item) parent.getItemAtPosition(position);
                    itemsAdapter.setCurrentPosition(position);

                    double price = Double.parseDouble(clickedObj.price);
                    TextView textView = (TextView) promptView.findViewById(R.id.item_amount);
                    int temp = parent.getWidth();
                    textView.setWidth(parent.getWidth() - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()));
                    textView.setText("$ " + Double.toString(price));

                    Log.d("Item :", clickedObj.item);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            double price = Double.parseDouble(itemsAdapter.getItem(0).price);
            TextView textView = (TextView) promptView.findViewById(R.id.item_amount);
            textView.setText("$ " + Double.toString(price));
        }else{
            itemsAdapter.clear();
            itemsAdapter.addItems(generateItems(categ));
            itemsAdapter.notifyDataSetChanged();

            double price = Double.parseDouble(itemsAdapter.getItem(0).price);
            TextView textView = (TextView) promptView.findViewById(R.id.item_amount);
            textView.setText("$ " + Double.toString(price));
        }
    }

    public void showDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        final  View promptView = layoutInflater.inflate(R.layout.prompt, null);

        //add the dropdown to prompt view
        View spinnerContainer = LayoutInflater.from(getActivity()).inflate(R.layout.toolbar_spinner,
                null);
        ((LinearLayout)promptView.findViewById(R.id.prompt_dropdown_categories)).addView(spinnerContainer);

        categoriesAdapter = new MyDropdownAdapter(getActivity());
        categoriesAdapter.addItems(getCategoriesData());

        final Spinner spinner = (Spinner) spinnerContainer.findViewById(R.id.toolbar_spinner);
        spinner.setAdapter(categoriesAdapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String clickedObj = (String) parent.getItemAtPosition(position);
                categoriesAdapter.setCurrentPosition(position);
                setItemSpinner(promptView, clickedObj);
                Log.d("Category:", clickedObj);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        setItemSpinner(promptView, categoriesAdapter.getItem(0));



        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setPositiveButton(android.R.string.ok, null);
        alertDialogBuilder.setNegativeButton(android.R.string.cancel, null);
        final AlertDialog alertD = alertDialogBuilder.create();
        alertD.setCanceledOnTouchOutside(false);
        alertD.show();
        Button positiveButton = alertD.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClick) {
                    ProductBean pb = new ProductBean();
                    pb.setProductname(itemsAdapter.getItem(itemsAdapter.getCurrentPosition()).item);
                    double d = Double.parseDouble(itemsAdapter.getItem(itemsAdapter.getCurrentPosition()).price);
                    pb.setProductPrice(d);
                    pb.setProductquantitiy(Double.parseDouble("1"));
                    pb.setTotalprice(Double.parseDouble("1") * d);
                    listData.add(pb);
                    //textView.setVisibility(View.GONE);
                    //cart.setVisibility(View.GONE);

                    list.setVisibility(View.VISIBLE);
                    //mAdapter.refreshAdapter(listData);
                    //mAdapter.refreshAdapter(listData);
                    alertD.dismiss();

                    mAdapter.notifyDataSetChanged();
                    itemsAdapter= null;
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            ((MainActivity)mActivity).runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                      ProductBean pb = new ProductBean();
//                                    pb.setProductname(et_productna.getText().toString());
//                                    double d = Double.parseDouble(et_price.getText().toString() );
//                                    pb.setProductPrice(d);
//                                    pb.setProductquantitiy(Integer.parseInt(et_quantity.getText().toString()));
//                                    pb.setTotalprice(Integer.parseInt(et_quantity.getText().toString()) * d);
//                                    listData.add(pb);
//                                    textView.setVisibility(View.GONE);
//                                    list.setVisibility(View.VISIBLE);
//                                  //  mAdapter.refreshAdapter(listData);
//                                    //mAdapter.refreshAdapter(listData);
//                                    mAdapter.notifyDataSetChanged();
//                                    alertD.dismiss();
//
//                                }
//                            });
//
//                        }
//                    }).start();
                }

        });

        Button negativeButton = alertD.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClick) {
                alertD.dismiss();
                itemsAdapter = null;

            }});
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
       // ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("Client Details");

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }
    public void onBackPressed() {
//        Intent setIntent = new Intent(Intent.ACTION_MAIN);
//        setIntent.addCategory(Intent.CATEGORY_HOME);
//        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(setIntent);
        Toast.makeText(mActivity,"back",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listData.clear();

    }

    public void listVisibiltiy() {
        if (mAdapter == null)
            mAdapter = new ListViewAdapter(mActivity);

        if (mAdapter.getData() != null && mAdapter.getData().size() > 0) {
            list.setVisibility(View.VISIBLE);
        } else {
            list.setVisibility(View.GONE);
        }
    }


    public void updateDatabase(){

        //first create the invoice - lets see
        //get dao obects
        try {

            DbHelper todoOpenDatabaseHelper = ISInvoicingApplication.getDBHelper();
            Dao<Invoice, Long> todoDao = todoOpenDatabaseHelper.getDao();
            Dao<ProductBean, Long> productBeans = todoOpenDatabaseHelper.getProductBeanDao();

            //description was saved in the prefrences so I have to go there and get those
            SharedPreferences preferences = mActivity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            DbShop dbShop = dataSqlite.get(0);
            //if(mInvoice == null) {
                String description = preferences.getString(PreferenceConstants.DESCRIPTION, null);
                if(description!=null){
                    description.replace('\n',' ');
                }
                mInvoice = new Invoice(dbShop.getShopname(), dbShop.getWebsite(), dbShop.getPhonenumber(), dbShop.getAddress(), description, dbShop.getCity(), dbShop.getState(), dbShop.getCountry(),  new Date().getTime());
                DBQuery.createInvoice(todoDao, productBeans, null, mInvoice);

                for (ProductBean obj : listData) {
                    obj.setInvoice(mInvoice);
                    DBQuery.createItem(todoDao, productBeans, obj, mInvoice);
                }
            //}else{
                //update the invoice and not create new one
                //set the invoice number to -1

            //}
        }catch (Exception e){

        }

    }

    public void removeElement(int position) {
        listData.remove(position);
        mAdapter.notifyDataSetChanged();
        listVisibiltiy();
    }


    private void generatePDF(ArrayList<ProductBean> data) {
        pdfFile = new File(mActivity.getExternalFilesDir(filepath), filename);
        Uri uri1 = Uri.fromFile(pdfFile);
        File fdelete = new File(uri1.getPath());
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + uri1.getPath());
            } else {
                System.out.println("file not Deleted :" + uri1.getPath());
            }
        }

        String personName = "rajesh";
        Document document = new Document();

        try {
          //  dataSqlite=dbhelper.getAllData();

            DbShop dbShop=dataSqlite.get(0);
            PdfWriter docWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            PdfContentByte cb = docWriter.getDirectContent();
            initializeFonts();

            InputStream inputStream = mActivity.getAssets().open("sign.jpg");
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image companyLogo = Image.getInstance(stream.toByteArray());
            companyLogo.setAbsolutePosition(25, 700);
            companyLogo.scalePercent(25);
            document.add(companyLogo);

            //creating a sample invoice with some customer data
            createHeadings(cb, 400, 780, dbShop.getShopname());
            createHeadings(cb, 400, 765, dbShop.getCity());
            createHeadings(cb, 400, 750, dbShop.getState());
            createHeadings(cb, 400, 735, dbShop.getPin());
            createHeadings(cb, 400, 720, dbShop.getCountry());

            //list all the products sold to the customer
            float[] columnWidths = {1.5f, 2f, 5f, 2f, 2f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setTotalWidth(500f);



            PdfPCell  cell = new PdfPCell(new Phrase("Item Number"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Item Description"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Qty"));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Price"));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Ext Price"));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            table.setHeaderRows(1);

            DecimalFormat df = new DecimalFormat("0.00");
            for (int i = 0; i < data.size(); i++) {

                ProductBean pb = data.get(i);

                PdfPCell pdfcellquan=new PdfPCell(new Phrase(""+pb.getProductquantitiy()));
                pdfcellquan.setBorder(Rectangle.TOP);
                table.addCell(pdfcellquan);

                PdfPCell pdfcellno=new PdfPCell(new Phrase("ITEM" + String.valueOf(i + 1)));
                pdfcellno.setBorder(Rectangle.TOP);
                table.addCell(pdfcellno);

                PdfPCell pdfcellname=new PdfPCell(new Phrase(pb.getProductname()));
                pdfcellname.setBorder(Rectangle.TOP);
                table.addCell(pdfcellname);

                PdfPCell pdfcellprice=new PdfPCell(new Phrase(""+pb.getProductPrice()));
                pdfcellprice.setBorder(Rectangle.TOP);
                table.addCell(pdfcellprice);

                PdfPCell pdfcelltotal=new PdfPCell(new Phrase(""+(pb.getProductquantitiy()*pb.getProductPrice())));
                pdfcelltotal.setBorder(Rectangle.TOP);
                table.addCell(pdfcelltotal);

            }

            //absolute location to print the PDF table from
            table.writeSelectedRows(0, -1, document.leftMargin(), 650, docWriter.getDirectContent());

            LineSeparator ls = new LineSeparator();
            ls.setLineColor(Color.BLUE);
            ls.setLineWidth(20);
            document.add(new Chunk(ls));
            //print the signature image along with the persons name
            InputStream inputStream1 = mActivity.getAssets().open("sign.jpg");
            bmp = BitmapFactory.decodeStream(inputStream1);
            ByteArrayOutputStream  stream1 = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream1);
            Image signature = Image.getInstance(stream.toByteArray());
            signature.setAbsolutePosition(400f, 150f);
            signature.scalePercent(25f);
            document.add(signature);

            createHeadings(cb, 450, 135, personName);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            dbhelper.closeDB();
            pdfFile = new File(mActivity.getExternalFilesDir(filepath), filename);
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);

//            Intent emailIntent = new Intent(Intent.ACTION_SEND);
//
//
//            emailIntent.setType("text/plain");
//
//
//            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"email@example.com"});
//
//
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
//
//
//            emailIntent.putExtra(Intent.EXTRA_TEXT, "Your shopping bill");
//
//
//            File root = Environment.getExternalStorageDirectory();
//
//
//            String pathToMyAttachedFile="temp/attachement.xml";
//
//
//            pdfFile = new File(mActivity.getExternalFilesDir(filepath), filename);
//
//
//            if (!pdfFile.exists() || !pdfFile.canRead()) {
//
//
//                return;
//
//
//            }
//
//
//            Uri uri = Uri.fromFile(pdfFile);
//
//
//            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
//
//
//            startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
//
//
        }

        //PDF file is now ready to be sent to the bluetooth printer using PrintShare
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setPackage("com.dynamixsoftware.printershare");
//        i.setDataAndType(Uri.fromFile(pdfFile), "application/pdf");
//        startActivity(i);

    }

    private void createHeadings(PdfContentByte cb, float x, float y, String text) {

        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();

    }


    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private void initializeFonts() {
        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDialoMessage1(String message) {
        Toast.makeText(getActivity(),"dd",Toast.LENGTH_SHORT).show();
    }

    class TestAsync extends AsyncTask<ArrayList<ProductBean>, Void, Void>
    {

        protected void onPreExecute (){

            Log.d("PreExceute","On pre Exceute......");
        }

        protected Void doInBackground(ArrayList<ProductBean>...data) {
            ArrayList<ProductBean> data1 = data[0];
            double totalprice=0;
            Log.d("DoInBackGround","On doInBackground...");
            filename = mInvoice.getId() + " " + mInvoice.getShopname() + ".pdf";
            pdfFile = new File(mActivity.getExternalFilesDir(filepath), filename);
            Uri uri1 = Uri.fromFile(pdfFile);
            File fdelete = new File(uri1.getPath());
            if (fdelete.exists()) {
                if (fdelete.delete()) {
                    System.out.println("file Deleted :" + uri1.getPath());
                } else {
                    System.out.println("file not Deleted :" + uri1.getPath());
                }
            }

            String personName = "Your Name";
            Document document = new Document();
            sharedpreferences = mActivity.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String currency=sharedpreferences.getString(PreferenceConstants.CURRENCY,null);

            try {
                DbShop dbShop=dataSqlite.get(0);
                pdfFile = new File(mActivity.getExternalFilesDir(filepath), filename);
                PdfWriter docWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                document.open();

                PdfContentByte cb = docWriter.getDirectContent();
                initializeFonts();

                AssetManager temp = mActivity.getAssets();
                Bitmap bmp =BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.invoice_logo);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image companyLogo = Image.getInstance(stream.toByteArray());
                companyLogo.setAbsolutePosition(25, 735);
                companyLogo.scalePercent(25);
                document.add(companyLogo);


//                String description = sharedpreferences.getString(PreferenceConstants.DESCRIPTION, null).replace('\n', ' ');
                createHeadings(cb, 50, 680, "Job Description : " + mInvoice.getDescription());

                //creating a sample invoice with some customer data
                createHeadings(cb, 400, 795, "Invoice No : " + mInvoice.getId().toString());
                createHeadings(cb, 400, 780, mInvoice.getShopname());
                createHeadings(cb, 400, 765, mInvoice.getAddress());
                createHeadings(cb, 400, 750, mInvoice.getCity());
                createHeadings(cb, 400, 735, mInvoice.getState());


                //list all the products sold to the customer
                float[] columnWidths = {1.5f, 5f, 2f, 2f, 2f};
                //create PDF table with the given widths
                PdfPTable table = new PdfPTable(columnWidths);
                // set table width a percentage of the page width
                table.setTotalWidth(500f);


                PdfPCell  cell = new PdfPCell(new Phrase("Item Number"));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Item Description"));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Qty"));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Price"));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Ext Price"));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                table.setHeaderRows(1);

                DecimalFormat df = new DecimalFormat("0.00");
                for (int i = 0; i <= data1.size(); i++) {

                    if(i <data1.size()) {
                        ProductBean pb = data1.get(i);

                        totalprice=totalprice+(pb.getProductquantitiy() * pb.getProductPrice());

                        PdfPCell pdfcellno = new PdfPCell(new Phrase("ITEM" + String.valueOf(i + 1)));
                        pdfcellno.setBorder(Rectangle.TOP);
                        table.addCell(pdfcellno);

                        PdfPCell pdfcellname = new PdfPCell(new Phrase(pb.getProductname()));
                        pdfcellname.setBorder(Rectangle.TOP);
                        table.addCell(pdfcellname);

                        PdfPCell pdfcellquan = new PdfPCell(new Phrase("" + pb.getProductquantitiy()));
                        pdfcellquan.setBorder(Rectangle.TOP);
                        table.addCell(pdfcellquan);

                        PdfPCell pdfcellprice = new PdfPCell(new Phrase("" + pb.getProductPrice()));
                        pdfcellprice.setBorder(Rectangle.TOP);
                        table.addCell(pdfcellprice);

                        PdfPCell pdfcelltotal = new PdfPCell(new Phrase("" + (pb.getProductquantitiy() * pb.getProductPrice())));
                        pdfcelltotal.setBorder(Rectangle.TOP);
                        table.addCell(pdfcelltotal);
                    }else{


                        PdfPCell pdfcellno = new PdfPCell(new Phrase("Total Price"));
                        pdfcellno.setBorder(Rectangle.TOP);
                        table.addCell(pdfcellno);

                        PdfPCell pdfcellname = new PdfPCell(new Phrase(""));
                        pdfcellname.setBorder(Rectangle.TOP);
                        table.addCell(pdfcellname);

                        PdfPCell pdfcellquan = new PdfPCell(new Phrase(""));
                        pdfcellquan.setBorder(Rectangle.TOP);
                        table.addCell(pdfcellquan);

                        PdfPCell pdfcellprice = new PdfPCell(new Phrase("Total Price"));
                        pdfcellprice.setBorder(Rectangle.TOP);
                        table.addCell(pdfcellprice);

                        PdfPCell pdfcelltotal = new PdfPCell(new Phrase(currency+" "+totalprice ));
                        pdfcelltotal.setBorder(Rectangle.TOP);
                        table.addCell(pdfcelltotal);
                    }

                }


                //absolute location to print the PDF table from
                table.writeSelectedRows(0, -1, document.leftMargin(), 650, docWriter.getDirectContent());

//              LineSeparator ls = new LineSeparator();
//              ls.setLineColor(Color.BLUE);
//              ls.setLineWidth(20);
//              document.add(new Chunk(ls));
                //print the signature image along with the persons name
                InputStream inputStream = mActivity.getAssets().open("sign.jpg");
                bmp = BitmapFactory.decodeStream(inputStream);
                stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image signature = Image.getInstance(stream.toByteArray());
                signature.setAbsolutePosition(400f, 150f);
                signature.scalePercent(25f);
                //document.add(signature);

                Date date = new Date(System.currentTimeMillis());
                //just Date
                DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
                System.out.println(df1.format(date));
                String barcode_data = df1.format(date)+""+counter;
                Log.d("barcode",barcode_data);
                counter++;
                // barcode image
                Bitmap bitmap = null;

                try {

                    bitmap = encodeAsBitmap(barcode_data, BarcodeFormat.CODE_128, 600, 300);
                    stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Image signature1 = Image.getInstance(stream.toByteArray());
                    companyLogo.setAbsolutePosition(25, 700);
                    signature1.scalePercent(25f);
                    //document.add(signature1);


                } catch (WriterException e) {
                    e.printStackTrace();
                }


                //createHeadings(cb, 450, 135, personName);
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
//                pdfFile = new File(mActivity.getExternalFilesDir(filepath), filename);
//                Intent intent=new Intent(Intent.ACTION_VIEW);
//                Uri uri = Uri.fromFile(pdfFile);
//                intent.setDataAndType(uri, "application/pdf");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);


                if(customername.equalsIgnoreCase("print")){
                    pdfFile = new File(mActivity.getExternalFilesDir(filepath), filename);
                    if (!pdfFile.exists() || !pdfFile.canRead()) {
                        Toast.makeText(mActivity,"Not",Toast.LENGTH_SHORT).show();
                        return null;
                    }else{
                        doPrint();
                    }
                }
                else{
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setType("text/plain");

                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {customername});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Invoice");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Thanks for shopping!");
                    File root = Environment.getExternalStorageDirectory();
                    String pathToMyAttachedFile="temp/attachement.xml";
                    pdfFile = new File(mActivity.getExternalFilesDir(filepath), filename);

                    if (!pdfFile.exists() || !pdfFile.canRead()) {
                        return null;
                    }
                    Uri uri = Uri.fromFile(pdfFile);
                    emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
                }
            }
            return null;

        }

        protected void onProgressUpdate(Integer...a){
        }

        protected void onPostExecute(String result) {

        }
    }


    private  void doPrint1(){
        pdfFile = new File(mActivity.getExternalFilesDir(filepath), filename);
        Uri imageUri = Uri.fromFile(pdfFile);

        Intent printIntent = new Intent(mActivity, PrintDialogActivity.class);
        printIntent.setDataAndType(imageUri, "");
        printIntent.putExtra("title", "docTitle");
        startActivity(printIntent);
    }


    private void doPrint() {
        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) mActivity.getSystemService(Context.PRINT_SERVICE);
        String jobName = this.getString(R.string.app_name) + " Document";

        PrintDocumentAdapter pda = new PrintDocumentAdapter(){

            @Override
            public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback){
                InputStream input = null;
                OutputStream output = null;

                try {
                    pdfFile = new File(mActivity.getExternalFilesDir(filepath), filename);

                    input = new FileInputStream(pdfFile);
                    output = new FileOutputStream(destination.getFileDescriptor());

                    byte[] buf = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = input.read(buf)) > 0) {
                        output.write(buf, 0, bytesRead);
                    }

                    callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});

                } catch (FileNotFoundException ee){
                    //Catch exception
                } catch (Exception e) {
                    //Catch exception
                } finally {
                    try {
                        input.close();
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras){

                if (cancellationSignal.isCanceled()) {
                    callback.onLayoutCancelled();
                    return;
                }


                PrintDocumentInfo pdi = new PrintDocumentInfo.Builder("Name of file").setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();

                callback.onLayoutFinished(pdi, true);
            }
        };

        printManager.print(jobName, pda, null);
    }


    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

}

