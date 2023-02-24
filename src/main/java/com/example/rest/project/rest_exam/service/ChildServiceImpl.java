package com.example.rest.project.rest_exam.service;

import com.example.rest.project.rest_exam.models.Child;
import com.example.rest.project.rest_exam.models.Employee;
import com.example.rest.project.rest_exam.repositories.ChildRepository;
import com.example.rest.project.rest_exam.repositories.EmployeeRepository;
import com.example.rest.project.rest_exam.utils.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChildServiceImpl implements ChildService {

    private final ChildRepository childRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Child> findAll() {
        return childRepository.findAll();
    }

    @Override
    public Child findById(int id) {
        Optional<Child> child = childRepository.findById(id);

        return child.orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    @Transactional
    public void create(Child child) {
        childRepository.save(child);
    }

    @Override
    @Transactional
    public void addChildToEmployee(Child child, int employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ObjectNotFoundException::new);

        employee.getChildren().add(child);
        childRepository.save(child);
    }

    @Override
    @Transactional
    public void addChildToEmployeeById(int employeeId, int childId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ObjectNotFoundException::new);
        Child child = childRepository.findById(childId)
                .orElseThrow(ObjectNotFoundException::new);

        employee.getChildren().add(child);

        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void update(Child child, int id) {
        Optional<Child> newChild = childRepository.findById(id);

        if (newChild.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        child.setId(id);
        childRepository.save(child);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Optional<Child> child = childRepository.findById(id);

        if (child.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        childRepository.deleteById(id);
    }
}
