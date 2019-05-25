package com.arfeenkhan.registrationappadmin.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arfeenkhan.registrationappadmin.Activity.UserDetails;
import com.arfeenkhan.registrationappadmin.Model.SelectTagModel;
import com.arfeenkhan.registrationappadmin.Model.SessionNameModel;
import com.arfeenkhan.registrationappadmin.R;
import com.arfeenkhan.registrationappadmin.util.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectTagAdapter extends RecyclerView.Adapter<SelectTagAdapter.TagViewHolder> {
    Context ctx;
    ArrayList<SelectTagModel> list;
    String updateurl = "http://magicconversion.com/barcodescanner/updatetgdata.php";
    String resetUrl = "http://magicconversion.com/barcodescanner/restallocation.php";
    ArrayList<SessionNameModel> sessionlist = new ArrayList<>();
    StringRequest sessionrequest1;
    String yes;

    public SelectTagAdapter(Context ctx, ArrayList<SelectTagModel> list, ArrayList<SessionNameModel> sessionlist) {
        this.ctx = ctx;
        this.list = list;
        this.sessionlist = sessionlist;
    }

    public SelectTagAdapter(Context ctx, ArrayList<SelectTagModel> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_tag_item, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TagViewHolder holder, final int position) {
        SelectTagModel stm = list.get(position);
        holder.mname.setText(stm.getName());
        holder.mplace.setText(stm.getPlace());
        holder.mctf.setText(stm.getCtf());
        holder.mtime.setText(stm.getTime());
        holder.mtags.setText(stm.getTagno());
        holder.mdate.setText(stm.getDate());

        final String place = holder.mplace.getText().toString();
        final String name = holder.mname.getText().toString();
        final String ctf = holder.mctf.getText().toString();
        final String time = holder.mtime.getText().toString();
        final String date = holder.mdate.getText().toString();
        final String tag = holder.mtags.getText().toString();

        String tf = list.get(position).getTf();
        String sessionnane = stm.getSessionname();

        if (sessionnane.equals("True")) {
            holder.itemView.setBackgroundColor(Color.GREEN);
            if (tf.equals("True")) {
                holder.itemView.setBackgroundColor(Color.WHITE);
            } else {
                holder.itemView.setBackgroundColor(Color.GREEN);
            }
        } else {
            holder.itemView.setBackgroundColor(Color.RED);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.coachName = holder.mname.getText().toString();
                Common.eventTimes = holder.mtime.getText().toString();
                Common.place = holder.mplace.getText().toString();
                Common.name = holder.mname.getText().toString();
                Common.ctf = holder.mctf.getText().toString();
                Common.tagno = holder.mtags.getText().toString();
                Common.sdate = holder.mdate.getText().toString();
                Common.tf = list.get(position).getTf();


                Intent mainIntent = new Intent(ctx, UserDetails.class);
                mainIntent.putExtra("place", place);
                mainIntent.putExtra("name", name);
                mainIntent.putExtra("ctf", ctf);
                mainIntent.putExtra("time", time);
                mainIntent.putExtra("tag", tag);
                mainIntent.putExtra("date", date);
                ctx.startActivity(mainIntent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Common.eventTimes = holder.mtime.getText().toString();
                Common.sdate = holder.mdate.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage("Do you want to see Data Every one");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        yes = "True";
                        UpdateView();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yes = "False";
                        UpdateView();
                    }
                });
                builder.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RestTag(tag);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }

    private void RestTag(final String tagno) {

        RequestQueue queue = Volley.newRequestQueue(ctx);
        sessionrequest1 = new StringRequest(Request.Method.POST, resetUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject array = new JSONObject(response);
                    String message = array.getString("tag");
                    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
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
                params.put("tagnum", tagno);
                return params;
            }
        };
        queue.add(sessionrequest1);
    }


//        if (AddPersonName.sessionlist.size() == 0) {
//            holder.itemView.setBackgroundColor(Color.RED);
//        } else {
//
//        }

    private void UpdateView() {
        RequestQueue queue = Volley.newRequestQueue(ctx);
        sessionrequest1 = new StringRequest(Request.Method.POST, updateurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject c = array.getJSONObject(0);
                    String message = c.getString("message");
                    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
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
                params.put("tf", yes);
                params.put("time", Common.eventTimes);
                params.put("date", Common.sdate);
                return params;
            }
        };
        queue.add(sessionrequest1);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TagViewHolder extends RecyclerView.ViewHolder {
        TextView mplace, mtime, mtags, mctf, mname, mdate, totalnopeople;
        RecyclerView allocationname;

        public TagViewHolder(View itemView) {
            super(itemView);

            mplace = itemView.findViewById(R.id.mplace);
            mtime = itemView.findViewById(R.id.mtime);
            mtags = itemView.findViewById(R.id.mtexttags);
            mctf = itemView.findViewById(R.id.mtype);
            mname = itemView.findViewById(R.id.mname);
            mdate = itemView.findViewById(R.id.mdate);
            allocationname = itemView.findViewById(R.id.allocationname_recycler);

        }
    }

//    public void getSessionName() {
////        sessionlist.clear();
//        RequestQueue queue = Volley.newRequestQueue(ctx);
//        sessionrequest = new StringRequest(Request.Method.POST, sessionUrl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray arr = new JSONArray(response);
//                    for (int i = 0; i < arr.length(); i++) {
//                        JSONObject c = arr.getJSONObject(i);
//                        String name = c.getString("name");
//                        String id = c.getString("id");
//                        SessionNameModel snm = new SessionNameModel(id, name);
//                        sessionlist.add(snm);
//                    }
////                    personNameAdapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        queue.add(sessionrequest);
//    }
}

