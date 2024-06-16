package springboot_university.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MsgCode {

    OOPS_ERROR(500, "oops-error"),
    UNIVERSITY_NOT_FOUND(404, "university-not-found"),
    PROGRAM_NOT_FOUND(404, "program-not-found"),
    NO_STUDENT_ENROLLED_IN_COURSE(404,"no-students-enrolled-in-course"),
    USER_NOT_FOUND(404, "user-not-found");

    private final Integer code;
    private final String languageKey;
}