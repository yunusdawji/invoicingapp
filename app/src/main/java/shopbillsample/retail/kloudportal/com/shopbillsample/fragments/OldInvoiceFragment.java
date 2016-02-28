package shopbillsample.retail.kloudportal.com.shopbillsample.fragments;

/**
 * Created by yunusdawji on 16-01-10.
 */
import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.kloudportal.shop.quickbill.R;

import java.util.ArrayList;
import java.util.List;

import shopbillsample.retail.kloudportal.com.shopbillsample.BillingListFragment;
import shopbillsample.retail.kloudportal.com.shopbillsample.MainActivity;
import shopbillsample.retail.kloudportal.com.shopbillsample.adapter.OldInvoiceListAdapter;
import shopbillsample.retail.kloudportal.com.shopbillsample.model.Invoice;
import shopbillsample.retail.kloudportal.com.shopbillsample.preferences.PreferenceConstants;

public class OldInvoiceFragment extends Fragment implements OnItemClickListener {

    private List<Invoice> mInvoices;
    private OldInvoiceListAdapter mAdapter;
    private ListView mListView;

    public OldInvoiceFragment(){
        super();
        mInvoices = new ArrayList<Invoice>();
    }

    public OldInvoiceFragment(List<Invoice> invoices){
        super();
        mInvoices = invoices;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_old_records, container, false);

        mListView = (ListView) view.findViewById(R.id.list);


        mListView.setOnItemClickListener(this);

        mAdapter = new OldInvoiceListAdapter(getActivity(), mInvoices);
        ((AdapterView) mListView).setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Planets, android.R.layout.simple_list_item_1);
        //setListAdapter(adapter);
        //mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

        //Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();

        //we open this guy
        Invoice invoice = mInvoices.get(position);

        FragmentManager fragmentManager = ((MainActivity)getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainContent, new BillingListFragment(invoice));
        fragmentTransaction.commit();


    }
}