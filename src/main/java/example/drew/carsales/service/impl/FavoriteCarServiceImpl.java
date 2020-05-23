package example.drew.carsales.service.impl;

import example.drew.carsales.persistence.entity.Car;
import example.drew.carsales.persistence.entity.FavoriteCar;
import example.drew.carsales.persistence.entity.User;
import example.drew.carsales.persistence.repository.FavoriteCarRepository;
import example.drew.carsales.service.FavoriteCarService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteCarServiceImpl implements FavoriteCarService {

    private final FavoriteCarRepository favoriteCarRepository;

    public FavoriteCarServiceImpl(FavoriteCarRepository favoriteCarRepository) {
        this.favoriteCarRepository = favoriteCarRepository;
    }

    @Override
    public void save(FavoriteCar favorites) {
        favoriteCarRepository.save(favorites);
    }

    @Override
    public void deleteByPerson_IdAndCar_Id(Long personId, Long carId) {
        favoriteCarRepository.deleteByPerson_IdAndCar_Id(personId, carId);
    }

    @Override
    public Optional<FavoriteCar> findByPersonAndCar(User person, Car car) {
        return favoriteCarRepository.findByPersonAndCar(person, car);
    }

}
