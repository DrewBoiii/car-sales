package example.drew.carsales.service.impl;

import example.drew.carsales.persistence.entity.CarBrand;
import example.drew.carsales.persistence.repository.CarBrandRepository;
import example.drew.carsales.service.CarBrandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarBrandServiceImpl implements CarBrandService {

    private final CarBrandRepository brandRepository;

    public CarBrandServiceImpl(CarBrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public void save(CarBrand carBrand) {
        brandRepository.save(carBrand);
    }

    @Override
    public List<CarBrand> getAll() {
        return brandRepository.findAll();
    }

}
