package example.drew.carsales.service;

import example.drew.carsales.persistence.dto.captcha.CaptchaResponseDto;

public interface CaptchaService {

    CaptchaResponseDto getCaptchaResponseDto(String captchaResponse);

}
