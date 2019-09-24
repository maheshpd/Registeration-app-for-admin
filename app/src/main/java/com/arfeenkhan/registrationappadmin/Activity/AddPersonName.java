package com.arfeenkhan.registrationappadmin.Activity;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arfeenkhan.registrationappadmin.Adapter.PersonNameAdapter;
import com.arfeenkhan.registrationappadmin.Model.SessionNameModel;
import com.arfeenkhan.registrationappadmin.R;
import com.arfeenkhan.registrationappadmin.util.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
mahesh
public class AddPersonName extends AppCompatActivity {
    private Spinner spinner;
    String insert_session_name = "http://167.71.229.74/barcodescanner/insert_session_name.php";
    Button add;
    String username;
    ProgressDialog progressDialog;
    StringRequest sessionrequest;
    public static ArrayList<SessionNameModel> sessionlist = new ArrayList<>();
    public static PersonNameAdapter personNameAdapter;

    RecyclerView sessionRecycler;
    String sessionUrl = "http://167.71.229.74/barcodescanner/getSessionName.php";
    String  allocationnameUrl = "http://167.71.229.74/barcodescanner/getAllocationName.php";
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<String> allocationnamelist = new ArrayList<>();

    AutoCompleteTextView autoSuggestion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person_name);

        spinner = findViewById(R.id.user_name1);
        autoSuggestion = findViewById(R.id.user_name);
        add = findViewById(R.id.add_btn);
        sessionRecycler = findViewById(R.id.vertical_Seesion);
        swipeRefreshLayout = findViewById(R.id.swipeLayout);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,allocationnamelist);
        autoSuggestion.setAdapter(adapter);



        getAllocationName();
        progressDialog = new ProgressDialog(this);
        getSessionName();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                InsertData();

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSessionName();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                username = spinner.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        personNameAdapter = new PersonNameAdapter(sessionlist, this);
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        sessionRecycler.setLayoutManager(manager1);
        sessionRecycler.setAdapter(personNameAdapter);
        sessionRecycler.setHasFixedSize(true);
        personNameAdapter.notifyDataSetChanged();
    }

    private void InsertData() {
        username = autoSuggestion.getText().toString().trim();
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest sr = new StringRequest(Request.Method.POST, insert_session_name, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    JSONObject c = arr.getJSONObject(0);
                    String message = c.getString("message");
                    Toast.makeText(AddPersonName.this, message, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    autoSuggestion.setText("");
                    getSessionName();
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
                params.put("tag_no", Common.tagno);
                params.put("name", username);
                return params;
            }
        };
        queue.add(sr);
    }


    private void getAllocationName() {
        RequestQueue queue = Volley.newRequestQueue(this);
        sessionlist.clear();
        sessionrequest = new StringRequest(Request.Method.GET, allocationnameUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject c = arr.getJSONObject(i);
                        String name = c.getString("name");
                        allocationnamelist.add(name);
                    }
//                    spinner.setAdapter(new ArrayAdapter<>(AddPersonName.this, android.R.layout.simple_spinner_dropdown_item, allocationnamelist));
                    personNameAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(sessionrequest);
    }


    public void getSessionName() {
        sessionlist.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        sessionrequest = new StringRequest(Request.Method.POST, sessionUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject c = arr.getJSONObject(i);
                        String name = c.getString("name");
                        String id = c.getString("id");
                        SessionNameModel snm = new SessionNameModel(id, name);
                        sessionlist.add(snm);
                    }

                    personNameAdapter.notifyDataSetChanged();
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
                params.put("tagno", Common.tagno);
                return params;
            }
        };
        queue.add(sessionrequest);
    }
}
