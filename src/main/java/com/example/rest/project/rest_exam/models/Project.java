package com.example.rest.project.rest_exam.models;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Transactional
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
@NamedEntityGraph(
        name = "projects-employees",
        attributeNodes = @NamedAttributeNode("employees")
)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotBlank(message = "The name project field should not be empty")
    @Size(min = 2, max = 30, message = "Name project should be between 2 and 30 characters")
    private String nameProject;
    @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id && Objects.equals(nameProject, project.nameProject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameProject);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", nameProject='" + nameProject + '\'' +
                '}';
    }
}
