package shopbillsample.retail.kloudportal.com.shopbillsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kloudportal.shop.quickbill.R;


public class ReachUs extends Fragment {
    TextView telephone,email,buylink;

    public ReachUs() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.reachus, container, false);
        email=(TextView)v.findViewById(R.id.emailid);
        telephone=(TextView)v.findViewById(R.id.telephone);
        buylink=(TextView)v.findViewById(R.id.buylink);

        email.setText("support@kloudportal.com");
        telephone.setText("https://www.kloudportal.com");

        String htmlString1="Buy now and Monetize the App";

        //buylink.setText(Html.fromHtml(htmlString1));
        SpannableString content = new SpannableString(htmlString1);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        buylink.setText(content);

        buylink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://codecanyon.net/user/kolluru81/portfolio?ref=kolluru81")));

            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("ReachUs");
    }
}
