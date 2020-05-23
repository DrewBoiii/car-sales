package example.drew.carsales.persistence.repository;

import example.drew.carsales.persistence.entity.Car;
import example.drew.carsales.persistence.entity.FavoriteCar;
import example.drew.carsales.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteCarRepository extends JpaRepository<FavoriteCar, Long> {

    void deleteByPerson_IdAndCar_Id(Long personId, Long carId);

    Optional<FavoriteCar> findByPersonAndCar(User person, Car car);

}