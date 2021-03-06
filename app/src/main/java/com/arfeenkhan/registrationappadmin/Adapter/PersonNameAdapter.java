package com.arfeenkhan.registrationappadmin.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arfeenkhan.registrationappadmin.Activity.AddPersonName;
import com.arfeenkhan.registrationappadmin.Model.SessionNameModel;
import com.arfeenkhan.registrationappadmin.R;
import com.arfeenkhan.registrationappadmin.util.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.arfeenkhan.registrationappadmin.Activity.AddPersonName.personNameAdapter;

public class PersonNameAdapter extends RecyclerView.Adapter<PersonNameAdapter.PeopleViewHolder> {
    ArrayList<SessionNameModel> list;
    Context context;
    private int highlightItem = 0;
    Dialog edtDialog;
    String delete_allocation_name = "http://167.71.229.74/barcodescanner/deleteAllocationName.php";

    String update_allocation_name = "http://167.71.229.74/barcodescanner/update1.php";
    EditText username;
    Button updateBtn;

    public PersonNameAdapter(ArrayList<SessionNameModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_name_item, parent, false);
        edtDialog = new Dialog(context);
        return new PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PeopleViewHolder holder, final int position) {
        final SessionNameModel ssm = list.get(position);
        holder.txtName.setText(ssm.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.editsessionid = ssm.getId();
                String name = holder.txtName.getText().toString();
                edtDialog.setContentView(R.layout.edit_layout);
                edtDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                edtDialog.setCanceledOnTouchOutside(false);
                username = edtDialog.findViewById(R.id.coach_name_field);
                updateBtn = edtDialog.findViewById(R.id.updateBtn);
                username.setText(name);
                edtDialog.show();

                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sname = username.getText().toString();
                        update(sname);
                    }
                });
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Common.editsessionid = ssm.getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Do you want to Delete");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        notifyDataSetChanged();
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


    private void update(final String username) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, update_allocation_name, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arr = new JSONObject(response);
                    for (int i = 0; i < arr.length(); i++) {
                        String message = arr.getString("tag");
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                    }
                    personNameAdapter.notifyDataSetChanged();
                    edtDialog.dismiss();
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
                params.put("id", Common.editsessionid);
                params.put("name", username);
                return params;
            }
        };

        queue.add(sr);
    }


    private void delete() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, delete_allocation_name, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arr = new JSONObject(response);
                    for (int i = 0; i < arr.length(); i++) {
                        String message = arr.getString("tag");
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                    personNameAdapter.notifyDataSetChanged();
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
                params.put("id", Common.editsessionid);
                return params;
            }
        };

        queue.add(sr);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView checkImage;

        public PeopleViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.person_name);
        }
    }
}
