package shopbillsample.retail.kloudportal.com.shopbillsample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kloudportal.shop.quickbill.R;

import java.util.ArrayList;
import java.util.List;

import shopbillsample.retail.kloudportal.com.shopbillsample.model.Items;

public class MyItemSpinnerAdapter extends BaseAdapter {
	private List<Items.Item> mItems = new ArrayList<>();
    private Context mContext;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    private int currentPosition;

    public MyItemSpinnerAdapter(Context context){
        mContext = context;
    }

	public void clear() {
		mItems.clear();
	}

	public void addItem(Items.Item yourObject) {
		mItems.add(yourObject);
	}

	public void addItems(List<Items.Item> yourObjectList) {
		mItems.addAll(yourObjectList);
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Items.Item getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getDropDownView(int position, View view, ViewGroup parent) {
		if (view == null || !view.getTag().toString().equals("DROPDOWN")) {
			view = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toolbar_spinner_item_dropdown, parent, false);
			view.setTag("DROPDOWN");
		}

		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		textView.setText(getTitle(position).item);

		return view;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null || !view.getTag().toString().equals("NON_DROPDOWN")) {
			view = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.
					toolbar_spinner_item_actionbar, parent, false);
			view.setTag("NON_DROPDOWN");
		}
		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		textView.setText(getTitle(position).item);
		return view;
	}


	private Items.Item getTitle(int position) {
		return position >= 0 && position < mItems.size() ? mItems.get(position): null;
	}
}