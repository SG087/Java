package com.example.rest.project.rest_exam.controllers;

import com.example.rest.project.rest_exam.dto.DetailsDTO;
import com.example.rest.project.rest_exam.models.Details;
import com.example.rest.project.rest_exam.service.DetailsService;
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
@RequestMapping("/details")
@RequiredArgsConstructor
public class DetailsController {

    private final DetailsService detailsService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<DetailsDTO> findAll() {
        return detailsService.findAll()
                .stream()
                .map(this::convertToDetailsDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DetailsDTO findById(@PathVariable(name = "id") int id) {
        return convertToDetailsDTO(detailsService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createDetails(@RequestBody @Valid DetailsDTO detailsDTO,
                                                    BindingResult bindingResult) {
        showErrors(bindingResult);

        detailsService.create(convertToDetails(detailsDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{employeeId}")
    public ResponseEntity<HttpStatus> addDetailsToEmployee(@PathVariable(name = "employeeId") int employeeId,
                                                           @RequestBody @Valid DetailsDTO detailsDTO,
                                                           BindingResult bindingResult) {
        showErrors(bindingResult);

        detailsService.addDetailsToEmployee(convertToDetails(detailsDTO), employeeId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{employeeId}/{detailsId}")
    public ResponseEntity<HttpStatus> addDetailsToEmployeeById(@PathVariable(name = "employeeId") int employeeId,
                                                               @PathVariable(name = "detailsId") int detailsId) {
        detailsService.addDetailsToEmployeeById(employeeId, detailsId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateDetails(@PathVariable(name = "id") int id,
                                                    @RequestBody @Valid DetailsDTO detailsDTO,
                                                    BindingResult bindingResult) {
        showErrors(bindingResult);

        detailsService.update(convertToDetails(detailsDTO), id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDetails(@PathVariable(name = "id") int id) {
        detailsService.delete(id);

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

    private DetailsDTO convertToDetailsDTO(Details details) {
        return modelMapper.map(details, DetailsDTO.class);
    }

    private Details convertToDetails(DetailsDTO detailsDTO) {
        return modelMapper.map(detailsDTO, Details.class);
    }
}
