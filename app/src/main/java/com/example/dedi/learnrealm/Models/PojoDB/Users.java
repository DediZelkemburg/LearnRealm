package com.example.dedi.learnrealm.Models.PojoDB;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * This is class declare database and field
 **/
public class Users extends RealmObject {

    @PrimaryKey
    private int id;

    /*@Index
    private String a;*/

    @Required
    private String name;
    private String pass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
