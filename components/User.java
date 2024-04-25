/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.phase1;

import java.util.ArrayList;
import java.util.Scanner;


//import java.awt.image.BufferedImage; 
//import java.io.File; 
//import java.io.IOException; 
//import javax.imageio.ImageIO; 


/**
 *
 * @author tamsa
 */
public class User {
    private String username;
    private String bio;
    private String pfpPath;
    private ArrayList<Integer> friendlist = new ArrayList<Integer>();
    private static int userID = 0;
    
    Scanner in = new Scanner(System.in);

    public User(String username, String bio, String pfpPath, ArrayList<String> friendlist) {
        this.username = username;
        this.bio = bio;
        this.pfpPath = pfpPath;
        this.friendlist = new ArrayList<>();
        userID++;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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

    public void getPfpPath(String pfpPtah) {
        this.pfpPath = pfpPath;
    }

   public void addfriend(int ID){
       friendlist.add(ID);
   }
   
   public void removefriend(int ID){
       friendlist.remove(ID);
   }
    
   public void displayfriendslist(){
   
       for(int ID :friendlist){
           System.out.println(ID);
       }
   
   }
    
    
    


    
    
    
    
    
    
    
    
    
}
