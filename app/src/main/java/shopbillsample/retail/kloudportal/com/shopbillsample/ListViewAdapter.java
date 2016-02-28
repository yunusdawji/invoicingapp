package shopbillsample.retail.kloudportal.com.shopbillsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import com.kloudportal.shop.quickbill.R;

import shopbillsample.retail.kloudportal.com.shopbillsample.model.ProductBean;


public class ListViewAdapter extends BaseSwipeAdapter {

    private Context mContext;
    ListView mListView;
    ArrayList<ProductBean> listData;
    BillingListFragment fragment;
    SharedPreferences sharedpreferences;

    public ListViewAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public ListViewAdapter(Context mContext, ListView mListView, List<ProductBean> listData, BillingListFragment fragment) {
        this.fragment = fragment;
        this.mContext = mContext;
        this.mListView = mListView;
        this.listData = (ArrayList<ProductBean>)listData;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public void refreshAdapter(ArrayList<ProductBean> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @Override
    public View generateView(int position, ViewGroup parent) {

        final int posi = position;
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
              //  YoYo.with(Techniques.FadeInRight).duration(0).delay(0).playOn(layout.findViewById(R.id.trash));

            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                //Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();

            }
        });

        ImageView trash = (ImageView) v.findViewById(R.id.trash);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SwipeLayout) (mListView.getChildAt(posi - mListView.getFirstVisiblePosition()))).close(true);


            }
        });

        Button delete = (Button) v.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment.removeElement(posi);
            //    ((SwipeLayout) (mListView.getChildAt(posi - mListView.getFirstVisiblePosition()))).close(true);

            }
        });
        ImageView im = (ImageView) v.findViewById(R.id.imageView);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((SwipeLayout) (mListView.getChildAt(posi - mListView.getFirstVisiblePosition()))).open(true);

            }
        });

        RobotoBoldTextView txtTitle = (RobotoBoldTextView) v.findViewById(R.id.txt);
        ImageView imageView = (ImageView) v.findViewById(R.id.img);
        TextView rate = (TextView) v.findViewById(R.id.rate);
        RobotoRegularTextView quantity = (RobotoRegularTextView) v.findViewById(R.id.quantity);
        sharedpreferences = mContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        ProductBean l = listData.get(position);
        txtTitle.setText("#" + l.getProductname());
        imageView.setImageResource(R.drawable.product);

        String currency=sharedpreferences.getString("currency",null);
        rate.setText(currency+ " " + l.getTotalprice());
        quantity.setText("#" + l.getProductquantitiy()+ " ("+l.getProductPrice()+")" );

        fillValues(position, v);

        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView t = (TextView) convertView.findViewById(R.id.position);
        t.setVisibility(View.INVISIBLE);
        t.setText((position + 1) + ".");
    }

    @Override
    public int getCount() {

        return listData.size();
    }

    public ArrayList<ProductBean> getData() {

        return listData;
    }

    @Override
    public ProductBean getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
