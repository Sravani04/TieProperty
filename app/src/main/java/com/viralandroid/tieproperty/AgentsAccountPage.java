package com.viralandroid.tieproperty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by T on 12-05-2017.
 */

public class AgentsAccountPage extends Activity {
    ImageView back_btn,edit_profile;
    TextView logout_btn;
    String agent_id;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agents_acount);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        logout_btn = (TextView) findViewById(R.id.logout_btn);
        edit_profile = (ImageView) findViewById(R.id.edit_profile);
        if (getIntent()!=null && getIntent().hasExtra("agentId")){
            agent_id = getIntent().getStringExtra("agentId");
        }
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgentsAccountPage.this,AgentsEditProfile.class);
                intent.putExtra("agentsId",agent_id);
                startActivity(intent);
            }
        });


        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.SetUserId(AgentsAccountPage.this,"-1");
            }
        });


    }
}
