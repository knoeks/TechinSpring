package lt.techin.povilas.kartojimas21.controller;


import lt.techin.povilas.kartojimas21.dto.CarMapper;
import lt.techin.povilas.kartojimas21.dto.CarRequestDTO;
import lt.techin.povilas.kartojimas21.dto.CarResponseDTO;
import lt.techin.povilas.kartojimas21.model.Car;
import lt.techin.povilas.kartojimas21.model.Rental;
import lt.techin.povilas.kartojimas21.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarController {
  private final CarService carService;

  public CarController(CarService carService) {
    this.carService = carService;
  }

  @GetMapping("/cars")
  public ResponseEntity<List<Car>> getCars() {
    return ResponseEntity.ok(carService.findAllCars());
  }

  @GetMapping("/cars/available")
  public ResponseEntity<List<Car>> getAvailableCars() {
    return ResponseEntity.ok(carService.findCarsByStatusAvailable());
  }

  @PostMapping("/cars")
  public ResponseEntity<CarResponseDTO> addCar(@RequestBody CarRequestDTO carRequestDTO) {
    Car savedCar = carService.saveCar(CarMapper.toCar(carRequestDTO));

    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedCar.getId())
                    .toUri()
    ).body(CarMapper.toCarResponseDTO(savedCar));
  }

  @DeleteMapping("/cars/{id}")
  public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
    Optional<Car> optionalCar = carService.findCarById(id);
    if (optionalCar.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    if ("RENTED".equals(optionalCar.get().getStatus())) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/cars/{id}")
  public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
    if (carService.existsCarById(id)) {
      Car carFromDb = carService.findCarById(id).get();

      carFromDb.setBrand(car.getBrand());
      carFromDb.setModel(car.getModel());
      carFromDb.setRentals(car.getRentals());
      carFromDb.setYear(car.getYear());
      carFromDb.setStatus(car.getStatus());

      Car savedCar = carService.saveCar(car);
      return ResponseEntity.ok(savedCar);
    }

    return ResponseEntity.notFound().build();
  }
}
