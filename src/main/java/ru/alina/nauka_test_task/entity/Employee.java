package ru.alina.nauka_test_task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthday;
    private String department;
    private double salary;
}
