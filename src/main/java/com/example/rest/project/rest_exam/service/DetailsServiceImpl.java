package com.example.rest.project.rest_exam.service;

import com.example.rest.project.rest_exam.models.Details;
import com.example.rest.project.rest_exam.models.Employee;
import com.example.rest.project.rest_exam.repositories.DetailsRepository;
import com.example.rest.project.rest_exam.repositories.EmployeeRepository;
import com.example.rest.project.rest_exam.utils.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetailsServiceImpl implements DetailsService {

    private final DetailsRepository detailsRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Details> findAll() {
        return detailsRepository.findAll();
    }

    @Override
    public Details findById(int id) {
        Optional<Details> details = detailsRepository.findById(id);

        return details.orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    @Transactional
    public void create(Details details) {
        detailsRepository.save(details);
    }

    @Override
    @Transactional
    public void addDetailsToEmployee(Details details, int employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ObjectNotFoundException::new);

        employee.setDetails(details);

        detailsRepository.save(details);
    }

    @Override
    @Transactional
    public void addDetailsToEmployeeById(int employeeId, int detailsId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ObjectNotFoundException::new);
        Details details = detailsRepository.findById(detailsId)
                .orElseThrow(ObjectNotFoundException::new);

        employee.setDetails(details);

        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void update(Details details, int id) {
        Optional<Details> newDetails = detailsRepository.findById(id);

        if (newDetails.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        details.setId(id);
        detailsRepository.save(details);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Optional<Details> details = detailsRepository.findById(id);

        if (details.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        detailsRepository.deleteById(id);
    }
}
