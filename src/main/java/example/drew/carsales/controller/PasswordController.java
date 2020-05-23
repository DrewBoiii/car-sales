package example.drew.carsales.controller;

import example.drew.carsales.persistence.dto.user.ChangePasswordDto;
import example.drew.carsales.service.UserService;
import example.drew.carsales.util.MailUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/password")
public class PasswordController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public PasswordController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/change")
    @PreAuthorize("hasAuthority('user')")
    public String changePassword(Model model,
                                 @ModelAttribute("pass_dto") ChangePasswordDto dto,
                                 @AuthenticationPrincipal User authUser) {
        example.drew.carsales.persistence.entity.User user = userService.getByUsername(authUser.getUsername());
        if(!passwordEncoder.matches(dto.getOld(), user.getPassword())) {
            model.addAttribute("message", "Incorrect old password!");
            return "private_profile";
        }
        if(!StringUtils.equals(dto.getPassword(), dto.getConfirm())) {
            model.addAttribute("message", "Passwords do not match!");
            return "private_profile";
        }
        userService.updateAndSendMail(dto);
        return "redirect:/profile";
    }

    @GetMapping("/reset")
    public String getResetPasswordPage() {
        return "password";
    }

    @PostMapping("/reset")
    public String resetPassword(Model model,
                                @NotBlank @Email @RequestParam("email") String email) {
        example.drew.carsales.persistence.entity.User user = userService.getByEmail(email);
        model.addAttribute("message", "Invalid email!");
        if(user != null) {
            user.setActivationCode(MailUtil.getGeneratedCode());
            userService.updateAndSendMail(user);
            model.addAttribute("message", "Mail to confirm your identity was send.");
        }
        return "password";
    }

    @GetMapping("/reset/confirm/{code}")
    public String getResetPasswordConfirmPage(Model model, @PathVariable("code") String code) {
        model.addAttribute("message", "Reset password code was not found.");
        example.drew.carsales.persistence.entity.User user = userService.getUserByCode(code);
        if(user != null) {
            userService.resetPasswordByEmail(user);
            model.addAttribute("message", "Mail with a new temporary password was send.");
        }
        return "password";
    }

}
