package com.pinkladydev.gameWeb.dao.entities.mappers;

import com.pinkladydev.gameWeb.api.models.UserRequest;
import com.pinkladydev.gameWeb.dao.entities.UserEntity;

import static com.pinkladydev.gameWeb.dao.entities.UserEntity.aUserEntityBuilder;

public class UserRequestToUserEntityMapper {

    public static UserEntity mapUserRequestToUserEntity(UserRequest userRequest){
        return aUserEntityBuilder().username(userRequest.getUsername()).password(userRequest.getPassword()).build();
    }
}
