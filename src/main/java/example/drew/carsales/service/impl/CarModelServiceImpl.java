package example.drew.carsales.service.impl;

import example.drew.carsales.persistence.entity.CarModel;
import example.drew.carsales.persistence.repository.CarModelRepository;
import example.drew.carsales.service.CarModelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarModelServiceImpl implements CarModelService {

    private final CarModelRepository modelRepository;

    public CarModelServiceImpl(CarModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public void save(CarModel carModel) {
        modelRepository.save(carModel);
    }

    @Override
    public List<CarModel> getAll() {
        return modelRepository.findAll();
    }
}
