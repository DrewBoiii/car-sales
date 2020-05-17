package example.drew.carsales.controller;

import example.drew.carsales.persistence.dto.captcha.CaptchaResponseDto;
import example.drew.carsales.persistence.dto.user.RegistrationDto;
import example.drew.carsales.service.CaptchaService;
import example.drew.carsales.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RegistrationController {

    @Value("${recaptcha.site}")
    private String siteKey;

    private final CaptchaService captchaService;
    private final UserService userService;

    public RegistrationController(CaptchaService captchaService, UserService userService) {
        this.captchaService = captchaService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user_dto", new RegistrationDto());
        model.addAttribute("siteKey", System.getenv(siteKey));
        return "registration";
    }

    @PostMapping("/registration")
    public String saveUser(Model model,
                           @ModelAttribute("user_dto") RegistrationDto dto,
                           @RequestParam("g-recaptcha-response") String captchaResponse) {
        CaptchaResponseDto responseDto = captchaService.getCaptchaResponseDto(captchaResponse);
        if(!responseDto.isSuccess()) {
            model.addAttribute("message", "captcha verification failed.");
            return "registration";
        }
        model.addAttribute("message", "User with such username or email is already exists.");
        if(!userService.isExists(dto.getUsername(), dto.getEmail())) {
            userService.save(dto);
            model.addAttribute("message", "You're successfully signed up! Please confirm your email to activate account.");
        }
        return "registration";
    }

    @GetMapping("/activation/{code}")
    public String getActivationPage(Model model, @PathVariable("code") String code) {
        model.addAttribute("message", "Activation code is not found.");
        if(userService.isCodeValid(code)) {
            model.addAttribute("message", "Account is activated!");
        }
        return "login";
    }

}
