package com.arfeenkhan.registrationappadmin.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arfeenkhan.registrationappadmin.Model.SignleCoachDataModel;
import com.arfeenkhan.registrationappadmin.R;
import com.arfeenkhan.registrationappadmin.util.Common;

import java.util.ArrayList;

public class SingleCoachDataAdapter extends RecyclerView.Adapter<SingleCoachDataAdapter.SingleCoachDataViewHolder> {

    private ArrayList<SignleCoachDataModel> list;
    private Context context;
    Dialog edtDialog, secondedt;
    EditText username, password;
    EditText name, phone, uniqueid;
    Button submit, update;

    String susername, spassword;

    public SingleCoachDataAdapter(ArrayList<SignleCoachDataModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SingleCoachDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singlecoachdata_item, viewGroup, false);
        edtDialog = new Dialog(context);
        secondedt = new Dialog(context);
        return new SingleCoachDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SingleCoachDataViewHolder singleCoachDataViewHolder, int i) {
        final SignleCoachDataModel signleCoachDataModel = list.get(i);

        Common.userid = signleCoachDataModel.getId();

        singleCoachDataViewHolder.username.setText(signleCoachDataModel.getUsername());
        singleCoachDataViewHolder.userphone.setText(signleCoachDataModel.getUserphone());
        singleCoachDataViewHolder.userid.setText(signleCoachDataModel.getUserid());
        singleCoachDataViewHolder.date.setText(signleCoachDataModel.getDate());
        singleCoachDataViewHolder.time.setText(signleCoachDataModel.getTime());
        singleCoachDataViewHolder.coachname.setText(signleCoachDataModel.getCoachname());

//        singleCoachDataViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                edtDialog.setContentView(R.layout.password_username_layout);
//                username = edtDialog.findViewById(R.id.username);
//                password = edtDialog.findViewById(R.id.userpass);
//                submit = edtDialog.findViewById(R.id.submit);
//                edtDialog.show();
//
//                submit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        susername = username.getText().toString().trim();
//                        spassword = password.getText().toString().trim();
//
//                        if (TextUtils.isEmpty(susername)) {
//                            Toast.makeText(context, "Enter user name", Toast.LENGTH_SHORT).show();
//                        } else if (TextUtils.isEmpty(spassword)) {
//                            Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show();
//                        } else {
//                            if (susername.equals("Admin") && spassword.equals("admin")) {
//                                edtDialog.dismiss();
//                                Intent userEditIntent = new Intent(context, UserEdit.class);
//
//                                String coachname = signleCoachDataModel.getCoachname();
//                                String uid = signleCoachDataModel.getId();
//                                String username = signleCoachDataModel.getUsername();
//                                String phone = signleCoachDataModel.getUserphone();
//                                String unino = signleCoachDataModel.getUserid();
//                                userEditIntent.putExtra("uid", uid);
//                                userEditIntent.putExtra("username", username);
//                                userEditIntent.putExtra("phone", phone);
//                                userEditIntent.putExtra("unino", unino);
//                                userEditIntent.putExtra("coachname", coachname);
//                                context.startActivity(userEditIntent);
//                            } else {
//                                Toast.makeText(context, "user not exist", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SingleCoachDataViewHolder extends RecyclerView.ViewHolder {
        private TextView coachname, username, userphone, userid, date, time;

        public SingleCoachDataViewHolder(@NonNull View itemView) {
            super(itemView);

            coachname = itemView.findViewById(R.id.coachname);
            username = itemView.findViewById(R.id.username);
            userphone = itemView.findViewById(R.id.phone);
            userid = itemView.findViewById(R.id.uniqueno);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);


//            Handler handler = new Handler() {
//                @Override
//                public void publish(LogRecord record) {
//
//                }
//
//                @Override
//                public void flush() {
//
//                }
//
//                @Override
//                public void close() throws SecurityException {
//
//                }
//            };
        }
    }


}
