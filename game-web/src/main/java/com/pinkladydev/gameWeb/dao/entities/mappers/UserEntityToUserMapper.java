package com.pinkladydev.gameWeb.dao.entities.mappers;

import com.pinkladydev.gameWeb.dao.entities.UserEntity;
import com.pinkladydev.gameWeb.model.User;

public class UserEntityToUserMapper {

    public static User mapUserEntityToUser(UserEntity userEntity){
        return User.aUserBuilder()
                .username(userEntity.username)
                .password(userEntity.password)
                .id(userEntity.id)
                .build();
    }
}
