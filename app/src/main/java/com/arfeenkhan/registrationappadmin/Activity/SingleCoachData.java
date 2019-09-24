package com.arfeenkhan.registrationappadmin.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arfeenkhan.registrationappadmin.Adapter.SingleCoachDataAdapter;
import com.arfeenkhan.registrationappadmin.Model.SignleCoachDataModel;
import com.arfeenkhan.registrationappadmin.R;
import com.arfeenkhan.registrationappadmin.util.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingleCoachData extends AppCompatActivity {

    //    private RecyclerView singleCoachData;
    private TextView textView;
    private String singleCoachDataUrl = "http://167.71.229.74/barcodescanner/singleuserdata.php";
    String deleteEventPeople = "http://167.71.229.74/barcodescanner/deleteEventPeople.php";
    private SingleCoachDataAdapter adapter;
    private ArrayList<SignleCoachDataModel> list = new ArrayList<>();
    ProgressDialog mdialog;

    int total;

    ArrayList<String> listofpeople = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;
    GridView SingleDataView;

    String deleteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_coach_data);

        mdialog = new ProgressDialog(this);
        getData();

        textView = findViewById(R.id.total_no_person);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        SingleDataView = findViewById(R.id.single_coach_recycler_data);
        adapter = new SingleCoachDataAdapter(list, this);
        SingleDataView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        SingleDataView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SingleCoachData.this);
                builder.setTitle("Delete");
                builder.setMessage("Do you want to Delete");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteId = list.get(position).getId();
                        delete();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });

    }

    private void delete() {
        RequestQueue queue = Volley.newRequestQueue(SingleCoachData.this);
        StringRequest sr = new StringRequest(Request.Method.POST, deleteEventPeople, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arr = new JSONObject(response);
                    for (int i = 0; i < arr.length(); i++) {
                        String message = arr.getString("tag");
                        Toast.makeText(SingleCoachData.this, message, Toast.LENGTH_SHORT).show();
                        getData();
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", deleteId);
                return params;
            }
        };

        queue.add(sr);
    }

    private void getData() {
        mdialog.setMessage("Please wait...");
        mdialog.setCanceledOnTouchOutside(false);
        mdialog.show();
        list.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, singleCoachDataUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject c = array.getJSONObject(i);
                        String id = c.getString("id");
                        String uqniceno = c.getString("uqniceno");
                        String name = c.getString("name");
                        String phone = c.getString("phone");
                        String dt = c.getString("dt");
                        String tm = c.getString("tm");
                        String coachname = c.getString("coachname");

                        SignleCoachDataModel signleCoachDataModel = new SignleCoachDataModel(id, coachname, name, uqniceno, dt, tm, phone);
                        list.add(signleCoachDataModel);

                        total = list.size();

                        textView.setText("Total no of Person: " + String.valueOf(total));
                        mdialog.dismiss();
                    }
                    adapter.notifyDataSetChanged();
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
                Map<String, String> param = new HashMap<>();
                param.put("tagno", Common.tagno);
                return param;
            }
        };

        queue.add(sr);
    }
}
