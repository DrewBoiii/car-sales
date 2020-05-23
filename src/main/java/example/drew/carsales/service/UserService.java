package example.drew.carsales.service;

import example.drew.carsales.persistence.dto.user.*;
import example.drew.carsales.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public interface UserService {

    @Transactional
    void save(RegistrationDto registrationDto);

    @Transactional
    void updateAndSendMail(UserProfileDto userProfileDto) throws IOException;

    @Transactional
    void updateAndSendMail(UserRolesUpdateDto userRolesUpdateDto);

    @Transactional
    void updateAndSendMail(ChangePasswordDto changePasswordDto);

    @Transactional
    void updateAndSendMail(User user);

    @Transactional
    void update(User user);

    @Transactional
    void deleteByUsername(String username);

    @Transactional
    void deleteById(Long id);

    User getByUsername(String username);
    User getByEmail(String email);
    User getUserByCode(String code);
    User getById(Long id);
    Page<User> getAll(Pageable pageable);
    Page<User> getAll(UserCriteriaDto criteria, Pageable pageable);
    Boolean isExists(String username, String email);
    Boolean isCodeValid(String code);
    void resetPasswordByEmail(User user);

}
