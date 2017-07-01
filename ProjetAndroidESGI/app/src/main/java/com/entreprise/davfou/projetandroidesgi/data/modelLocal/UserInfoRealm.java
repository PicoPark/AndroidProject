package com.entreprise.davfou.projetandroidesgi.data.modelLocal;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by davidfournier on 01/07/2017.
 */

public class UserInfoRealm extends RealmObject {

    @PrimaryKey
    private String _id;

    private String email;




    private String firstName;

    private String lastName;

    public UserInfoRealm() {
    }

    public UserInfoRealm(String email, String _id, String firstName, String lastName) {
        this.email = email;
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
}
