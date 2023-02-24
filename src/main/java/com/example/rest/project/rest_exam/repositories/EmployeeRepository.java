package com.example.rest.project.rest_exam.repositories;

import com.example.rest.project.rest_exam.models.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
    value = "employees-details-children-cars-projects")
    List<Employee> findAll();

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "employees-details-children-cars-projects")
    Optional<Employee> findById(Integer integer);
}
