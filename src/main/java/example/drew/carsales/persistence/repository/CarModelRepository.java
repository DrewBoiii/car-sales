package example.drew.carsales.persistence.repository;

import example.drew.carsales.persistence.entity.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {

    Optional<CarModel> findByName(String name);

}
