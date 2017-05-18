package com.viralandroid.tieproperty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by T on 17-05-2017.
 */

public class CommissionsPage  extends Activity{
    ImageView back_btn;
    TextView comm_list,site_visits_list,payouts_list;
    TextView tot_comm_amt,tds_amt,pay_amt,site_visits,ded_visit,tot_amt,paid_amt,bal_amt;
    String comm_amt,tds,pay,site,ded,tot,paid,bal;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commissions_page);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        comm_list = (TextView) findViewById(R.id.comm_list);
        site_visits_list = (TextView) findViewById(R.id.site_visit_lists);
        payouts_list = (TextView) findViewById(R.id.payouts_list);
        tot_comm_amt = (TextView) findViewById(R.id.tot_comm_amt);
        tds_amt = (TextView) findViewById(R.id.tds_amt);
        pay_amt = (TextView) findViewById(R.id.pay_amt);
        site_visits = (TextView) findViewById(R.id.site_visits);
        ded_visit = (TextView) findViewById(R.id.ded_visit);
        tot_amt = (TextView) findViewById(R.id.tot_amt);
        paid_amt = (TextView) findViewById(R.id.paid_amt);
        bal_amt = (TextView) findViewById(R.id.bal_amt);

        if (getIntent()!=null && getIntent().hasExtra("tot_amt")){
            comm_amt = getIntent().getStringExtra("tot_amt");
            tds = getIntent().getStringExtra("tds_amt");
            pay = getIntent().getStringExtra("pay_amt");
            site = getIntent().getStringExtra("site_visit");
            ded = getIntent().getStringExtra("ded_visit");
            tot = getIntent().getStringExtra("total_amt");
            paid = getIntent().getStringExtra("paid_amt");
            bal = getIntent().getStringExtra("bal_amt");
        }

        tot_comm_amt.setText(comm_amt);
        tds_amt.setText(tds);
        pay_amt.setText(pay);
        site_visits.setText(site);
        ded_visit.setText(ded);
        tot_amt.setText(tot);
        paid_amt.setText(paid);
        bal_amt.setText(bal);


        comm_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommissionsPage.this,CommissionList.class);
                startActivity(intent);

            }
        });

        site_visits_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommissionsPage.this,SiteVisitScreen.class);
                startActivity(intent);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommissionsPage.this.onBackPressed();
            }
        });


    }
}
