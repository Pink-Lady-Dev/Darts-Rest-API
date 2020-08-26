package com.pinkladydev.DartsRestAPI.dao.entities.mappers;

import com.pinkladydev.DartsRestAPI.dao.entities.UserEntity;
import com.pinkladydev.DartsRestAPI.model.User;

import static com.pinkladydev.DartsRestAPI.model.User.aUserBuilder;

public class UserEntityToUserMapper {

    public static User mapUserEntityToUser(UserEntity userEntity){
        return aUserBuilder()
                .username(userEntity.username)
                .password(userEntity.password)
                .id(userEntity.id)
                .build();
    }
}
