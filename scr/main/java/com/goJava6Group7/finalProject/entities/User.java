package com.goJava6Group7.finalProject.entities;

import com.goJava6Group7.finalProject.utils.IdUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Created by Igor on 13.04.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends Entity {
    @XmlElement
    private long id;
    @XmlElement
    private String login;
    @XmlElement
    private String password;
    @XmlElement
    private String name;
    private Role role;

    public enum Role{
        ADMIN, USER, GUEST
    }

    public User(){
    }

    public User(String name, String login, String password) {
        this.id = IdUtil.getUserId();
        role = Role.GUEST;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    @Override
    public String getOutput(){

        String output = String.format("%-4d \t %-20s \t %-20s \t %-20s \t %n", this.getId(), this.getLogin(), this.getPassword(), this.getName());

        return output;

    }

//    @Override
    public static String getOutputHeader() {

        String header = String.format("%-4s \t %-20s \t %-20s \t %-20s \t", "id", "login", "password", "name");

        return header;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof User)) return false;

        User user = (User) o;
        return user.id == this.id && user.name.equals(this.name)
                && user.login.equals(this.login) && user.password.equals(this.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, password);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
