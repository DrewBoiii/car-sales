package example.drew.carsales.controller;

import example.drew.carsales.persistence.dto.car.CarCriteriaDto;
import example.drew.carsales.service.CarBrandService;
import example.drew.carsales.service.CarModelService;
import example.drew.carsales.service.CarService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;

@Controller
public class MainController {

    public static final int ITEMS_PER_PAGE = 5;

    private final CarBrandService carBrandService;
    private final CarModelService carModelService;
    private final CarService carService;

    public MainController(CarBrandService carBrandService, CarModelService carModelService, CarService carService) {
        this.carBrandService = carBrandService;
        this.carModelService = carModelService;
        this.carService = carService;
    }

    @GetMapping("/")
    public String redirectToIndex() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getIndexPage(Model model,
                               @ModelAttribute("criteria") CarCriteriaDto criteria,
                               @PageableDefault(value = ITEMS_PER_PAGE) Pageable pageable) {
        Sort sort = Sort.by(criteria.getSort()).descending();
        if(criteria.getOrder().equals("asc")) {
            sort = Sort.by(criteria.getSort()).ascending();
        }
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        model.addAttribute("page", carService.getAll(criteria, pageRequest));
        model.addAttribute("brands", carBrandService.getAll());
        model.addAttribute("models", carModelService.getAll());
        model.addAttribute("date", LocalDateTime.now());

        return "index";
    }

}
