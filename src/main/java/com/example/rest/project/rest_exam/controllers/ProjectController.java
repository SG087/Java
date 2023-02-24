package com.example.rest.project.rest_exam.controllers;

import com.example.rest.project.rest_exam.dto.ProjectDTO;
import com.example.rest.project.rest_exam.models.Project;
import com.example.rest.project.rest_exam.service.ProjectService;
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
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<ProjectDTO> findAll() {
        return projectService.findAll()
                .stream()
                .map(this::convertToProjectDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProjectDTO findById(@PathVariable(name = "id") int id) {
        return convertToProjectDTO(projectService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createProject(@RequestBody @Valid ProjectDTO projectDTO,
                                                    BindingResult bindingResult) {
        showErrors(bindingResult);

        projectService.create(convertToProject(projectDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{employeeId}/{projectId}")
    public ResponseEntity<HttpStatus> addEmployeeTheProject(@PathVariable(name = "employeeId") int employeeId,
                                                            @PathVariable(name = "projectId") int projectId) {
        projectService.addEmployeeTheProject(employeeId, projectId);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateProject(@PathVariable(name = "id") int id,
                                                    @RequestBody @Valid ProjectDTO projectDTO,
                                                    BindingResult bindingResult) {
        showErrors(bindingResult);

        projectService.update(convertToProject(projectDTO), id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable(name = "id") int id) {
        projectService.delete(id);

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

    private ProjectDTO convertToProjectDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }

    private Project convertToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }
}
