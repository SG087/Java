package com.example.rest.project.rest_exam.controllers;

import com.example.rest.project.rest_exam.dto.ChildDTO;
import com.example.rest.project.rest_exam.models.Child;
import com.example.rest.project.rest_exam.service.ChildService;
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
@RequestMapping("/children")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<ChildDTO> findAll() {
        return childService.findAll()
                .stream()
                .map(this::convertToChildDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ChildDTO findById(@PathVariable(name = "id") int id) {
        return convertToChildDTO(childService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createChild(@RequestBody @Valid ChildDTO childDTO,
                                                  BindingResult bindingResult) {
        showErrors(bindingResult);

        childService.create(convertToChild(childDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{employeeId}")
    public ResponseEntity<HttpStatus> addChildToEmployee(@PathVariable(name = "employeeId") int employeeId,
                                                         @RequestBody @Valid ChildDTO childDTO,
                                                         BindingResult bindingResult) {
        showErrors(bindingResult);

        childService.addChildToEmployee(convertToChild(childDTO), employeeId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{employeeId}/{childId}")
    public ResponseEntity<HttpStatus> addChildToEmployeeById(@PathVariable(name = "employeeId") int employeeId,
                                                             @PathVariable(name = "childId") int childId) {
        childService.addChildToEmployeeById(employeeId, childId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateChild(@PathVariable(name = "id") int id,
                                                  @RequestBody @Valid ChildDTO childDTO,
                                                  BindingResult bindingResult) {
        showErrors(bindingResult);

        childService.update(convertToChild(childDTO), id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteChild(@PathVariable(name = "id") int id) {
        childService.delete(id);

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

    private ChildDTO convertToChildDTO(Child child) {
        return modelMapper.map(child, ChildDTO.class);
    }

    private Child convertToChild(ChildDTO childDTO) {
        return modelMapper.map(childDTO, Child.class);
    }
}
