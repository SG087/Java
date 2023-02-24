package com.example.rest.project.rest_exam.models;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Transactional
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
@NamedEntityGraph(name = "employees-details-children-cars-projects",
        attributeNodes = {
                @NamedAttributeNode("details"),
                @NamedAttributeNode("children"),
                @NamedAttributeNode("cars"),
                @NamedAttributeNode("projects")
        })
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
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
    @Min(value = 22, message = "Age should be greater than 22")
    @Max(value = 65, message = "The age must be less than 65")
    private int age;
    @NotBlank(message = "Gender should not be empty")
    @Pattern(regexp = "^[a-zA-Z\\s]+", message = "Enter the correct gender")
    private String gender;
    @NotBlank(message = "The department field should not be empty")
    private String department;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    private Details details;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Set<Child> children;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Set<Car> cars;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id
                && age == employee.age
                && Objects.equals(name, employee.name)
                && Objects.equals(surname, employee.surname)
                && Objects.equals(gender, employee.gender)
                && Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, age, gender, department);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", department='" + department + '\'' +
                ", details=" + details +
                ", children=" + children +
                ", cars=" + cars +
                ", projects=" + projects +
                '}';
    }
}
