package example.drew.carsales.service;

import example.drew.carsales.persistence.dto.user.*;
import example.drew.carsales.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface UserService {

    void save(RegistrationDto registrationDto);
    void update(UserProfileDto userProfileDto) throws IOException;
    void update(UserRolesUpdateDto userRolesUpdateDto);
    void update(ChangePasswordDto changePasswordDto);
    void deleteByUsername(String username);
    void deleteById(Long id);
    User getByUsername(String username);
    User getById(Long id);
    Page<User> getAll(Pageable pageable);
    Page<User> getAll(UserCriteriaDto criteria, Pageable pageable);
    Boolean isExists(String username, String email);
    Boolean isActivate(String code);

}
