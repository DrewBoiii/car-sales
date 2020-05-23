package example.drew.carsales.controller;

import example.drew.carsales.persistence.dto.car.SaveCarDto;
import example.drew.carsales.service.CarBrandService;
import example.drew.carsales.service.CarModelService;
import example.drew.carsales.service.CarService;
import example.drew.carsales.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarBrandService carBrandService;
    private final CarModelService carModelService;
    private final CarService carService;
    private final UserService userService;

    public CarController(CarBrandService carBrandService, CarModelService carModelService, CarService carService, UserService userService) {
        this.carBrandService = carBrandService;
        this.carModelService = carModelService;
        this.carService = carService;
        this.userService = userService;
    }

    @GetMapping("/editor")
    @PreAuthorize("hasAuthority('user')")
    public String getEditorPage(Model model) {
        model.addAttribute("car_dto", new SaveCarDto());
        model.addAttribute("brands", carBrandService.getAll());
        model.addAttribute("models", carModelService.getAll());
        model.addAttribute("date", LocalDateTime.now());
        return "editor";
    }

    @PostMapping("/editor")
    @PreAuthorize("hasAuthority('user')")
    public String saveCar(@AuthenticationPrincipal User authUser,
                          @ModelAttribute("car_dto") SaveCarDto dto) {
        example.drew.carsales.persistence.entity.User user = userService.getByUsername(authUser.getUsername());
        dto.setUser(user);
        carService.save(dto);
        return "redirect:/home";
    }

}
