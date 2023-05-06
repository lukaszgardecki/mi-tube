package com.example.app.domain.user;

import com.example.app.domain.config.security.AuthenticationService;
import com.example.app.domain.user.dto.UserCredentialsDto;
import com.example.app.domain.user.dto.UserManageAccountDto;
import com.example.app.domain.user.dto.UserRegistrationDto;
import com.example.app.domain.user.dto.UserSessionDto;
import com.example.app.domain.user.mapper.UserCredentialsDtoMapper;
import com.example.app.domain.user.mapper.UserManageAccountDtoMapper;
import com.example.app.domain.user.mapper.UserSessionDtoMapper;
import com.example.app.storage.FileStorageService;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationService authenticationService;


    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder, FileStorageService fileStorageService,
                       AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
        this.authenticationService = authenticationService;
    }

    public Optional<UserCredentialsDto> findCredentialsByEmail(String email) {
        return userRepository.findByEmail(email).map(UserCredentialsDtoMapper::map);
    }

    public Optional<UserSessionDto> findUserByEmail(String email) {
        return userRepository.findByEmail(email).map(UserSessionDtoMapper::map);
    }

    public Optional<UserManageAccountDto> findUserDataByEmail(String email) {
        return userRepository.findByEmail(email).map(UserManageAccountDtoMapper::map);
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

    @Transactional
    public UserSessionDto updateUserData(UserManageAccountDto userData) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User userToUpdate = userRepository.findByEmail(currentEmail).orElseThrow();
        changeUserData(userData, userToUpdate);
        updateUserSecurityAuthentication(userToUpdate);
        return UserSessionDtoMapper.map(userToUpdate);
    }

    private void changeUserData(UserManageAccountDto userData, User user) {
        changeFirstName(user, userData);
        changeLastName(user, userData);
        changeEmail(user, userData);
        changePassword(user, userData);
        changeAvatar(user, userData);
    }

    private void updateUserSecurityAuthentication(User user) {
        authenticationService.updateAuthentication(UserCredentialsDtoMapper.map(user));
    }

    private void changeFirstName(User user, UserManageAccountDto userData) {
        if (!user.getFirstName().equals(userData.getFirstName())) {
            user.setFirstName(userData.getFirstName());
        }
    }

    private void changeLastName(User user, UserManageAccountDto userData) {
        if (!user.getLastName().equals(userData.getLastName())) {
            user.setLastName(userData.getLastName());
        }
    }

    private void changeEmail(User user, UserManageAccountDto userData) {
        if (!user.getEmail().equals(userData.getEmail())) {
            user.setEmail(userData.getEmail());
        }
    }

    private void changeAvatar(User user, UserManageAccountDto userData) {
        boolean avatarIsChanged = !Objects.equals(userData.getAvatar().getOriginalFilename(), "")
                && userData.getAvatar() != null;

        if (avatarIsChanged) {
            String savedFileName = fileStorageService.saveAvatar(userData.getAvatar());
            fileStorageService.deleteAvatar(user.getAvatar());
            user.setAvatar(savedFileName);
        }
    }

    private void changePassword(User user, UserManageAccountDto userData) {
        String newPassword = userData.getNewPassword();
        boolean newPassIsNotSameLikeOldOne = !passwordEncoder.matches(newPassword, user.getPassword());
        boolean newPassIsNotEmpty = !newPassword.isEmpty() && !newPassword.isBlank();

        if (newPassIsNotSameLikeOldOne && newPassIsNotEmpty) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
    }
}
