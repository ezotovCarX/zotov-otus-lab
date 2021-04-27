package com.example.carotus.repo;

import com.example.carotus.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Created by ZotovES on 27.04.
 * Репозиторий автомобилей
 */
@Repository
public interface CarRepo extends JpaRepository<Car, Long> {
}
