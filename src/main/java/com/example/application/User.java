package com.example.application;

import java.util.ArrayList;




public class User {
    private String first;
    private String last;
    private String username;
    private String bio;
    private String pfpPath;
    private ArrayList<String> friendlist = new ArrayList<String>();


    public User(String first, String last, String username, String bio, String pfpPath) {
        this.first = first;
        this.last = last;
        this.username = username;
        this.bio = bio;
        this.pfpPath = pfpPath;
        this.friendlist = new ArrayList<>();
    }
    
    public User(String first, String last,String username){
        this.first = first;
        this.last = last;
        this.username = username;
    }
    



    public String getFirstName() {
        return first;
    }

    public void editFirstName(String first) {
        this.first = first;
    }

    public String getLastName() {
        return last;
    }

    public void editLastName(String last) {
        this.last = last;
    }

    public String getUsername() {
        return username;
    }

    public void editUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPfpPath() {
        return pfpPath;
    }

    public void setPfpPath(String pfpPtah) {
        this.pfpPath = pfpPath;
    }

   public void addfriend(String name){
       friendlist.add(name);
   }
   
   public void removefriend(String name){
       friendlist.remove(name);
   }
}