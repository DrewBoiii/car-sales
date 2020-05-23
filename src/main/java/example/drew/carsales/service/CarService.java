package example.drew.carsales.service;

import example.drew.carsales.persistence.dto.car.CarCriteriaDto;
import example.drew.carsales.persistence.dto.car.SaveCarDto;
import example.drew.carsales.persistence.dto.car.UpdateCarDto;
import example.drew.carsales.persistence.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface CarService {

    @Transactional
    void save(SaveCarDto dto);

    @Transactional
    void update(UpdateCarDto dto);

    @Transactional
    void delete(Long id);

    Car get(Long id);
    Page<Car> getAll(CarCriteriaDto criteria, Pageable pageable);

}
