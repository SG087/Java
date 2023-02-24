package com.example.rest.project.rest_exam.controllers;

import com.example.rest.project.rest_exam.dto.EmployeeDTO;
import com.example.rest.project.rest_exam.models.Employee;
import com.example.rest.project.rest_exam.service.EmployeeService;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;


    @GetMapping()
    public List<EmployeeDTO> findAll() {
        return employeeService.findAll()
                .stream()
                .map(this::convertToEmployeeDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeDTO findById(@PathVariable(name = "id") int id) {
        return convertToEmployeeDTO(employeeService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO,
                                                     BindingResult bindingResult) {

        showErrors(bindingResult);

        employeeService.create(convertToEmployee(employeeDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateEmployee(@PathVariable(name = "id") int id,
                                                     @RequestBody @Valid EmployeeDTO employeeDTO,
                                                     BindingResult bindingResult) {
        showErrors(bindingResult);

        employeeService.update(convertToEmployee(employeeDTO), id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable(name = "id") int id) {
        employeeService.delete(id);

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

    private EmployeeDTO convertToEmployeeDTO(Employee employee) {
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    private Employee convertToEmployee(EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeDTO, Employee.class);
    }
}
