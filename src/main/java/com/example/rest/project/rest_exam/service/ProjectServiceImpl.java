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
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findById(int id) {
        Optional<Project> project = projectRepository.findById(id);

        return project.orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    @Transactional
    public void create(Project project) {
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public void addEmployeeTheProject(int employeeId, int projectId) {
        Set<Employee> employeeList = null;
        Set<Project> projectList = null;
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ObjectNotFoundException::new);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(ObjectNotFoundException::new);

        employeeList = project.getEmployees();
        employeeList.add(employee);
        project.setEmployees(employeeList);

        projectList = employee.getProjects();
        projectList.add(project);
        employee.setProjects(projectList);

        projectRepository.save(project);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void update(Project project, int id) {
        Optional<Project> newProject = projectRepository.findById(id);

        if (newProject.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        project.setId(id);
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Optional<Project> newProject = projectRepository.findById(id);

        if (newProject.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        projectRepository.deleteById(id);
    }
}
