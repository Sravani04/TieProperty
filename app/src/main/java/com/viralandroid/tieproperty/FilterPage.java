package com.viralandroid.tieproperty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 01-05-2017.
 */

public class FilterPage extends FragmentActivity {
    TextView areas,category_type;
    ViewFlipper filter_frame;
    TextView range_seekbar,reset_btn,apply_btn;
    String cityid,cities,phone,from,to;
    ImageView close_btn;
    LinearLayout localities,types,ranges;
    ArrayList<Areas> areasfrom_api;
    ArrayList<Category> categoriesfrom_api;
    ArrayList<Areas> array_sort;
    ArrayList<Category> categories_sort;
    CategoryPageAdapter categoryPageAdapter;
    AreaFragmentAdapter areaFragmentAdapter;
    ListView localities_list;
    ListView category_list;
    EditText search,search_type;
    int textlength = 0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters_page);
        FilterPage.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        areas = (TextView) findViewById(R.id.areas);
        category_type = (TextView) findViewById(R.id.category_type);
        filter_frame = (ViewFlipper) findViewById(R.id.filter_frame);
        range_seekbar = (TextView) findViewById(R.id.range_seekbar);
        close_btn = (ImageView) findViewById(R.id.close_btn);
        reset_btn = (TextView) findViewById(R.id.reset_btn);
        apply_btn = (TextView) findViewById(R.id.apply_btn);
        localities = (LinearLayout) findViewById(R.id.localities);
        ranges = (LinearLayout) findViewById(R.id.ranges);
        types = (LinearLayout) findViewById(R.id.types);
        localities_list = (ListView) findViewById(R.id.localities_list);
        search = (EditText) findViewById(R.id.search);
        category_list = (ListView) findViewById(R.id.category_list);
        search_type = (EditText) findViewById(R.id.search_type);

//Areas
        areasfrom_api = new ArrayList<>();
        array_sort = new ArrayList<>();

        areaFragmentAdapter = new AreaFragmentAdapter(this,areasfrom_api);
        localities_list.setAdapter(areaFragmentAdapter);

        localities_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        search.addTextChangedListener(new TextWatcher() {


            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                textlength = search.getText().length();
                array_sort.clear();
                for (int i = 0; i < areasfrom_api.size(); i++) {
                    if (textlength <= areasfrom_api.get(i).title.length()) {
                        Log.d("ertyyy", areasfrom_api.get(i).title.toLowerCase().trim());
                        if (areasfrom_api.get(i).title.toLowerCase().trim().contains(
                                search.getText().toString().toLowerCase().trim())) {
                            array_sort.add(areasfrom_api.get(i));

                        }
                    }
                }
                areaFragmentAdapter = new AreaFragmentAdapter(FilterPage.this, array_sort);
                localities_list.setAdapter(areaFragmentAdapter);

            }
        });


        //Categories

        categoriesfrom_api = new ArrayList<>();
        categories_sort = new ArrayList<>();

        categoryPageAdapter = new CategoryPageAdapter(this,categoriesfrom_api);
        category_list.setAdapter(categoryPageAdapter);
        category_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        search_type.addTextChangedListener(new TextWatcher() {


            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                textlength = search_type.getText().length();
                categories_sort.clear();
                for (int i = 0; i < categoriesfrom_api.size(); i++) {
                    if (textlength <= categoriesfrom_api.get(i).title.length()) {
                        Log.d("ertyyy", categoriesfrom_api.get(i).title.toLowerCase().trim());
                        if (categoriesfrom_api.get(i).title.toLowerCase().trim().contains(
                                search_type.getText().toString().toLowerCase().trim())) {
                            categories_sort.add(categoriesfrom_api.get(i));

                        }
                    }
                }
                categoryPageAdapter = new CategoryPageAdapter(FilterPage.this, categories_sort);
                category_list.setAdapter(categoryPageAdapter);

            }
        });




        if (getIntent()!=null && getIntent().hasExtra("id")){
            cityid = getIntent().getStringExtra("id");


        }


        areas.setBackgroundColor(Color.parseColor("#ffffff"));
        category_type.setBackgroundColor(Color.parseColor("#d3d3d3"));
        range_seekbar.setBackgroundColor(Color.parseColor("#d3d3d3"));
        filter_frame.setDisplayedChild(0);

        areas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                areas.setBackgroundColor(Color.parseColor("#ffffff"));
                category_type.setBackgroundColor(Color.parseColor("#d3d3d3"));
                range_seekbar.setBackgroundColor(Color.parseColor("#d3d3d3"));
                filter_frame.setDisplayedChild(0);
            }
        });

        category_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category_type.setBackgroundColor(Color.parseColor("#ffffff"));
                areas.setBackgroundColor(Color.parseColor("#d3d3d3"));
                range_seekbar.setBackgroundColor(Color.parseColor("#d3d3d3"));
                filter_frame.setDisplayedChild(1);
            }
        });

        range_seekbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                range_seekbar.setBackgroundColor(Color.parseColor("#ffffff"));
                areas.setBackgroundColor(Color.parseColor("#d3d3d3"));
                category_type.setBackgroundColor(Color.parseColor("#d3d3d3"));
                filter_frame.setDisplayedChild(2);
            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0;i<areasfrom_api.size();i++){
                    areasfrom_api.get(i).setChecked(false);
                }

                for (int i=0;i<categoriesfrom_api.size();i++){
                    categoriesfrom_api.get(i).setChecked(false);
                }

                areaFragmentAdapter.notifyDataSetChanged();
                categoryPageAdapter.notifyDataSetChanged();
            }
        });

        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.rangeSeekbar1);
        final TextView tvMin = (TextView) findViewById(R.id.textMin1);
        final TextView tvMax = (TextView) findViewById(R.id.textMax1);

        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue));
                tvMax.setText(String.valueOf(maxValue));

            }
        });

        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
