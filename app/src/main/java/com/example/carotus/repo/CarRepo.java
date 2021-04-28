package com.example.carotus.repo;

import com.example.carotus.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Created by ZotovES on 27.04.
 * Репозиторий автомобилей
 */
@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
    /**
     * Поиск авто по внешнему ид
     *
     * @param carId внешний ид авто
     * @return авто
     */
    Optional<Car> findByCarId(UUID carId);
}
