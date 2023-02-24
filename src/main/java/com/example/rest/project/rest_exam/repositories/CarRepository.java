package com.example.rest.project.rest_exam.repositories;

import com.example.rest.project.rest_exam.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {

}
