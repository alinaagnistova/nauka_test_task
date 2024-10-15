package ru.alina.nauka_test_task.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.alina.nauka_test_task.entity.Employee;
import picocli.CommandLine.*;
import ru.alina.nauka_test_task.service.EmployeeService;


import java.time.LocalDate;
import java.util.Optional;


@Component
@RequiredArgsConstructor
@Command(name = "employee", description = "Manage employees", mixinStandardHelpOptions = true)
public class EmployeeCommand implements Runnable {
    private final EmployeeService employeeService;

    @Override
    public void run() {
        System.out.println("\033[31mUse --help to see available commands\033[0m");
    }

    @Command(name = "create", description = "Create a new employee")
    public void createCommand(
            @Parameters(paramLabel = "name", description = "Employee's name") String name,
            @Parameters(paramLabel = "surname", description = "Employee's surname") String surname,
            @Option(names = {"-b", "--birthday"}, description = "Employee's birthday (YYYY-MM-DD)") LocalDate birthday,
            @Option(names = {"-d", "--department"}, description = "Employee's department") String department,
            @Option(names = {"-s", "--salary"}, description = "Employee's salary") double salary) {
        System.out.println("\033[31mCreating employee with name: " + name + ", surname: " + surname + "\033[0m");
        employeeService.addEmployee(name, surname, birthday, department, salary);
        System.out.println("\033[31mEmployee created: " + name + "\033[0m");

    }

    @Command(name = "update", description = "Update an existing employee")
    public void updateCommand(
            @Parameters(paramLabel = "id", description = "Employee's ID") Long id,
            @Option(names = {"-n", "--name"}, description = "Employee's name") String name,
            @Option(names = {"-s", "--surname"}, description = "Employee's surname") String surname,
            @Option(names = {"-b", "--birthday"}, description = "Employee's birthday (YYYY-MM-DD)") LocalDate birthday,
            @Option(names = {"-d", "--department"}, description = "Employee's department") String department,
            @Option(names = {"-sal", "--salary"}, description = "Employee's salary") double salary) {
        Optional<Employee> updatedEmployee = employeeService.updateEmployee(id, name, surname, birthday, department, salary);
        updatedEmployee.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("\033[31mEmployee not found!\033[0m")
        );


    }

    @Command(name = "read", description = "Read employee by ID or all employees (with --all)")
    public void readCommand(
            @Option(names = {"-a", "--all"}, description = "Read all employees") boolean all,
            @Parameters(paramLabel = "id", description = "Employee ID", arity = "0..1") Long id) {
        if (all) {
            System.out.println("\033[31m" + employeeService.findAll() + "\033[0m");
        } else if (id != null) {
            System.out.println("\033[31m" + employeeService.findById(id) + "\033[0m");
        } else {
            System.out.println("\033[31mProvide an ID or use the --all option\033[0m");
        }

    }

    @Command(name = "delete", description = "Delete an employee by ID")
    public void deleteCommand(@Parameters(paramLabel = "id", description = "Employee ID") Long id) {
        employeeService.deleteEmployee(id);
        System.out.println("\033[31mEmployee deleted: " + id + "\033[0m");

    }

    @Command(name = "group", description = "Group employees by name")
    public void groupByNameCommand() {
        System.out.println("\033[31m" + employeeService.groupByName() + "\033[0m");
    }

    @Command(name = "find_between", description = "Find employees with birthdays in range [start_date;end_date]")
    public void findBetweenCommand(
            @Parameters(paramLabel = "start_date", description = "Left boundary for search") LocalDate startDate,
            @Parameters(paramLabel = "end_date", description = "Right boundary for search") LocalDate endDate) {
        if (startDate.isBefore(endDate)) {
            System.out.println("\033[31m" + employeeService.findBetween(startDate, endDate) + "\033[0m");
        } else {
            System.out.println("\033[31mStart date should be before end date\033[0m");
        }
    }
}
