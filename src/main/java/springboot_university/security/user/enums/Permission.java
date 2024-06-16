package springboot_university.security.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    UNIVERSITY_CREATE("UNIVERSITY:CREATE"),
    UNIVERSITY_READ("UNIVERSITY:READ"),
    UNIVERSITY_UPDATE("UNIVERSITY:UPDATE"),
    UNIVERSITY_DELETE("UNIVERSITY:DELETE"),

    STUDENT_CREATE("STUDENT:CREATE"),
    STUDENT_READ("STUDENT:READ"),
    STUDENT_UPDATE("STUDENT:UPDATE"),
    STUDENT_DELETE("STUDENT:DELETE");

    private final String permissions;
}