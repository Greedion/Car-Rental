package com.project.demo.Utils;
import com.project.demo.DataTransferObject.UserDTO;
import com.project.demo.Entity.UserEntity;

public class UserMapper {

    public static UserDTO mapperFormUserEntityToUserDTA(UserEntity inputUserEntity){
        return new UserDTO(String.valueOf(inputUserEntity.getId()),
                inputUserEntity.getUsername(),
                null, inputUserEntity.getRole().getRole(),
                inputUserEntity.getMoneyOnTheAccount().toString());
    }
}
