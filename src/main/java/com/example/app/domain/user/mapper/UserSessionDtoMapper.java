package com.example.app.domain.user.mapper;

import com.example.app.domain.user.User;
import com.example.app.domain.user.dto.UserSessionDto;

public class UserSessionDtoMapper {

    public static UserSessionDto map(User user) {
        UserSessionDto dto = new UserSessionDto();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setInitials(user.getFirstName().substring(0, 1));
        dto.setAvatar(user.getAvatar());
        return dto;
    }
}
