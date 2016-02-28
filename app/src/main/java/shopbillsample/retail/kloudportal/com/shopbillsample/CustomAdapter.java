package shopbillsample.retail.kloudportal.com.shopbillsample;

import android.content.Context;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kloudportal.shop.quickbill.R;

import java.util.List;

import shopbillsample.retail.kloudportal.com.shopbillsample.model.ProductBean;

public class CustomAdapter extends BaseAdapter{
    Context context;
    private static LayoutInflater inflater=null;
    List<ProductBean> data;
    public CustomAdapter(Context mainActivity, List<ProductBean> data) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(data.get(position).getProductname());
        holder.img.setImageResource(R.drawable.ic_launcher);
        View overflow = rowView.findViewById(R.id.album_overflow);
        overflow.setOnClickListener(new OnAlbumOverflowSelectedListener(context));

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+data.get(position).getProductname(), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }



}
 class OnAlbumOverflowSelectedListener implements OnClickListener {
    private Context mContext;

    public OnAlbumOverflowSelectedListener(Context context) {
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        // This is an android.support.v7.widget.PopupMenu;
        PopupMenu popupMenu = new PopupMenu(mContext, v) {
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.album_overflow_delete:
                       // deleteAlbum(mAlbum);
                        return true;

                    case R.id.album_overflow_rename:
                        return true;

                    case R.id.album_overflow_lock:
                        return true;

                    case R.id.album_overflow_unlock:
                        return true;

                    case R.id.album_overflow_set_cover:
                        return true;

                    default:
                         return super.onMenuItemSelected(menu, item);
                }
            }
        };

        popupMenu.inflate(R.menu.main);



        popupMenu.show();
    }
}