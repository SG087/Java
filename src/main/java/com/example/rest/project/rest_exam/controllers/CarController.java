package com.example.rest.project.rest_exam.controllers;

import com.example.rest.project.rest_exam.dto.CarDTO;
import com.example.rest.project.rest_exam.models.Car;
import com.example.rest.project.rest_exam.service.CarService;
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
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<CarDTO> findAll() {
        return carService.findAll()
                .stream()
                .map(this::convertToCarDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CarDTO findById(@PathVariable(name = "id") int id) {
        return convertToCarDTO(carService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createCar(@RequestBody @Valid CarDTO carDTO,
                                                BindingResult bindingResult) {
        showErrors(bindingResult);

        carService.create(covertToCar(carDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{employeeId}")
    public ResponseEntity<HttpStatus> addCarToEmployee(@PathVariable(name = "employeeId") int employeeId,
                                                       @RequestBody @Valid CarDTO carDTO,
                                                       BindingResult bindingResult) {
        showErrors(bindingResult);

        carService.addCarToEmployee(covertToCar(carDTO), employeeId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{employeeId}/{carId}")
    public ResponseEntity<HttpStatus> addCarToEmployeeById(@PathVariable(name = "employeeId") int employeeId,
                                                           @PathVariable(name = "carId") int carId) {
        carService.addCarToEmployeeById(employeeId, carId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCar(@PathVariable(name = "id") int id,
                                                @RequestBody @Valid CarDTO carDTO,
                                                BindingResult bindingResult) {
        showErrors(bindingResult);

        carService.update(covertToCar(carDTO), id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable(name = "id") int id) {
        carService.delete(id);

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

    private CarDTO convertToCarDTO(Car car) {
        return modelMapper.map(car, CarDTO.class);
    }

    private Car covertToCar(CarDTO carDTO) {
        return modelMapper.map(carDTO, Car.class);
    }
}
