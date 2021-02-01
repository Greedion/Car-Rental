package com.project.Utils;
import com.project.DataTransferObject.UserDTO;
import com.project.Entity.UserEntity;

public class UserMapper {

    public static UserDTO mapperFormUserEntityToUserDTA(UserEntity inputUserEntity){
        return new UserDTO(String.valueOf(inputUserEntity.getId()),
                inputUserEntity.getUsername(),
                null, inputUserEntity.getRole().getRole(),
                inputUserEntity.getMoneyOnTheAccount().toString());
    }
}
