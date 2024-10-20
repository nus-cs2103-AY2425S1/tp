package seedu.academyassist.model.person;

public enum SubjectEnum {
    ENGLISH("English"),
    MATH("Math"),
    CHINESE("Chinese"),
    SCIENCE("Science");

    private final String subjectName;

    SubjectEnum(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public static boolean isValidSubject(String subject) {
        for (SubjectEnum s : values()) {
            if (s.getSubjectName().equalsIgnoreCase(subject)) {
                return true;
            }
        }
        return false;
    }

    public static SubjectEnum fromString(String subject) {
        for (SubjectEnum s : values()) {
            if (s.getSubjectName().equalsIgnoreCase(subject)) {
                return s;
            }
        }
        throw new IllegalArgumentException("No such subject: " + subject);
    }
}