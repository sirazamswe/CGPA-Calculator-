import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Course {

    String name;
    double credit;
    String grade;
    //constructor 
    Course(String name, double credit, String grade) {
        this.name = name;
        this.credit = credit;
        this.grade = grade;
    }

    double getGradePoint() {
        switch (grade.toUpperCase()) {
            case "A+": return 4.00;
            case "A": return 3.75;
            case "A-": return 3.50;
            case "B+": return 3.25;
            case "B": return 3.00;
            case "B-": return 2.75;
            case "C+": return 2.50;
            case "C": return 2.25;
            case "D": return 2.00;
            case "F": return 0.00;
            default: return 0.00;
        }
    }
}

public class GradeManagementSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean again = true;

        while (again) {

            //Student Information
            String studentName;
            while (true) {
                System.out.print("Student Name: ");
                studentName = sc.nextLine().trim();
                if (!studentName.isEmpty()) break;
                System.out.println("Name cannot be empty.");
            }

            String studentID;
            while (true) {
                System.out.print("Student ID: ");
                studentID = sc.nextLine().trim();
                if (!studentID.isEmpty()) break;
                System.out.println("ID cannot be empty.");
            }

            String semester;
            while (true) {
                System.out.print("Semester: ");
                semester = sc.nextLine().trim();
                if (!semester.isEmpty()) break;
                System.out.println("Semester cannot be empty.");
            }

            //Number of Courses
            int n;

            while (true) {
                System.out.print("Enter number of courses: ");
                if (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Enter a number.");
                    sc.next();
                    continue;
                }
                n = sc.nextInt();
                sc.nextLine();
                if (n <= 0) {
                    System.out.println("Number of courses must be greater than 0.");
                    continue;
                }
                break;
            }

            Course[] courses = new Course[n];

            // Courses Input
            for (int i = 0; i < n; i++) {

                System.out.println("\nCourse " + (i + 1));

                String name;
                while (true) {
                    System.out.print("Course Name: ");
                    name = sc.nextLine().trim();
                    if (!name.isEmpty()) break;
                    System.out.println("Course name cannot be empty.");
                }

                double credit;
                while (true) {
                    System.out.print("Course Credit (1-4): ");
                    if (!sc.hasNextDouble()) {
                        System.out.println("Invalid credit.");
                        sc.next();
                        continue;
                    }
                    credit = sc.nextDouble();
                    sc.nextLine();
                    if (credit < 1 || credit > 4) {
                        System.out.println("Credit must be between 1 and 4.");
                        continue;
                    }
                    break;
                }

                String grade;
                while (true) {
                    System.out.print("Grade (A+, A, A-, B+, B, B-, C+, C, D, F): ");
                    grade = sc.nextLine().toUpperCase();
                    if (grade.equals("A+") || grade.equals("A") || grade.equals("A-") ||
                        grade.equals("B+") || grade.equals("B") || grade.equals("B-") ||
                        grade.equals("C+") || grade.equals("C") ||
                        grade.equals("D") || grade.equals("F")) break;
                    System.out.println("Invalid grade. Please enter a valid grade.");
                }

                courses[i] = new Course(name, credit, grade);
            }

            //  Calculations
            double totalPoints = 0, totalCredits = 0;

            System.out.println("\n-----------------------------------------");
            System.out.println("Student: " + studentName);
            System.out.println("ID: " + studentID);
            System.out.println("Semester: " + semester);
            System.out.println("-----------------------------------------");
            System.out.printf("%-15s %-8s %-8s %-10s\n", "Course", "Credit", "Grade", "GP");
            System.out.println("---------------------------------------------");

            String fileData = "";
            fileData += "Student: " + studentName + "\n";
            fileData += "ID: " + studentID + "\n";
            fileData += "Semester: " + semester + "\n";

            // Save Date and Time
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateTime.format(formatter);
            fileData += "Date: " + timestamp + "\n";
            fileData += "---------------------------------------------\n";

            for (Course c : courses) {
                double gp = c.getGradePoint();
                System.out.printf("%-15s %-8.1f %-8s %-10.2f\n",
                        c.name, c.credit, c.grade, gp);
                totalPoints += gp * c.credit;
                totalCredits += c.credit;
                fileData += String.format("%-15s %-8.1f %-8s %-10.2f\n",
                        c.name, c.credit, c.grade, gp);
            }

            System.out.println("---------------------------------------------");

            double cgpa = totalPoints / totalCredits;
            System.out.printf("Final CGPA = %.2f\n", cgpa);
            fileData += "Final CGPA = " + String.format("%.2f", cgpa) + "\n";

            // CGPA Classification
            String classification;
            if (cgpa >= 3.75) classification = "Outstanding";
            else if (cgpa >= 3.50) classification = "Excellent";
            else if (cgpa >= 3.00) classification = "Good";
            else if (cgpa >= 2.00) classification = "Pass";
            else classification = "Fail";

            System.out.println("Classification: " + classification);
            fileData += "Classification: " + classification + "\n";

            // ================= Save to File =================
            try {
                FileWriter writer = new FileWriter("result.txt", true);
                writer.write(fileData + "\n");
                writer.close();
                System.out.println("Result saved to result.txt");
            } catch (IOException e) {
                System.out.println("Error saving file.");
            }

            // ================= Restart Option =================
            System.out.print("\nDo you want to calculate again? (Y/N): ");
            String answer = sc.nextLine();
            if (!answer.equalsIgnoreCase("Y")) {
                again = false;
                System.out.println("Program Ended.");
            }
        }

        sc.close();
    }
}
