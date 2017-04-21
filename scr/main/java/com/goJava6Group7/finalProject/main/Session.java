package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.entities.User;

/**
 * Created by Igor on 13.04.2017.
 */
public class Session {
    private User user;
    private boolean isGuest;
    private boolean isAdmin;

    public Session(){
        isGuest = true;
    }

    public Session(User user){}



}
