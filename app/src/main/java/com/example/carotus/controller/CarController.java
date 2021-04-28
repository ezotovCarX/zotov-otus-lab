package com.example.carotus.controller;

import com.example.carotus.domain.Car;
import com.example.carotus.domain.CarDto;
import com.example.carotus.repo.CarRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 27.04.2021
 * Контроллер управления автомобилями
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/cars", produces = APPLICATION_JSON_VALUE)
public class CarController {
    private final CarRepo carRepo;
    private final ModelMapper mapper = new ModelMapper();

    /**
     * Создать авто
     *
     * @param carDto дто авто
     * @return dto авто
     */
    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        return Optional.ofNullable(carDto)
                .map(c -> mapper.map(c, Car.class))
                .map(carRepo::save)
                .map(c -> mapper.map(c, CarDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Получить авто по ид
     *
     * @param carId ид авто
     * @return dto авто
     */
    @GetMapping("{carId}")
    public ResponseEntity<CarDto> getByIdCar(@PathVariable("carId") Long carId) {
        return carRepo.findById(carId)
                .map(c -> mapper.map(c, CarDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Рeдактировать авто по ид
     *
     * @param carId  ид авто
     * @param carDto dto авто
     * @return dto авто
     */
    @PutMapping("{carId}")
    public ResponseEntity<CarDto> updateByIdCar(@PathVariable("carId") Long carId, @RequestBody CarDto carDto) {
        if (carDto == null || !carDto.getId().equals(carId)) {
            return ResponseEntity.badRequest().build();
        }

        return carRepo.findById(carId)
                .map(updateFieldCar(carDto))
                .map(carRepo::save)
                .map(c -> mapper.map(c, CarDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Удалить авто по ид
     *
     * @param carId ид авто
     * @return dto авто
     */
    @DeleteMapping("{carId}")
    public ResponseEntity<Void> deleteByIdCar(@PathVariable("carId") Long carId) {
        carRepo.deleteById(carId);

        return ResponseEntity.ok().build();
    }

    /**
     * Получить все авто
     *
     * @return dto авто
     */
    @GetMapping("all")
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<CarDto> listCar = carRepo.findAll().stream()
                .map(c -> mapper.map(c, CarDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listCar);
    }

    /**
     * Обновление полей авто
     *
     * @param carDto дто авто
     */
    private Function<Car, Car> updateFieldCar(CarDto carDto) {
        return car -> {
            car.setName(carDto.getName());
            car.setPower(carDto.getPower());
            car.setMaxSpeed(carDto.getMaxSpeed());

            return car;
        };
    }
}
