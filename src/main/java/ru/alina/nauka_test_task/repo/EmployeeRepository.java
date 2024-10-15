package ru.alina.nauka_test_task.repo;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alina.nauka_test_task.entity.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Modifying
    @Query(value = "INSERT INTO employee (name, surname, birthday, department, salary) VALUES (:name, :surname, :birthday, :department, :salary)", nativeQuery = true)
    void addEmployee(@Param("name") String name,
                     @Param("surname") String surname,
                     @Param("birthday") LocalDate birthday,
                     @Param("department") String department,
                     @Param("salary") double salary);

    @Query(value = "SELECT * FROM employee WHERE id=:empId", nativeQuery = true)
    Optional<Employee> findById(@Param("empId") Long id);

    @Query(value = "SELECT * FROM employee", nativeQuery = true)
    List<Employee> findAll();

    @Modifying
    @Query(value = "DELETE FROM employee WHERE id=:empId", nativeQuery = true)
    void deleteEmployeeById(@Param("empId") Long id);

    @Query(value = "SELECT name, COUNT(*) AS EmployeeCount FROM employee GROUP BY name", nativeQuery = true)
    List<String> groupByName();

    @Query(value = "SELECT * FROM employee WHERE birthday BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Employee> findBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE employee SET " +
            "name = COALESCE(:name, name), " +
            "surname = COALESCE(:surname, surname), " +
            "birthday = COALESCE(:birthday, birthday), " +
            "department = COALESCE(:department, department), " +
            "salary = COALESCE(:salary, salary) " +
            "WHERE id = :id", nativeQuery = true)
    int updateEmployee(@Param("id") Long id,
                       @Param("name") String name,
                       @Param("surname") String surname,
                       @Param("birthday") LocalDate birthday,
                       @Param("department") String department,
                       @Param("salary") Double salary);


}
