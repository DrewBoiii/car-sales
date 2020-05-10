package example.drew.carsales.service;

import example.drew.carsales.persistence.entity.CarBrand;

import java.util.List;

public interface CarBrandService {

    void save(CarBrand carBrand);
    List<CarBrand> getAll();

}
