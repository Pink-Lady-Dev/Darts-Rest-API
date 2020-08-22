package com.pinkladydev.DartsRestAPI.dao.entities.mappers;

import com.pinkladydev.DartsRestAPI.api.models.UserRequest;
import com.pinkladydev.DartsRestAPI.dao.entities.UserEntity;

import static com.pinkladydev.DartsRestAPI.dao.entities.UserEntity.aUserEntityBuilder;

public class UserRequestToUserEntityMapper {

    public static UserEntity mapUserRequestToUserEntity(UserRequest userRequest){
        return aUserEntityBuilder().username(userRequest.getUsername()).password(userRequest.getPassword()).build();
    }
}
