package example.drew.carsales.service;

import example.drew.carsales.persistence.entity.CarModel;

import java.util.List;

public interface CarModelService {

    void save(CarModel carModel);
    List<CarModel> getAll();

}
