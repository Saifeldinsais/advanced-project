package com.example.application;


public class SessionManager {
    private static User currentUser;
    private static int UserId;

    public static void setCurrentUser(User user) {
        currentUser = user;
        if(user != null) {
        	UserId = user.getID();
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }
    
    public static int getUserId() {
    	return UserId;
    }
    
}