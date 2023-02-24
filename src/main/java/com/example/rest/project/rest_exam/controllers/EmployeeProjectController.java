package com.example.rest.project.rest_exam.controllers;

import com.example.rest.project.rest_exam.dto.EmployeeProjectDTO;
import com.example.rest.project.rest_exam.models.Employee;
import com.example.rest.project.rest_exam.service.EmployeeProjectService;
import com.example.rest.project.rest_exam.utils.ErrorResponse;
import com.example.rest.project.rest_exam.utils.ObjectNotCreatedException;
import com.example.rest.project.rest_exam.utils.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee_project")
@RequiredArgsConstructor
public class EmployeeProjectController {


    private final EmployeeProjectService employeeProjectService;

    private final ModelMapper modelMapper;

    @GetMapping()
    public List<EmployeeProjectDTO> getAllEmployeesWithProjects() {
        return employeeProjectService.findAllEmployeesAndProjects()
                .stream()
                .map(this::convertToEmployeeProjectDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{employeeId}")
    public EmployeeProjectDTO getAllEmployeesWithProjectsById(@PathVariable(name = "employeeId") int employeeId) {
        return convertToEmployeeProjectDTO(employeeProjectService.findAllEmployeesAndProjectsById(employeeId));
    }

    @PutMapping("/{employeeId}/{projectId}")
    public ResponseEntity<HttpStatus> assignProjectToEmployee(
            @PathVariable(name = "employeeId") int employeeId,
            @PathVariable(name = "projectId") int projectId) {
        employeeProjectService.assignProjectToEmployee(employeeId, projectId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}/delete/{projectId}")
    public ResponseEntity<HttpStatus> removeAnEmployeeFromProject(
            @PathVariable(name = "employeeId") int employeeId,
            @PathVariable(name = "projectId") int projectId) {

        employeeProjectService.removeAnEmployeeFromProject(employeeId, projectId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void showErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessage
                        .append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new ObjectNotCreatedException(errorMessage.toString());
        }
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ObjectNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(ObjectNotFoundException e) {
        ErrorResponse response = new ErrorResponse("Object with this id wasn't found!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private EmployeeProjectDTO convertToEmployeeProjectDTO(Employee employee) {
        return modelMapper.map(employee, EmployeeProjectDTO.class);
    }

    private Employee convertToEmployee(EmployeeProjectDTO employeeProjectDTO) {
        return modelMapper.map(employeeProjectDTO, Employee.class);
    }
}
