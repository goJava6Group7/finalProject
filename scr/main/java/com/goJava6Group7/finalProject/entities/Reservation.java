package com.goJava6Group7.finalProject.entities;

import java.util.Date;

/**
 * Created by Igor on 13.04.2017.
 */
public class Reservation extends Entity {

    //TODO Kontar Maryna changes these for  ProjectController   reserveRoom
    //TODO(Answer) поле Hotel лишнее(ссылка на Hotel должна быть в обьекте Room)
    private User user;
    private Room room;
    private Date dataOfArrival;
    private Date dateOfDeparture;

    public Reservation(User user, Room room, Date dataOfArrival, Date dateOfDeparture) {
        this.user = user;
        this.room = room;
        this.dataOfArrival = dataOfArrival;
        this.dateOfDeparture = dateOfDeparture;
    }
}
