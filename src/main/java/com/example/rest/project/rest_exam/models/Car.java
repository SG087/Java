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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id
                && yearOfRelease == car.yearOfRelease
                && horsepower == car.horsepower
                && Double.compare(car.engineCapacity, engineCapacity) == 0
                && Objects.equals(manufacturer, car.manufacturer)
                && Objects.equals(model, car.model)
                && Objects.equals(colour, car.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, manufacturer, model, colour, yearOfRelease, horsepower, engineCapacity);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", colour='" + colour + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", horsepower=" + horsepower +
                ", engineCapacity=" + engineCapacity +
                '}';
    }
}
