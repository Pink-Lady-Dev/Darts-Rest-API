package com.pinkladydev.user.entities.mappers;

import com.pinkladydev.user.User;
import com.pinkladydev.user.entities.UserEntity;

public class UserEntityToUserMapper {

    public static User mapUserEntityToUser(UserEntity userEntity){
        return User.aUserBuilder()
                .username(userEntity.username)
                .password(userEntity.password)
                .id(userEntity.id)
                .build();
    }
}
