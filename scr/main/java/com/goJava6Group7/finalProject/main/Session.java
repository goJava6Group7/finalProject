package com.goJava6Group7.finalProject.main;

import com.goJava6Group7.finalProject.entities.User;

import static com.goJava6Group7.finalProject.entities.User.Role.ADMIN;

public class Session {

    private User user;
    private boolean isGuest;
    private boolean isAdmin;

    public Session(){
        user = null;
        isGuest = true;
        isAdmin = false;
    }

    public Session(User user){
        this.user = user;
        if(user.getRole().equals(ADMIN))
            setAdmin(true);
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {isAdmin = admin;}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
