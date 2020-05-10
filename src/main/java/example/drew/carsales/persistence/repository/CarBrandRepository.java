package example.drew.carsales.persistence.repository;

import example.drew.carsales.persistence.entity.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {

    Optional<CarBrand> findByName(String name);

}
