package com.example.rest.project.rest_exam.service;

import com.example.rest.project.rest_exam.models.Employee;

import java.util.List;

public interface EmployeeProjectService {
    /**
     * Возвращает список работников и их проекты
     *
     * @return список работников и их проекты
     */
    List<Employee> findAllEmployeesAndProjects();

    /**
     * Возвращет работника по переданному ID и его проекты
     *
     * @param employeeId - ID работника
     * @return - работника и его проекты
     */
    Employee findAllEmployeesAndProjectsById(int employeeId);

    /**
     * Добавляет проект работнику
     *
     * @param employeeId - ID работника, которому нужно добавить проект
     * @param projectId  - ID проект, который нужно добавить работнику
     */
    void assignProjectToEmployee(int employeeId, int projectId);

    /**
     * Удаляет работника из проекта
     *
     * @param employeeId - ID работника, которого нужно удалить
     * @param projectId  - ID проекта, из которого нужно удалить работника
     */
    void removeAnEmployeeFromProject(int employeeId, int projectId);
}
