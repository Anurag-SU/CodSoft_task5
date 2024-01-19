import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private List<Student> enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void enrollStudent(Student student) {
        if (enrolledStudents.size() < capacity) {
            enrolledStudents.add(student);
            System.out.println("Student " + student.getName() + " enrolled in " + title);
        } else {
            System.out.println("Course " + title + " is full. Cannot enroll more students.");
        }
    }

    public void removeStudent(Student student) {
        enrolledStudents.remove(student);
        System.out.println("Student " + student.getName() + " removed from " + title);
    }
}

class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
        course.enrollStudent(this);
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
        course.removeStudent(this);
    }
}

class CourseManager {
    private List<Course> courses;
    private List<Student> students;

    public CourseManager() {
        this.courses = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void displayCourseListing() {
        System.out.println("\nCourse Listing:");
        for (Course course : courses) {
            System.out.println(course.getCourseCode() + " - " + course.getTitle() +
                    " | Capacity: " + course.getEnrolledStudents().size() + "/" + course.getCapacity());
        }
    }

    public Student findStudent(String studentID) {
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                return student;
            }
        }
        return null;
    }

    public Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
}

public class InteractiveCourseManager {
    private static CourseManager courseManager = new CourseManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    displayCourseListing();
                    break;
                case 4:
                    registerStudent();
                    break;
                case 5:
                    dropCourse();
                    break;
                case 6:
                    System.out.println("Exiting. Thank you!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Course");
        System.out.println("2. Add Student");
        System.out.println("3. Display Course Listing");
        System.out.println("4. Register Student for Course");
        System.out.println("5. Drop Course for Student");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addCourse() {
        System.out.println("\nAdding Course:");
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Course Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character
        System.out.print("Enter Schedule: ");
        String schedule = scanner.nextLine();

        Course course = new Course(courseCode, title, description, capacity, schedule);
        courseManager.addCourse(course);
        System.out.println("Course added successfully!");
    }

    private static void addStudent() {
        System.out.println("\nAdding Student:");
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        Student student = new Student(studentID, name);
        courseManager.addStudent(student);
        System.out.println("Student added successfully!");
    }

    private static void displayCourseListing() {
        System.out.println("\nCourse Listing:");
        courseManager.displayCourseListing();
    }

    private static void registerStudent() {
        System.out.println("\nRegistering Student for Course:");
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();

        Student student = courseManager.findStudent(studentID);
        Course course = courseManager.findCourse(courseCode);

        if (student != null && course != null) {
            student.registerCourse(course);
        } else {
            System.out.println("Invalid Student ID or Course Code. Registration failed.");
        }
    }

    private static void dropCourse() {
        System.out.println("\nDropping Course for Student:");
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();

        Student student = courseManager.findStudent(studentID);
        Course course = courseManager.findCourse(courseCode);

        if (student != null && course != null) {
            student.dropCourse(course);
        } else {
            System.out.println("Invalid Student ID or Course Code. Dropping failed.");
        }
    }
}
