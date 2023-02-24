package com.example.rest.project.rest_exam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectDTO {

    @NotBlank(message = "The name project field should not be empty")
    @Size(min = 2, max = 30, message = "Name project should be between 2 and 30 characters")
    private String nameProject;

    private List<EmployeeDTOForTheProject> employees = new ArrayList<>();
}
