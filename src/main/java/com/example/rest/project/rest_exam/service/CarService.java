package com.example.rest.project.rest_exam.service;

import com.example.rest.project.rest_exam.models.Car;

import java.util.List;


public interface CarService {
    /**
     * Возвращает список всех машин
     *
     * @return список машин
     */
    List<Car> findAll();

    /**
     * Возвращает машину по ID
     *
     * @param id - ID машины
     * @return - объект машины с заданным ID
     */
    Car findById(int id);

    /**
     * Создает новоую машину
     *
     * @param car - объект car для создания
     */
    void create(Car car);

    /**
     * Создает машину и добавляет её работнику
     *
     * @param car        - объект car для создания
     * @param employeeId - ID работника, к которому нужно добавить переданный объект
     */
    void addCarToEmployee(Car car, int employeeId);

    /**
     * Добавляет машину работнику по ID
     *
     * @param employeeId - ID работника
     * @param carId      - ID машины
     */
    void addCarToEmployeeById(int employeeId, int carId);

    /**
     * Обновляет машину с заданным ID,
     * в соответсвии с переданным объектом car
     *
     * @param car - объект, в соответствии с которым нужно обновить данные
     * @param id  - ID машины, которую нужно обновить
     */
    void update(Car car, int id);

    /**
     * Удаляет машину c заданным ID
     *
     * @param id - ID машины, которую нужно удалить
     */
    void delete(int id);
}
