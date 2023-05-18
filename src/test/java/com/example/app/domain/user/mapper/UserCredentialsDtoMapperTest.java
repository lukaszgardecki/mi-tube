package com.example.app.domain.user.mapper;

import com.example.app.domain.user.User;
import com.example.app.domain.user.UserRole;
import com.example.app.domain.user.dto.UserCredentialsDto;
import org.assertj.core.api.Assertions;
import org.codehaus.groovy.tools.RootLoader;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserCredentialsDtoMapperTest {

    @Test
    void map() {
        //given
        User user = createUser();
        //when
        UserCredentialsDto credentialsDto = UserCredentialsDtoMapper.map(user);
        //then
        assertThat(credentialsDto).isNotNull();
        assertThat(credentialsDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(credentialsDto.getPassword()).isEqualTo(user.getPassword());
        assertThat(credentialsDto.getRoles()).isNotNull();
        assertThat(credentialsDto.getRoles().size()).isGreaterThanOrEqualTo(1);
        assertThat(credentialsDto.getRoles()).contains("USER");
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