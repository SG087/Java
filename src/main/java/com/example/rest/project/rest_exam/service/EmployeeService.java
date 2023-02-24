package com.example.rest.project.rest_exam.service;

import com.example.rest.project.rest_exam.models.Employee;

import java.util.List;

public interface EmployeeService {
    /**
     * Возвращает список всех имеющихся работников
     *
     * @return список работников
     */
    List<Employee> findAll();

    /**
     * Возвращает работника по его ID
     *
     * @param id - ID работника
     * @return - объект работника с заданным ID
     */
    Employee findById(int id);

    /**
     * Создает нового работника
     *
     * @param employee - объект работника для создания
     */
    void create(Employee employee);

    /**
     * Обновляет работника с заданным ID,
     * в соответсвии с переданным работником
     *
     * @param employee - работник, в соответствии с которым нужно обновить данные
     * @param id       - ID работника, которого нужно обновить
     */
    void update(Employee employee, int id);

    /**
     * Удаляет работника с заданным ID,
     *
     * @param id - ID работника, которого нужно удалить
     */
    void delete(int id);
}
