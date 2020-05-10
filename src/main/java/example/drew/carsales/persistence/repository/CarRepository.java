package example.drew.carsales.persistence.repository;

import example.drew.carsales.persistence.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {



}
