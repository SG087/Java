package com.example.rest.project.rest_exam.service;

import com.example.rest.project.rest_exam.models.Car;
import com.example.rest.project.rest_exam.models.Employee;
import com.example.rest.project.rest_exam.repositories.CarRepository;
import com.example.rest.project.rest_exam.repositories.EmployeeRepository;
import com.example.rest.project.rest_exam.utils.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findById(int id) {
        Optional<Car> car = carRepository.findById(id);

        return car.orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    @Transactional
    public void create(Car car) {
        carRepository.save(car);
    }

    @Override
    @Transactional
    public void addCarToEmployee(Car car, int employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ObjectNotFoundException::new);

        employee.getCars().add(car);
        carRepository.save(car);
    }

    @Override
    @Transactional
    public void addCarToEmployeeById(int employeeId, int carId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ObjectNotFoundException::new);
        Car car = carRepository.findById(carId)
                .orElseThrow(ObjectNotFoundException::new);

        employee.getCars().add(car);

        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void update(Car car, int id) {
        Optional<Car> newCar = carRepository.findById(id);

        if (newCar.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        car.setId(id);
        carRepository.save(car);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Optional<Car> car = carRepository.findById(id);

        if (car.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        carRepository.deleteById(id);
    }
}
