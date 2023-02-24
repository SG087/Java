package com.example.rest.project.rest_exam.repositories;

import com.example.rest.project.rest_exam.models.Employee;
import com.example.rest.project.rest_exam.models.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "projects-employees")
    List<Project> findAll();

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "projects-employees")
    Optional<Project> findById(Integer integer);
}
