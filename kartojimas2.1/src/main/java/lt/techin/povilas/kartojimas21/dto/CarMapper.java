package lt.techin.povilas.kartojimas21.dto;

import lt.techin.povilas.kartojimas21.model.Car;

import java.util.List;

public class CarMapper {
  public static CarRequestDTO toCarRequestDTO(Car car) {
    return new CarRequestDTO(
            car.getBrand(),
            car.getModel(),
            car.getYear()
    );
  }

  public static CarResponseDTO toCarResponseDTO(Car car) {
    return new CarResponseDTO(
            car.getId(),
            car.getBrand(),
            car.getModel(),
            car.getYear(),
            car.getStatus()
    );
  }

  //
//      this.id = id;
//    this.brand = brand;
//    this.model = model;
//    this.year = year;
//    this.status = status;
//    this.rentals = rentals;
  public static Car toCar(CarRequestDTO carRequestDTO) {
    return new Car(
            null,
            carRequestDTO.brand(),
            carRequestDTO.model(),
            carRequestDTO.year(),
            "AVAILABLE",
            null
    );
  }

}
