package example.drew.carsales.service.impl;

import example.drew.carsales.persistence.dto.car.CarCriteriaDto;
import example.drew.carsales.persistence.dto.car.SaveCarDto;
import example.drew.carsales.persistence.dto.car.UpdateCarDto;
import example.drew.carsales.persistence.entity.Car;
import example.drew.carsales.persistence.repository.CarBrandRepository;
import example.drew.carsales.persistence.repository.CarModelRepository;
import example.drew.carsales.persistence.repository.CarRepository;
import example.drew.carsales.service.CarService;
import example.drew.carsales.specification.CarSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarBrandRepository carBrandRepository;
    private final CarModelRepository carModelRepository;

    public CarServiceImpl(CarRepository carRepository, CarBrandRepository carBrandRepository, CarModelRepository carModelRepository) {
        this.carRepository = carRepository;
        this.carBrandRepository = carBrandRepository;
        this.carModelRepository = carModelRepository;
    }

    @Override
    public void save(SaveCarDto dto) {
        Car car = new Car();
        car.setCarBrand(carBrandRepository.findByName(dto.getBrand()).orElse(null));
        car.setCarModel(carModelRepository.findByName(dto.getModel()).orElse(null));
        car.setUser(dto.getUser());
        car.setKilometers(dto.getKilometers());
        car.setDescription(dto.getDescription());
        car.setBuild(LocalDate.parse(dto.getBuild()));
        carRepository.save(car);
    }

    @Override
    public void update(UpdateCarDto dto) {

    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public Car get(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Car> getAll(CarCriteriaDto criteria, Pageable pageable) {
        return carRepository.findAll(
                Specification
                        .where(CarSpecification.getCarsByBrand(criteria.getBrand()))
                        .and(CarSpecification.getCarsByModel(criteria.getModel()))
                        .and(CarSpecification.getCarsByOwner(criteria.getUsername()))
                        .and(CarSpecification.getCarsByKilometers(criteria.getKilometers()))
                        .and(CarSpecification.getCarsByBuild(criteria.getBuild())),
                pageable
        );
    }
}
