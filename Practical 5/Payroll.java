abstract class Employee {

    private String name;
    private String id;

    public Employee(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    abstract double monthlySalary();
}

class FullTime extends Employee {

    private double salary;

    public FullTime(String name, String id, double salary) {
        super(name, id);
        this.salary = salary;
    }

    @Override
    double monthlySalary() {
        return salary;
    }
}

class PartTime extends Employee {

    private int hours;
    private double rate;

    public PartTime(String name, String id, int hours, double rate) {
        super(name, id);
        this.hours = hours;
        this.rate = rate;
    }

    @Override
    double monthlySalary() {
        return hours * rate;
    }
}

class Intern extends Employee {

    private double stipend;

    public Intern(String name, String id, double stipend) {
        super(name, id);
        this.stipend = stipend;
    }

    @Override
    double monthlySalary() {
        return stipend;
    }
}

public class Payroll {

    public static void main(String[] args) {
        Employee[] employees = {
                new FullTime("Rahul", "E001", 50000),
                new PartTime("Priya", "E002", 80, 300),
                new Intern("Amit", "E003", 15000),
                new FullTime("Neha", "E004", 45000)
        };

        double total = 0;

        for (Employee employee : employees) {
            double salary = employee.monthlySalary();
            System.out.println(employee.getId() + " - " + employee.getName() + " - Salary: " + salary);
            total += salary;

            if (employee instanceof Intern) {
                System.out.println("Note: This employee is an Intern.");
            }
        }

        System.out.println("Total Payroll: " + total);
    }
}
