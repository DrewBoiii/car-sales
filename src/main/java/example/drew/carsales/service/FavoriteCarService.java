package example.drew.carsales.service;

import example.drew.carsales.persistence.dto.car.CarCriteriaDto;
import example.drew.carsales.persistence.entity.Car;
import example.drew.carsales.persistence.entity.FavoriteCar;
import example.drew.carsales.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface FavoriteCarService {

    @Transactional
    void save(FavoriteCar favorites);

    @Transactional
    void deleteByPerson_IdAndCar_Id(Long personId, Long carId);

    Optional<FavoriteCar> findByPersonAndCar(User person, Car car);

    Page<Car> getFavoriteCarsByUsername(CarCriteriaDto dto, Pageable pageable);

}
