package com.project.demo.Utils;
import com.project.demo.DataTransferObject.UserDTA;
import com.project.demo.Entity.UserEntity;

public class UserMapper {

    public static UserDTA mapperFormUserEntityToUserDTA(UserEntity inputUserEntity){
        return new UserDTA(String.valueOf(inputUserEntity.getId()),
                inputUserEntity.getUsername(),
                null, inputUserEntity.getRole().getRole(),
                inputUserEntity.getMoneyOnTheAccount().toString());
    }
}
