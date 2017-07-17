//package com.viralandroid.tieproperty;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.koushikdutta.ion.Ion;
//
///**
// * Created by T on 17-05-2017.
// */
//
//public class AgentsAccountPage extends Activity {
//    ImageView back_btn,agent_image;
//    TextView edit_btn,agent_name;
//    String name,image;
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.agents_account_page);
//        edit_btn = (TextView) findViewById(R.id.edit_btn);
//        agent_name = (TextView) findViewById(R.id.agent_name);
//        back_btn = (ImageView) findViewById(R.id.back_btn);
//        agent_image = (ImageView) findViewById(R.id.agent_image);
//
//        if (getIntent()!=null && getIntent().hasExtra("agent_name")){
//            name = getIntent().getStringExtra("agent_name");
//            image = getIntent().getStringExtra("agent_image");
//        }
//
//        agent_name.setText(name);
//        Ion.with(AgentsAccountPage.this).load(image).intoImageView(agent_image);
//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AgentsAccountPage.this.onBackPressed();
//            }
//        });
//        edit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),AgentsEditProfile.class);
//                startActivity(intent);
//            }
//        });
//
//    }
//}
