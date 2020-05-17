package example.drew.carsales.service.impl;

import example.drew.carsales.persistence.dto.user.*;
import example.drew.carsales.persistence.entity.Role;
import example.drew.carsales.persistence.entity.User;
import example.drew.carsales.persistence.repository.UserRepository;
import example.drew.carsales.service.MailService;
import example.drew.carsales.service.RoleService;
import example.drew.carsales.service.UserService;
import example.drew.carsales.specification.UserSpecification;
import example.drew.carsales.util.ImageUploadUtil;
import example.drew.carsales.util.MailUtil;
import example.drew.carsales.util.PasswordUtil;
import example.drew.carsales.util.RoleConstant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final MailService mailService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.mailService = mailService;
    }

    @Override
    public void save(RegistrationDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Collections.singleton(roleService.getByName(RoleConstant.USER_ROLE)));
        user.setActivationCode(MailUtil.getGeneratedCode());

        userRepository.save(user);

        String message = MailUtil.getActivationCodeMessage(user.getUsername());

        mailService.send(user.getEmail(), "Account activation", message);
    }

    @Override
    public void update(UserProfileDto dto) throws IOException {
        User user = userRepository.findById(dto.getId()).orElse(null);

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

        byte[] userPhoto;
        try {
            userPhoto = ImageUploadUtil.avatarConvert(dto.getPhoto());
            user.setPhoto(userPhoto);
        } catch (IOException e) {
            throw new IOException();
        }

        userRepository.save(user);
    }

    @Override
    public void update(UserRolesUpdateDto userRolesUpdateDto) {
        User user = userRepository.findById(userRolesUpdateDto.getId()).orElse(null);
        Set<Role> roles = user.getRoles();
        List<String> roleNames = userRolesUpdateDto.getRoles();
        roleNames.forEach(roleName -> roles.add(roleService.getByName(roleName)));
        userRepository.save(user);
    }

    @Override
    public void update(ChangePasswordDto changePasswordDto) {
        User user = userRepository.findById(changePasswordDto.getId()).orElse(null);
        user.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> getAll(UserCriteriaDto criteria, Pageable pageable) {
        return userRepository.findAll(
                Specification
                        .where(UserSpecification.getUsersByUsername(criteria.getUsername()))
                        .and(UserSpecification.getUsersByFirstName(criteria.getFirstName()))
                        .and(UserSpecification.getUsersByLastName(criteria.getLastName())),
                pageable
        );
    }

    @Override
    public Boolean isExists(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }

    @Override
    public Boolean isCodeValid(String code) {
        User user = userRepository.findByActivationCode(code).orElse(null);

        if(user != null) {
            user.setActive(true);
            user.setActivationCode(null);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User getUserByCode(String code) {
        return userRepository.findByActivationCode(code).orElse(null);
    }

    @Override
    public void resetPasswordByEmail(User user) {
        String newPassword = PasswordUtil.getGeneratedPassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        mailService.send(user.getEmail(), "Password Reset", MailUtil.getNewTempPasswordMessage(newPassword));
    }

}
