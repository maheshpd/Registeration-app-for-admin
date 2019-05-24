package com.arfeenkhan.registrationappadmin.Model;

import java.util.ArrayList;

public class SelectTagModel {

    String name, place, tagno, time, ctf, date, Tf;
    ArrayList<SessionNameModel> list;


    public SelectTagModel(String name, String place, String tagno, String time, String ctf, String date, String tf) {
        this.name = name;
        this.place = place;
        this.tagno = tagno;
        this.time = time;
        this.ctf = ctf;
        this.date = date;
        Tf = tf;
    }

    public SelectTagModel(String name, String place, String tagno, String time, String ctf, String date, String tf, ArrayList<SessionNameModel> list) {
        this.name = name;
        this.place = place;
        this.tagno = tagno;
        this.time = time;
        this.ctf = ctf;
        this.date = date;
        Tf = tf;
        this.list = list;
    }

    //    public SelectTagModel(String name, String place, String tagno, String time, String ctf, String date, String tf) {
//        this.name = name;
//        this.place = place;
//        this.tagno = tagno;
//        this.time = time;
//        this.ctf = ctf;
//        this.date = date;
//        Tf = tf;
//    }


    public ArrayList<SessionNameModel> getList() {
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTagno() {
        return tagno;
    }

    public void setTagno(String tagno) {
        this.tagno = tagno;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCtf() {
        return ctf;
    }

    public void setCtf(String ctf) {
        this.ctf = ctf;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTf() {
        return Tf;
    }

    public void setTf(String tf) {
        Tf = tf;
    }
}
