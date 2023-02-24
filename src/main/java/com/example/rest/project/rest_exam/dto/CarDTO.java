package com.example.rest.project.rest_exam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarDTO {

    @NotBlank(message = "The manufacturer field should not be empty")
    @Size(min = 3, max = 30, message = "Manufacturer should be between 3 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+", message = "Enter the correct manufacturer")
    private String manufacturer;
    @NotBlank(message = "The model field should not be empty")
    @Size(min = 2, max = 30, message = "Model should be between 3 and 30 characters")
    private String model;
    @NotBlank(message = "The colour field should not be empty")
    @Size(min = 4, max = 30, message = "Colour should be between 4 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+", message = "Enter the correct colour")
    private String colour;
    @Min(value = 1980, message = "Enter the correct year of release")
    @Max(value = 2023, message = "Enter the correct year of release")
    private int yearOfRelease;
    @Min(value = 70, message = "Enter the correct horsepower")
    @Max(value = 999, message = "Enter the correct horsepower")
    private int horsepower;
    @Min(value = 0, message = "Enter the correct engine capacity")
    @Max(value = 9, message = "Enter the correct engine capacity")
    private double engineCapacity;
}
