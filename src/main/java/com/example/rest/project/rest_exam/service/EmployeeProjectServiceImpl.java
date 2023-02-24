package com.example.rest.project.rest_exam.service;

import com.example.rest.project.rest_exam.models.Employee;
import com.example.rest.project.rest_exam.models.Project;
import com.example.rest.project.rest_exam.repositories.EmployeeRepository;
import com.example.rest.project.rest_exam.repositories.ProjectRepository;
import com.example.rest.project.rest_exam.utils.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployeeProjectServiceImpl implements EmployeeProjectService {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    @Override
    public List<Employee> findAllEmployeesAndProjects() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findAllEmployeesAndProjectsById(int employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        return employee.orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    @Transactional
    public void assignProjectToEmployee(int employeeId, int projectId) {
        Set<Project> projectList = null;
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ObjectNotFoundException::new);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(ObjectNotFoundException::new);

        projectList = employee.getProjects();
        projectList.add(project);
        employee.setProjects(projectList);

        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void removeAnEmployeeFromProject(int employeeId, int projectId) {
        Set<Employee> employeeList = null;
        Set<Project> projectList = null;
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ObjectNotFoundException::new);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(ObjectNotFoundException::new);

        employeeList = project.getEmployees();
        employeeList.removeIf(element -> element.equals(employee));
        project.setEmployees(employeeList);

        projectList = employee.getProjects();
        projectList.removeIf(element -> element.equals(project));
        employee.setProjects(projectList);
    }
}
