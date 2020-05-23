package example.drew.carsales.controller;

import example.drew.carsales.persistence.dto.car.SaveCarDto;
import example.drew.carsales.persistence.entity.Car;
import example.drew.carsales.persistence.entity.FavoriteCar;
import example.drew.carsales.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarBrandService carBrandService;
    private final CarModelService carModelService;
    private final CarService carService;
    private final UserService userService;
    private final FavoriteCarService favoriteCarService;

    public CarController(CarBrandService carBrandService,
                         CarModelService carModelService,
                         CarService carService,
                         UserService userService,
                         FavoriteCarService favoriteCarService) {
        this.carBrandService = carBrandService;
        this.carModelService = carModelService;
        this.carService = carService;
        this.userService = userService;
        this.favoriteCarService = favoriteCarService;
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
    public String saveCar(Model model,
                          @AuthenticationPrincipal User authUser,
                          @ModelAttribute("car_dto") SaveCarDto dto) {
        example.drew.carsales.persistence.entity.User user = userService.getByUsername(authUser.getUsername());
        if(!user.getActive()) {
            model.addAttribute("message", "Non-active user are not allowed to add cars! Please activate your account.");
            return "editor";
        }
        dto.setUser(user);
        carService.save(dto);
        return "redirect:/home";
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('user')")
    public String getCarDetails(Model model,
                                @PathVariable("id") Long id,
                                @AuthenticationPrincipal User authUser) {
        example.drew.carsales.persistence.entity.User user = userService.getByUsername(authUser.getUsername());
        Car car = carService.get(id);
        FavoriteCar favoriteCar = favoriteCarService.findByPersonAndCar(user, car).orElse(null);

        boolean isLiked = true;
        if(favoriteCar == null) {
            isLiked = false;
        }

        model.addAttribute("isLiked", isLiked);
        model.addAttribute("car", carService.get(id));
        return "details";
    }

    @PostMapping("/{id}/fav")
    @PreAuthorize(value = "hasAuthority('user')")
    public String addToFavorites(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal User authUser) {
        example.drew.carsales.persistence.entity.User user = userService.getByUsername(authUser.getUsername());
        Car car = carService.get(id);
        FavoriteCar favoriteCar = favoriteCarService.findByPersonAndCar(user, car).orElse(null);

        if(favoriteCar != null) {
            favoriteCarService.deleteByPerson_IdAndCar_Id(user.getId(), car.getId());
        } else {
            favoriteCar = new FavoriteCar(car, user);
            favoriteCarService.save(favoriteCar);
        }
        return "redirect:/cars/{id}";
    }

    @PostMapping("/{id}/remove")
    @PreAuthorize(value = "hasAuthority('user')")
    public String removeCar(@PathVariable("id") Long id,
                            @AuthenticationPrincipal User authUser) {
        Car car = carService.get(id);
        example.drew.carsales.persistence.entity.User user = userService.getByUsername(authUser.getUsername());
        if(car.getUser().equals(user)) {
            carService.delete(id);
            long carsSoldCount = user.getCarsSoldCount();
            user.setCarsSoldCount(++carsSoldCount);
            userService.update(user);
        }
        return "redirect:/home";
    }

}
