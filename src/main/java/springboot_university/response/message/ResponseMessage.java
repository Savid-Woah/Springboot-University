package springboot_university.response.message;

public class ResponseMessage {

    public static final String COURSE_ADDED = "Curso añadido exitosamente!";
    public static final String PROGRAM_ADDED = "Programa añadido exitosamente!";
    public static final String STUDENT_REGISTERED = "Estudiante registrado exitosamente!";
    public static final String STUDENT_ENROLLED = "Student enrolled in course successfully!";
    public static final String UNIVERSITY_REGISTERED = "Universidad registrada exitosamente!";
    public static final String UNIVERSITY_AVG_HIGHER_THAN_COURSE_AVG =
            """
            The university's students age average is higher than the course's students age average
            """;
    public static final String COURSE_AVG_HIGHER_THAN_UNIVERSITY_AVG =
            """
            The course's students age average is higher than the university's students age average
            """;
    public static final String COURSE_AVG_EQUAL_TO_UNIVERSITY_AVG =
            """
            The course's students age average is equal to the university's students age average
            """;
    public static final String UNIVERSITY_AVG_HIGHER_THAN_PROGRAM_AVG =
            """
            The university's students age average is higher than the program's students age average
            """;
    public static final String PROGRAM_AVG_HIGHER_THAN_UNIVERSITY_AVG =
            """
            The program's students age average is higher than the university's students age average
            """;
    public static final String PROGRAM_AVG_EQUAL_TO_UNIVERSITY_AVG =
            """
            The program's students age average is equal to the university's students age average
            """;
}