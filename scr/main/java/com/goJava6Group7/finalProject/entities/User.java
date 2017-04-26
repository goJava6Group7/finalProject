package com.goJava6Group7.finalProject.entities;

/**
 * Created by Igor on 13.04.2017.
 */
public class User extends Entity {

    //TODO Kontar Maryna changes these for  ProjectController   createAccount
    private String name;
    private String login;
    private String password;

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    //TODO Kontar Maryna: для тестирования методов, пока backend не написали DB.
    ////TODO ОБЯЗАТЕЛЬНО ПОМЕНЯТЬ (УБРАТЬ ПАРОЛЬ)!!!!!!!!!!!!!
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
