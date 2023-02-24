package com.example.rest.project.rest_exam.service;

import com.example.rest.project.rest_exam.models.Project;

import java.util.List;

public interface ProjectService {
    /**
     * Возвращает список всех проектов
     *
     * @return список проектов
     */
    List<Project> findAll();
    /**
     * Возвращает ребенка по ID
     *
     * @param id - ID ребенка
     * @return - объект ребенка с заданным ID
     */
    Project findById(int id);
    /**
     * Создает новый проект
     *
     * @param project - объект project для создания
     */
    void create(Project project);
    /**
     * Добавляет проект работнику по ID
     *
     * @param employeeId - ID работника
     * @param projectId    - ID проекта
     */
    void addEmployeeTheProject(int employeeId, int projectId);
    /**
     * Обновляет проект с заданным ID,
     * в соответсвии с переданным объектом project
     *
     * @param project - объект, в соответствии с которым нужно обновить данные
     * @param id    - ID проекта, который нужно обновить
     */
    void update(Project project, int id);
    /**
     * Удаляет проект c заданным ID
     *
     * @param id - ID проекта, который нужно удалить
     */
    void delete(int id);

}
