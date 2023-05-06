package com.example.app.domain.user.mapper;

import com.example.app.domain.user.User;
import com.example.app.domain.user.dto.UserManageAccountDto;

public class UserManageAccountDtoMapper {

    public static UserManageAccountDto map(User user) {
        UserManageAccountDto dto = new UserManageAccountDto();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());


        return dto;
    }
}
