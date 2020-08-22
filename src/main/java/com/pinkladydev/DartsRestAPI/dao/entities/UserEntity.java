package com.pinkladydev.DartsRestAPI.dao.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "Users")
public class UserEntity {

    @Id
    public String id;
    public String username;
    public String password;

    public UserEntity( String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, username, password);
    }
}
