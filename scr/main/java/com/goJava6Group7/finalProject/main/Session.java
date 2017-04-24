package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.entities.User;

/**
 * Created by Igor on 13.04.2017.
 */
public class Session {
    private User user;
    private boolean isGuest;
    private boolean isAdmin;

    //TODO Kontar Maryna changes these for Menu mainMenu()
    public Session(){
        isGuest = true;
    }

    public Session(User user){}

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
