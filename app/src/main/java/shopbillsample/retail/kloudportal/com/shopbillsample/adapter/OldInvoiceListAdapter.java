package shopbillsample.retail.kloudportal.com.shopbillsample.adapter;

/**
 * Created by yunusdawji on 16-01-10.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.app.Activity;

import com.kloudportal.shop.quickbill.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import shopbillsample.retail.kloudportal.com.shopbillsample.model.Invoice;

public class OldInvoiceListAdapter extends ArrayAdapter {

    private Context context;
    private boolean useList = true;

    public OldInvoiceListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    /**
     * Holder for the list items.
     */
    private class ViewHolder{
        TextView titleText;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Invoice item = (Invoice)getItem(position);
        View viewToUse = null;

        // This block exists to inflate the settings list item conditionally based on whether
        // we want to support a grid or list view.
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            if(useList){
                viewToUse = mInflater.inflate(R.layout.layout_old_invoice_row, null);
            } else {
                viewToUse = mInflater.inflate(R.layout.layout_old_invoice_row, null);
            }

            holder = new ViewHolder();
            holder.titleText = (TextView)viewToUse.findViewById(R.id.row_name);

            viewToUse.setTag(holder);
        } else {
            viewToUse = convertView;
            holder = (ViewHolder) viewToUse.getTag();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String date = sdf.format(new Date(item.getDateCreated()));

        ((TextView) viewToUse.findViewById(R.id.row_number)).setText(item.getId().toString());
        ((TextView)viewToUse.findViewById(R.id.row_date)).setText(date);
        holder.titleText.setText(item.getShopname());
        return viewToUse;
    }
}
