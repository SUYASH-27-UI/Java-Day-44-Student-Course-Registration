import java.util.*;
import java.io.*;

class Registration {
    String studentName;
    String courseName;

    Registration(String studentName, String courseName) {
        this.studentName = studentName;
        this.courseName = courseName;
    }

    void display() {
        System.out.println(
            "Student: " + studentName +
            " | Course: " + courseName
        );
    }
}

public class Main {

    static ArrayList<Registration> registrations =
        new ArrayList<>();

    static final String FILE_NAME = "registrations.csv";

    static void loadData() {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                return;
            }

            Scanner reader = new Scanner(file);

            if (reader.hasNextLine()) {
                reader.nextLine();
            }

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split(",", -1);

                if (data.length == 2) {
                    registrations.add(
                        new Registration(data[0], data[1])
                    );
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error loading data!");
        }
    }

    static void saveData() {
        try {
            FileWriter writer = new FileWriter(FILE_NAME);

            writer.write("StudentName,CourseName\n");

            for (Registration r : registrations) {
                writer.write(
                    r.studentName + "," +
                    r.courseName + "\n"
                );
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving data!");
        }
    }

    static void registerStudent(Scanner sc) {
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter Course Name: ");
        String course = sc.nextLine().trim();

        if (name.isEmpty() || course.isEmpty()
                || name.contains(",") || course.contains(",")) {
            System.out.println("Invalid name or course!");
            return;
        }

        for (Registration r : registrations) {
            if (r.studentName.equalsIgnoreCase(name)
                    && r.courseName.equalsIgnoreCase(course)) {
                System.out.println("Already registered!");
                return;
            }
        }

        registrations.add(new Registration(name, course));
        saveData();

        System.out.println("Registration successful!");
    }

    static void showRegistrations() {
        if (registrations.isEmpty()) {
            System.out.println("No registrations found.");
            return;
        }

        System.out.println("\n--- All Registrations ---");

        for (Registration r : registrations) {
            r.display();
        }
    }

    static void searchStudent(Scanner sc) {
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine().trim();

        boolean found = false;

        for (Registration r : registrations) {
            if (r.studentName.equalsIgnoreCase(name)) {
                r.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Registration not found!");
        }
    }

    static void showSummary() {
        System.out.println(
            "Total Registrations: " + registrations.size()
        );
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        loadData();

        while (true) {
            System.out.println("\n===== COURSE REGISTRATION =====");
            System.out.println("1. Register Student");
            System.out.println("2. Show All Registrations");
            System.out.println("3. Search Student");
            System.out.println("4. Registration Summary");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    registerStudent(sc);
                    break;

                case "2":
                    showRegistrations();
                    break;

                case "3":
                    searchStudent(sc);
                    break;

                case "4":
                    showSummary();
                    break;

                case "5":
                    System.out.println("Program closed.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
