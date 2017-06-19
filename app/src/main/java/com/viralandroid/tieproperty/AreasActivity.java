//package com.viralandroid.tieproperty;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.google.gson.JsonArray;
//import com.koushikdutta.async.future.FutureCallback;
//import com.koushikdutta.ion.Ion;
//
//import java.util.ArrayList;
//
///**
// * Created by T on 28-04-2017.
// */
//
//public class AreasActivity extends Activity {
//    private EditText search_area;
//    private ListView listView;
//    private AreasListAdapter adapter;
//    private String[] moviewList;
//    ArrayList<Areas> areasfrom_api;
//    ArrayList<Areas> array_sort;
//    int textlength = 0;
//    String city_name,city_id,category_id;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.areas);
//
//        // Generate sample data
//
//        moviewList = new String[]{"Xmen", "Titanic", "Captain America",
//                "Iron man", "Rocky", "Transporter", "Lord of the rings", "The jungle book",
//                "Tarzan","Cars","Shreck"};
//
//        listView = (ListView) findViewById(R.id.areas_list);
//
//        if (getIntent()!=null && getIntent().hasExtra("city")){
//            city_name = getIntent().getStringExtra("city");
//            city_id = getIntent().getStringExtra("id");
//            category_id = getIntent().getStringExtra("cat_id");
//        }
//
//
//        areasfrom_api  = new ArrayList<>();
//        array_sort = new ArrayList<>();
//
//
//        adapter = new AreasListAdapter(this,areasfrom_api);
//        listView.setAdapter(adapter);
//
////        if (adapter.getCount()>0)
////            listView.setVisibility(View.VISIBLE);
////        else
////            listView.setVisibility(View.INVISIBLE);
//
//
//
//        search_area = (EditText) findViewById(R.id.search_area);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(AreasActivity.this,MainActivity.class);
//                intent.putExtra("city",city_name);
//                intent.putExtra("id",city_id);
//                intent.putExtra("cat_id",category_id);
//                intent.putExtra("area_id",areasfrom_api.get(position).id);
//                startActivity(intent);
//                Toast.makeText(AreasActivity.this, array_sort.get(position).title, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        search_area.addTextChangedListener(new TextWatcher() {
//
//
//                public void afterTextChanged(Editable s) {
//                }
//
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                }
//
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    textlength = search_area.getText().length();
//                    array_sort.clear();
//                    for (int i = 0; i < areasfrom_api.size(); i++) {
//                        if (textlength <= areasfrom_api.get(i).title.length()) {
//                            Log.d("ertyyy", areasfrom_api.get(i).title.toLowerCase().trim());
//                            if (areasfrom_api.get(i).title.toLowerCase().trim().contains(
//                                    search_area.getText().toString().toLowerCase().trim())) {
//                                array_sort.add(areasfrom_api.get(i));
//
//                            }
//                        }
//                    }
//                    adapter = new AreasListAdapter(AreasActivity.this, array_sort);
//                    listView.setAdapter(adapter);
//
//                }
//            });
//
//
//
//        get_areas();
//
//    }
//
//
//    public void get_areas(){
//        Ion.with(this)
//                .load(Session.SERVER_URL+"areas.php")
//                .setBodyParameter("city",city_id)
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//                        try {
//                            for (int i = 0; i < result.size(); i++) {
//                                Areas areas = new Areas(result.get(i).getAsJsonObject(), AreasActivity.this);
//                                areasfrom_api.add(areas);
//                                array_sort.add(areas);
//                            }
//                            adapter.notifyDataSetChanged();
//                        }catch (Exception e1){
//                            e1.printStackTrace();
//                        }
//                    }
//                });
//    }
//}
