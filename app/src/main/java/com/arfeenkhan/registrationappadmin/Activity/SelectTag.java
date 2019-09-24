package com.arfeenkhan.registrationappadmin.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arfeenkhan.registrationappadmin.Adapter.SelectTagAdapter;
import com.arfeenkhan.registrationappadmin.Model.SelectTagModel;
import com.arfeenkhan.registrationappadmin.Model.SessionNameModel;
import com.arfeenkhan.registrationappadmin.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectTag extends AppCompatActivity {

    RecyclerView tagRecycler;
    ArrayList<SelectTagModel> taglist;
    SelectTagAdapter tagAdapter;

    String data_url = "http://167.71.229.74/barcodescanner/tagdata.php";

    StringRequest request;
    ProgressDialog progressDialog;

    String place;

    SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<SessionNameModel> sessionlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tag);

        taglist = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        place = getIntent().getExtras().get("place").toString();
        getData();

//        getJsonData();

        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        tagRecycler = findViewById(R.id.select_tags);
        tagAdapter = new SelectTagAdapter(this, taglist);
//        tagAdapter = new SelectTagAdapter(this, taglist, sessionlist);


        LinearLayoutManager llm = new LinearLayoutManager(this);
        tagRecycler.setLayoutManager(llm);
        tagRecycler.setAdapter(tagAdapter);
        tagRecycler.setHasFixedSize(true);
        tagAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
//                getJsonData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

//    private void getJsonData() {
//        progressDialog.setMessage("Please wait...");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//        taglist.clear();
//        String url = "http://167.71.229.74/barcodescanner/innerjoin.php";
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String question = jsonObject.getString("question");
//                    JSONArray array = new JSONArray(question);
//                    for (int i = 0; i < array.length(); i++) {
//                        JSONObject jsonObject1 = array.getJSONObject(i);
//                        String date = jsonObject1.getString("date");
//                        String tag = jsonObject1.getString("tagno");
//                        String place = jsonObject1.getString("place");
//                        String name = jsonObject1.getString("name");
//                        String ctf = jsonObject1.getString("ctf");
//                        String time = jsonObject1.getString("time");
//                        String tf = jsonObject1.getString("TF");
//                        JSONArray jsonArray1 = jsonObject1.getJSONArray("session_name");
//                        for (int j = 0; j < jsonArray1.length(); j++) {
//                            JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
//                            String sessionname = jsonObject2.getString("name");
//                            SessionNameModel sessionNameModel = new SessionNameModel("deleteId", sessionname);
//                            sessionlist.add(sessionNameModel);
//                        }
//
//                        SelectTagModel stm = new SelectTagModel(name, place, tag, time, ctf, date, tf,sessionlist);
//                        taglist.add(stm);
//                        progressDialog.dismiss();
//                        tagAdapter.notifyDataSetChanged();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> param = new HashMap<>();
//                param.put("place", place);
//                return param;
//            }
//        };
//
////        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
////            @Override
////            public void onResponse(JSONObject response) {
////                try {
////                    JSONArray jsonArray = response.getJSONArray("question");
////                    for (int i = 0; i < jsonArray.length(); i++) {
////                        JSONObject data = jsonArray.getJSONObject(i);
////                        String tag = data.getString("tagno");
////                        String place = data.getString("place");
////                        String name = data.getString("name");
////                        String ctf = data.getString("ctf");
////                        String time = data.getString("time");
////                        String date = data.getString("date");
////                        String tf = data.getString("tf");
////
////                        JSONArray jsonArray1 = data.getJSONArray("session_name");
////                        for (int j = 0; j < jsonArray1.length(); j++) {
////                            JSONObject jsonObject = jsonArray1.getJSONObject(j);
////                            String sessionname = jsonObject.getString("name");
////                            SessionNameModel sessionNameModel = new SessionNameModel("", sessionname);
////                            sessionlist.add(sessionNameModel);
////                        }
////
////                        SelectTagModel stm = new SelectTagModel(name, place, tag, time, ctf, date, tf);
////                        taglist.add(stm);
////                        progressDialog.dismiss();
////
////                    }
////                    tagAdapter.notifyDataSetChanged();
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                error.printStackTrace();
////            }
////        }) {
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String, String> params = new HashMap<>();
////                params.put("place", place);
////                return params;
////
////            }
////        };
//        queue.add(sr);
//    }

    private void getData() {
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        taglist.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, data_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    JSONObject c = null;
                    for (int i = 0; i < arr.length(); i++) {
                        c = arr.getJSONObject(i);
                        String tag = c.getString("tagno");
                        String place = c.getString("place");
                        String name = c.getString("name");
                        String ctf = c.getString("ctf");
                        String time = c.getString("time");
                        String date = c.getString("date");
                        String tf = c.getString("tf");
                        String sessionname = c.getString("ss_name");
                        SelectTagModel stm = new SelectTagModel(name, place, tag, time, ctf, date, tf,sessionname);
                        taglist.add(stm);
                        progressDialog.dismiss();
                        tagAdapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("place", place);
                return params;

            }
        };


        queue.add(sr);

    }
}