//                value = String.valueOf(minValue) + " : " + String.valueOf(maxValue);
                from = String.valueOf(minValue);
                Log.e("range",from);
                to   = String.valueOf(maxValue);
                Log.e("rangeto",to);
            }
        });



        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String areas_filter,category_filter,from,to;
                areas_filter = "";
                category_filter = "";
                from = "";
                to ="";

                for (int i=0;i<areaFragmentAdapter.areas.size();i++){
                    if (areaFragmentAdapter.areas.get(i).checked){
                        Log.e("checked",areaFragmentAdapter.areas.get(i).id);
                        if (areas_filter.equals("")) {
                            areas_filter = areaFragmentAdapter.areas.get(i).id;
                        }else {
                            areas_filter = areas_filter +"@"+ areaFragmentAdapter.areas.get(i).id;
                        }
                    }
                }

                for (int i=0;i<categoriesfrom_api.size();i++){
                    if (categoriesfrom_api.get(i).checked){
                        Log.e("category_type",categoriesfrom_api.get(i).id);
                        if (category_filter.equals("")) {
                            category_filter = categoriesfrom_api.get(i).id;
                        }else {
                            category_filter = category_filter +"@"+ categoriesfrom_api.get(i).id;
                        }

                    }
                }



                //Toast.makeText(FilterPage.this,"Filters added",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FilterPage.this,MainActivity.class);
                intent.putExtra("area_id",areas_filter);
                intent.putExtra("cat_id",category_filter);
                intent.putExtra("range_from",from);
                intent.putExtra("range_to",to);
                Log.e("areaId",areas_filter);
                intent.putExtra("id",cityid);

                startActivity(intent);
            }
        });



        get_areas();
        get_categories();

    }

    public void get_areas(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"areas.php")
                .setBodyParameter("city",cityid)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        try {
                            for (int i = 0; i < result.size(); i++) {
                                Areas areas = new Areas(result.get(i).getAsJsonObject(), FilterPage.this,false);
                                areasfrom_api.add(areas);
                                array_sort.add(areas);
                            }
                            areaFragmentAdapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
    }

    public void get_categories(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"category.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        try {
                            for (int i=0;i<result.size();i++){
                                Category category = new Category(result.get(i).getAsJsonObject(),FilterPage.this,false);
                                categoriesfrom_api.add(category);
                                categories_sort.add(category);
                            }
                            categoryPageAdapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }



}
