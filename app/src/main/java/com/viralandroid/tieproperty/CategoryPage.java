//package com.viralandroid.tieproperty;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.GridView;
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
//public class CategoryPage extends Activity {
//    GridView gridView;
//    CategoryPageAdapter categoryPageAdapter;
//    ArrayList<Category> categoriesfrom_api;
//    String city_name,city_id,mobile;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.category_list);
//        categoriesfrom_api = new ArrayList<>();
//        gridView = (GridView) findViewById(R.id.category_list);
//
//        if (getIntent()!=null && getIntent().hasExtra("city")){
//            city_name = getIntent().getStringExtra("city");
//            city_id = getIntent().getStringExtra("id");
//            mobile = getIntent().getStringExtra("phone");
//
//
//        }
//        categoryPageAdapter = new CategoryPageAdapter(this,categoriesfrom_api);
//        gridView.setAdapter(categoryPageAdapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(CategoryPage.this,AreasActivity.class);
//                intent.putExtra("city",city_name);
//                intent.putExtra("id",city_id);
//                intent.putExtra("cat_id",categoriesfrom_api.get(i).id);
//                startActivity(intent);
//            }
//        });
//
//        get_categories();
//
//    }
//
//    public void get_categories(){
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("please wait..");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        Ion.with(this)
//                .load(Session.SERVER_URL+"category.php")
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//                        if (progressDialog!=null)
//                            progressDialog.dismiss();
//                        try {
//                            for (int i=0;i<result.size();i++){
//                                Category category = new Category(result.get(i).getAsJsonObject(),CategoryPage.this);
//                                categoriesfrom_api.add(category);
//                            }
//
//                            categoryPageAdapter.notifyDataSetChanged();
//                        }catch (Exception e1){
//                            e1.printStackTrace();
//                        }
//
//                    }
//                });
//    }
//
//
//}
