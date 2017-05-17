//package com.viralandroid.tieproperty;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.ListView;
//
//import java.util.ArrayList;
//
///**
// * Created by T on 17-05-2017.
// */
//
//public class VisitsPage extends Activity {
//    ImageView close_btn;
//    ListView listView;
//    VisitsAdapter visitsAdapter;
//    ArrayList<Commissions> commissions;
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.site_visit_popup_list);
//        close_btn = (ImageView) findViewById(R.id.close_btn);
//        listView = (ListView)findViewById(R.id.visits_list);
//        close_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        commissions = new ArrayList<>();
//
//        if (getIntent()!=null && getIntent().hasExtra("commissions")){
//            commissions = (ArrayList<Commissions>) getIntent().getSerializableExtra("commissions");
//        }
//
//        visitsAdapter = new VisitsAdapter(this,commissions);
//        listView.setAdapter(visitsAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
//
//    }
//}
