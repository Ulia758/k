package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private List<Employee> employees;

    public Main(String filename) {
        employees = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                String name = parts[0];
                String dolgn = parts[1];
                double salary = Double.parseDouble(parts[2]);
                employees.add(new Employee(name, dolgn, salary));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> searchByName(String name) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getName().equalsIgnoreCase(name)) {
                result.add(employee);
            }
        }
        return result;
    }

    public List<Employee> searchByDolgn(String dolgn) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getDolgn().equalsIgnoreCase(dolgn)) {
                result.add(employee);
            }
        }
        return result;
    }

    public List<Employee> searchBySalary(double salary) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getSalary() == salary) {
                result.add(employee);
            }
        }
        return result;
    }
    public static void main(String[] args) {
        String filename = "employees.txt";
        Main management = new Main(filename);

        List<Employee> employeesByName = management.searchByName("Александр");
        System.out.println("\nРаботники с именем Александр: ");
        for (Employee employee : employeesByName) {
            System.out.println(employee.getName() + " - " + employee.getDolgn() + " - " + employee.getSalary());
        }

        List<Employee> employeesByDolgn = management.searchByDolgn("Программист");
        System.out.println("\nРаботники с должностью программист:");
        for (Employee employee : employeesByDolgn) {
            System.out.println(employee.getName() + " - " + employee.getDolgn() + " - " + employee.getSalary());
        }

        List<Employee> employeesBySalary = management.searchBySalary(67000.0);
        System.out.println("\nРаботники с зарплатой 67000:");
        for (Employee employee : employeesBySalary) {
            System.out.println(employee.getName() + " - " + employee.getDolgn() + " - " + employee.getSalary());
        }
    }
}

class Employee {
    private String name;
    private String dolgn;
    private double salary;

    public Employee(String name, String dolgn, double salary) {
        this.name = name;
        this.dolgn = dolgn;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getDolgn() {
        return dolgn;
    }

    public double getSalary() {
        return salary;
    }
}
