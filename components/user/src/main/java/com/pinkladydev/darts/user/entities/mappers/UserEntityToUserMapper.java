package com.pinkladydev.darts.user.entities.mappers;

import com.pinkladydev.darts.user.User;
import com.pinkladydev.darts.user.entities.UserEntity;

public class UserEntityToUserMapper {

    public static User mapUserEntityToUser(UserEntity userEntity){
        return User.aUserBuilder()
                .username(userEntity.username)
                .password(userEntity.password)
                .id(userEntity.id)
                .build();
    }
}
