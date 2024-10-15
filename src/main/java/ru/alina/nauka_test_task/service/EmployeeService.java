package ru.alina.nauka_test_task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alina.nauka_test_task.entity.Employee;
import ru.alina.nauka_test_task.exceptions.UserNotFoundException;
import ru.alina.nauka_test_task.repo.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<String> groupByName() {
        return employeeRepository.groupByName();
    }

    public List<Employee> findBetween(LocalDate start, LocalDate end) {
        return employeeRepository.findBetween(start, end);
    }
    @Transactional
    public void addEmployee(String name, String surname, LocalDate birthday, String department, double salary) {
         employeeRepository.addEmployee(name, surname, birthday, department, salary);
    }
    @Transactional
    public Optional<Employee> updateEmployee(Long id, String name, String surname, LocalDate birthday, String department, Double salary) {
        Double wrappedSalary = (salary != 0) ? salary : null;
        int updatedRows = employeeRepository.updateEmployee(id, name, surname, birthday, department, wrappedSalary);
        if (updatedRows > 0) {
            return employeeRepository.findById(id);
        } else {
            return Optional.empty();
        }
    }
    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.deleteEmployeeById(id);
    }

}
