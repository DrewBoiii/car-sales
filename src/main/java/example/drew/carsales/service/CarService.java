package example.drew.carsales.service;

import example.drew.carsales.persistence.dto.car.CarCriteriaDto;
import example.drew.carsales.persistence.dto.car.SaveCarDto;
import example.drew.carsales.persistence.dto.car.UpdateCarDto;
import example.drew.carsales.persistence.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {

    void save(SaveCarDto dto);
    void update(UpdateCarDto dto);
    void delete(Long id);
    Car get(Long id);
    Page<Car> getAll(CarCriteriaDto criteria, Pageable pageable);

}
