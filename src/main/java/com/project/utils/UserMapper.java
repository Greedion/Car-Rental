package com.project.utils;

import com.project.model.FullUser;
import com.project.entity.UserEntity;

public class UserMapper {

    private UserMapper(){}
    public static FullUser mapperFormUserEntityToUserDTA(UserEntity inputUserEntity){
        return new FullUser(String.valueOf(inputUserEntity.getId()),
                inputUserEntity.getUsername(),
                null, inputUserEntity.getRole().getRole(),
                inputUserEntity.getMoneyOnTheAccount().toString());
    }
}
