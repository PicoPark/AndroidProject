package com.entreprise.davfou.projetandroidesgi.data.modelLocal;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by davidfournier on 18/06/2017.
 */

public class UserRealm extends RealmObject {

    @PrimaryKey
    private long id;


    private String email;

    private String password;

    private String firstName;

    private String lastName;


    private String token;

    private boolean connected;

    private boolean keepConnection;

    public UserRealm() {
    }

    public UserRealm(long id, String email, String password,  String token,String firstName,String lastName, boolean connected, boolean keepConnection) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        this.connected = connected;
        this.keepConnection = keepConnection;
    }


    public boolean isKeepConnection() {
        return keepConnection;
    }

    public void setKeepConnection(boolean keepConnection) {
        this.keepConnection = keepConnection;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
