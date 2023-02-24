package com.example.rest.project.rest_exam.service;

import com.example.rest.project.rest_exam.models.Child;

import java.util.List;

public interface ChildService {
    /**
     * Возвращает список всех детей
     *
     * @return список деталей
     */
    List<Child> findAll();

    /**
     * Возвращает ребенка по ID
     *
     * @param id - ID ребенка
     * @return - объект ребенка с заданным ID
     */
    Child findById(int id);

    /**
     * Создает нового ребенка
     *
     * @param child - объект child для создания
     */
    void create(Child child);

    /**
     * Создает ребенка и добавляет его работнику
     *
     * @param child      - объект child для создания
     * @param employeeId - ID работника, к которому нужно добавить переданный объект
     */

    void addChildToEmployee(Child child, int employeeId);

    /**
     * Добавляет ребенка работнику по ID
     *
     * @param employeeId - ID работника
     * @param childId    - ID ребенка
     */
    void addChildToEmployeeById(int employeeId, int childId);

    /**
     * Обновляет ребенка с заданным ID,
     * в соответсвии с переданным объектом child
     *
     * @param child - объект, в соответствии с которым нужно обновить данные
     * @param id    - ID ребенка, которого нужно обновить
     */
    void update(Child child, int id);

    /**
     * Удаляет ребенка c заданным ID
     *
     * @param id - ID ребенка, которого нужно удалить
     */
    void delete(int id);
}
