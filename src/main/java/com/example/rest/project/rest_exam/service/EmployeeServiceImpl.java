package com.example.rest.project.rest_exam.service;

import com.example.rest.project.rest_exam.models.Employee;
import com.example.rest.project.rest_exam.repositories.EmployeeRepository;
import com.example.rest.project.rest_exam.utils.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }


    @Override
    public Employee findById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        return employee.orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    @Transactional
    public void create(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void update(Employee employee, int id) {
        Employee newEmployee = employeeRepository.findById(id)
                .orElseThrow(ObjectNotFoundException::new);

        newEmployee.setId(id);
        newEmployee.setName(employee.getName());
        newEmployee.setSurname(employee.getSurname());
        newEmployee.setDepartment(employee.getDepartment());
        newEmployee.setAge(employee.getAge());
        newEmployee.setDetails(employee.getDetails());
    }

    @Override
    @Transactional
    public void delete(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        employeeRepository.deleteById(id);
    }
}
