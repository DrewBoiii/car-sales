package example.drew.carsales.config;

import example.drew.carsales.persistence.entity.CarBrand;
import example.drew.carsales.persistence.entity.CarModel;
import example.drew.carsales.persistence.entity.Role;
import example.drew.carsales.service.CarBrandService;
import example.drew.carsales.service.CarModelService;
import example.drew.carsales.service.RoleService;
import example.drew.carsales.util.RoleConstant;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class ApplicationConfig {

    private final RoleService roleService;
    private final CarBrandService brandService;
    private final CarModelService modelService;

    public ApplicationConfig(RoleService roleService, CarBrandService brandService, CarModelService modelService) {
        this.roleService = roleService;
        this.brandService = brandService;
        this.modelService = modelService;
    }

    @PostConstruct
    public void init() {
        addRoles();
        addBrandsAndModels();
    }

    private void addRoles() {
        if(roleService.getAll().isEmpty()) {
            Role userRole = new Role(RoleConstant.USER_ROLE);
            Role moderRole = new Role(RoleConstant.MODER_ROLE);
            Role adminRole = new Role(RoleConstant.ADMIN_ROLE);
            roleService.save(userRole);
            roleService.save(moderRole);
            roleService.save(adminRole);
        }
    }

    private void addBrandsAndModels() {
        if(brandService.getAll().isEmpty()) {
            CarModel model1 = new CarModel("Polo");
            CarModel model2 = new CarModel("Tiguan");
            CarModel model3 = new CarModel("Golf");

            modelService.save(model1);
            modelService.save(model2);
            modelService.save(model3);

            CarBrand brand1 = new CarBrand();
            brand1.setName("VW");

            Set<CarModel> models = new HashSet<>();
            models.add(model1);
            models.add(model2);
            models.add(model3);

            brand1.setCarModels(models);

            brandService.save(brand1);
        }
    }

}
