package com.pinkladydev.DartsRestAPI.dao.entities;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Builder
@Document(collection = "Users")
public class UserEntity {

    @Id
    public String id;
    public String username;
    public String password;

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, username, password);
    }

    public static UserEntityBuilder aUserEntityBuilder(){
        return UserEntity.builder();
    }
}
