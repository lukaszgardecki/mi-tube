package com.example.app.domain.user.mapper;

import com.example.app.domain.user.User;
import com.example.app.domain.user.UserRole;
import com.example.app.domain.user.dto.UserCredentialsDto;
import com.example.app.domain.user.dto.UserManageAccountDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserManageAccountDtoMapperTest {

    @Test
    void map() {
        //given
        User user = createUser();
        //when
        UserManageAccountDto userManageAccountDto = UserManageAccountDtoMapper.map(user);
        //then
        assertThat(userManageAccountDto).isNotNull();
        assertThat(userManageAccountDto.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userManageAccountDto.getLastName()).isEqualTo(user.getLastName());
        assertThat(userManageAccountDto.getEmail()).isEqualTo(user.getEmail());
    }

    private User createUser() {
        User user = new User();
        user.setFirstName("FirstnameTest");
        user.setLastName("LastnameTest");
        user.setEmail("test@test.test");
        user.setAvatar("test_avatar.jpg");
        user.setPassword("testPass");
        user.setRoles(createUserRoles());
        return user;
    }

    private Set<UserRole> createUserRoles() {
        Set<UserRole> roles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setId(1L);
        userRole.setName("USER");
        userRole.setDescription("USER test role");
        roles.add(userRole);
        return roles;
    }
}