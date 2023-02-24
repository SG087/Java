package com.example.rest.project.rest_exam.models;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
@Transactional
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "children")
public class Child {
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
    @Min(value = 0, message = "Age should be greater than 0")
    @Max(value = 40, message = "The age must be less than 40")
    private int age;
    @NotBlank(message = "Gender should not be empty")
    @Pattern(regexp = "^[a-zA-Z\\s]+", message = "Enter the correct gender")
    private String gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Child child = (Child) o;
        return id == child.id
                && age == child.age
                && Objects.equals(name, child.name)
                && Objects.equals(surname, child.surname)
                && Objects.equals(gender, child.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, age, gender);
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
