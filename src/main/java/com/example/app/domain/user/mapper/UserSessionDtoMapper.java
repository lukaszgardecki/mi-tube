package com.example.app.domain.user.mapper;

import com.example.app.domain.user.User;
import com.example.app.domain.user.dto.UserSessionDto;

public class UserSessionDtoMapper {

    public static UserSessionDto map(User user) {
        UserSessionDto dto = new UserSessionDto();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setInitials(getUserInitials(user));
        return dto;
    }

    private static String getUserInitials(User user) {
        String firstLetterOfFirstName = user.getFirstName().substring(0, 1);
        String firstLetterOfLastName = user.getLastName().substring(0, 1);
        return firstLetterOfFirstName + firstLetterOfLastName;
    }
}
