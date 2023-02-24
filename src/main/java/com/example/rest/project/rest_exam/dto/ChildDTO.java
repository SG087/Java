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
public class ChildDTO {

    @NotBlank(message = "The name field should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$",
            message = "The name must start with a capital letter and contain only Latin characters")
    private String name;
    @NotBlank(message = "The surname field should not be empty")
    @Size(min = 2, max = 30, message = "Surname should be between 2 and 30 characters")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$",
            message = "The surname must start with a capital letter and contain only Latin characters")
    private String surname;
    @Min(value = 0, message = "Age should be greater than 0")
    @Max(value = 40, message = "The age must be less than 40")
    private int age;
    @NotBlank(message = "Gender should not be empty")
    @Pattern(regexp = "^[a-zA-Z\\s]+", message = "Enter the correct gender")
    private String gender;
}
