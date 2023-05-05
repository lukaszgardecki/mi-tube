package com.example.app.domain.user;

import com.example.app.domain.user.dto.UserCredentialsDto;
import com.example.app.domain.user.dto.UserManageAccountDto;
import com.example.app.domain.user.dto.UserRegistrationDto;
import com.example.app.domain.user.dto.UserSessionDto;
import com.example.app.domain.user.mapper.UserCredentialsDtoMapper;
import com.example.app.domain.user.mapper.UserSessionDtoMapper;
import com.example.app.storage.FileStorageService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private static final String DEFAULT_USER_ROLE = "USER";
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;


    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, FileStorageService fileStorageService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
    }

    public Optional<UserCredentialsDto> findCredentialsByEmail(String email) {
        return userRepository.findByEmail(email).map(UserCredentialsDtoMapper::map);
    }

    @Transactional
    public void registerUserWithDefaultRole(UserRegistrationDto userRegistration) {
        UserRole defaultRole = userRoleRepository.findByName(DEFAULT_USER_ROLE).orElseThrow();
        User user = new User();
        user.setFirstName(userRegistration.getFirstName());
        user.setLastName(userRegistration.getLastName());
        user.setEmail(userRegistration.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        user.getRoles().add(defaultRole);
        userRepository.save(user);
    }

    public Optional<UserSessionDto> findUserByEmail(String email) {
        return userRepository.findByEmail(email).map(UserSessionDtoMapper::map);
    }

    @Transactional
    public UserSessionDto updateUserData(UserManageAccountDto user) {
        User userToUpdate = userRepository.findByEmail(user.getEmail()).orElseThrow();
        if (!userToUpdate.getFirstName().equals(user.getFirstName())) {
            userToUpdate.setFirstName(user.getFirstName());
        }
        if (!userToUpdate.getLastName().equals(user.getLastName())) {
            userToUpdate.setLastName(user.getLastName());
        }

        if (!userToUpdate.getEmail().equals(user.getNewEmail())) {
            userToUpdate.setEmail(user.getNewEmail());
        }

        boolean avatarIsChanged = !Objects.equals(user.getAvatar().getOriginalFilename(), "")
                && user.getAvatar() != null;

        if (avatarIsChanged) {
            String savedFileName = fileStorageService.saveAvatar(user.getAvatar());
            fileStorageService.deleteAvatar(userToUpdate.getAvatar());
            userToUpdate.setAvatar(savedFileName);
        }
        return UserSessionDtoMapper.map(userToUpdate);
    }


}
