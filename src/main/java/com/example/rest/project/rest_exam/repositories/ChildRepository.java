package com.example.rest.project.rest_exam.repositories;

import com.example.rest.project.rest_exam.models.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Integer> {
}
