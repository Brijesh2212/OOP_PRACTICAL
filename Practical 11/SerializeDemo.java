import java.io.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    int age;

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return name + " | " + age;
    }
}

public class SerializeDemo {

    public static void main(String[] args) throws Exception {
        Student[] students = {
            new Student("Alice", 20),
            new Student("Bob", 22),
            new Student("Charlie", 21)
        };

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            oos.writeObject(students);
            System.out.println("Saved " + students.length + " students.");
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            Student[] loaded = (Student[]) ois.readObject();
            System.out.println("Loaded students:");
            for (Student s : loaded) {
                System.out.println(s);
            }
        }
    }
}
