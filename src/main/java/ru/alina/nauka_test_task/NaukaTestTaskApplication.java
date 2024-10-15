package ru.alina.nauka_test_task;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;
import ru.alina.nauka_test_task.command.EmployeeCommand;


@SpringBootApplication
@RequiredArgsConstructor
public class NaukaTestTaskApplication implements CommandLineRunner, ExitCodeGenerator {
    private final EmployeeCommand employeeCommand;
    private final IFactory factory;
    private int exitCode;

    @Override
    public void run(String... args) throws Exception {
        exitCode = new CommandLine(employeeCommand, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(NaukaTestTaskApplication.class, args)));
    }

}
