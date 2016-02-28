package shopbillsample.retail.kloudportal.com.shopbillsample;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.kloudportal.shop.quickbill.R;

import shopbillsample.retail.kloudportal.com.shopbillsample.application.ISInvoicingApplication;
import shopbillsample.retail.kloudportal.com.shopbillsample.database.DbHelper;
import shopbillsample.retail.kloudportal.com.shopbillsample.fragments.OldInvoiceFragment;
import shopbillsample.retail.kloudportal.com.shopbillsample.model.Invoice;
import shopbillsample.retail.kloudportal.com.shopbillsample.model.ProductBean;

public class MainActivity extends ActionBarActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    Context context;
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    Dialog dialog;
    RelativeLayout profileBox;
    private Toolbar mToolbar;
    DataBaseHelper dbhelper;
    List<DbShop> dataSqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Date now = new Date();
        //now.setToNow();

        Date twseven = new Date();
        twseven.setDate(31);
        twseven.setMonth(0);
        twseven.setYear(116);



        if(now.getDay() <= twseven.getDay() && now.getMonth() == twseven.getMonth() && now.getYear() == twseven.getYear()){
            setContentView(R.layout.activity_main_black);
        }
        else {
            setContentView(R.layout.activity_main);

            mToolbar = (Toolbar) findViewById(R.id.toolbar);

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            mNavItems.add(new NavItem("Home", "", R.drawable.ic_action_account_balance));
            mNavItems.add(new NavItem("Client Details", "", R.drawable.ic_image_edit));
            mNavItems.add(new NavItem("Old Invoices", "", R.drawable.ic_maps_local_post_office));

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

            profileBox = (RelativeLayout) findViewById(R.id.profileBox);
            profileBox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    openorclose();
                }
            });

            // Populate the Navigtion Drawer with options
            mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
            mDrawerList = (ListView) findViewById(R.id.navList);
            DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
            mDrawerList.setAdapter(adapter);

            // Drawer Item click listeners
            mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    displayView(position);

                }
            });

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    invalidateOptionsMenu();
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    invalidateOptionsMenu();
                }

            };

            mDrawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    mDrawerToggle.syncState();
                }
            });

            mDrawerLayout.setDrawerListener(mDrawerToggle);

            context = MainActivity.this;
            displayView(0);
        }
    }

    public void openorclose() {


        //final ImageView myImage = (ImageView) findViewById(R.id.rightclick);
        //myImage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate) );
        if (mDrawerLayout == null)
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        //checking whether drawerlayout is opened or not
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            //mDrawerLayout.openDrawer(Gravity.RIGHT);
            mDrawerLayout.closeDrawers();

        } else {
            mDrawerLayout.openDrawer(Gravity.LEFT);

        }
    }


    private void changeFragment(Fragment targetFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContent, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    private void selectItemFromDrawer(int position) {
         android.support.v4.app.Fragment fragment=null;

        if(position ==0)
        fragment = new SwipeContactFragment();
        else if (position ==1 )
            fragment = new ContactFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainContent, fragment);
        fragmentTransaction.commit();


        mDrawerList.setItemChecked(position, true);
        getSupportActionBar().setTitle(mNavItems.get(position).mTitle);


        mDrawerLayout.closeDrawer(mDrawerPane);
    }



    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                dbhelper=new DataBaseHelper(MainActivity.this);
                dataSqlite=dbhelper.getAllData();
                if(dataSqlite !=null && dataSqlite.size() >0){
                    fragment = new BillingListFragment();
                    //startActivity(new Intent(MainActivity.this,SampleActiivyt.class));

                    title = getString(R.string.title_home);
                    dbhelper.closeDB();
                }else {
                    fragment = new HomeFragment();
                    title = getString(R.string.title_home);
                    dbhelper.closeDB();

                }
                break;
            case 1:
                fragment = new EditUserDAta();
                title = getString(R.string.title_friends);
                break;

            case 2:

                try {
                    DbHelper todoOpenDatabaseHelper = ISInvoicingApplication.getDBHelper();
                    Dao<Invoice, Long> todoDao = todoOpenDatabaseHelper.getDao();
                    Dao<ProductBean, Long> productBeans = todoOpenDatabaseHelper.getProductBeanDao();

                    List<Invoice> todos = todoDao.queryForAll();
                    int length = todos.size();

                    fragment = new OldInvoiceFragment(todos);
                    title = getString(R.string.title_messages);
                }catch (Exception e){

                }


                break;
            default:
                break;
        }

        if (fragment != null) {
            // set the toolbar title
            getSupportActionBar().setTitle(title);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.mainContent, fragment);
            fragmentTransaction.commit();


        }
        mDrawerLayout.closeDrawer(mDrawerPane);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    protected void showCustomDialog() {

        dialog = new Dialog(context, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_dialog);

        final ImageView myImage = (ImageView) dialog.findViewById(R.id.loader);
        // myImage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate) );

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x7f000000));

        dialog.show();
    }
}
