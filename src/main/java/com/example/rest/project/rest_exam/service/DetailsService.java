package com.example.rest.project.rest_exam.service;

import com.example.rest.project.rest_exam.models.Details;

import java.util.List;

public interface DetailsService {

    /**
     * Возвращает список всех details
     *
     * @return список деталей
     */
    List<Details> findAll();

    /**
     * Возвращает details по ID
     *
     * @param id - ID деталей
     * @return - объект details с заданным ID
     */
    Details findById(int id);

    /**
     * Создает новый объкт details
     *
     * @param details - объект details для создания
     */
    void create(Details details);

    /**
     * Создает объект details и добавляет его работнику
     *
     * @param details    - объект details для создания
     * @param employeeId - ID работника, к которому нужно добавить details
     */
    void addDetailsToEmployee(Details details, int employeeId);

    /**
     * Добавляет объект details работнику по ID
     *
     * @param employeeId - ID работника
     * @param detailsId  - ID details
     */
    void addDetailsToEmployeeById(int employeeId, int detailsId);

    /**
     * Обновляет details с заданным ID,
     * в соответсвии с переданным details
     *
     * @param details - details, в соответствии с которым нужно обновить данные
     * @param id      - ID details, который нужно обновить
     */
    void update(Details details, int id);

    /**
     * Удаляет details c заданным ID
     *
     * @param id - ID details, которую нужно удалить
     */
    void delete(int id);
}
