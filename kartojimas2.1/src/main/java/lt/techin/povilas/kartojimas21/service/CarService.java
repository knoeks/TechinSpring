package lt.techin.povilas.kartojimas21.service;

import lt.techin.povilas.kartojimas21.model.Car;
import lt.techin.povilas.kartojimas21.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> findCarById(Long id) {
        return carRepository.findById(id);
    }

    public boolean existsCarById(Long id) {
        return carRepository.existsById(id);
    }

    // potential bug (comes from CarsRepository) **fixed**
    public List<Car> findCarsByStatusAvailable() {
        return carRepository.findByStatus("AVAILABLE");
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }
}
