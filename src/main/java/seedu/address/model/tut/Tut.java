package seedu.address.model.tut;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.student.Name;
import seedu.address.model.student.Student;


/**
 * Represents a Tutorial in the address book.
 * Guarantees: details are present and not null
 */
public class Tut {

    public static final String MESSAGE_CONSTRAINTS = "Tutorial names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    private final List<Student> students = new ArrayList<>();

    // TODO: Insert TutDate
    private final String tutName;
    /**
     * Constructs a {@code Tut}.
     *
     * @param tutName A valid tutorial name.
     */
    public Tut(String tutName) {
        requireNonNull(tutName);
        checkArgument(isValidName(tutName), MESSAGE_CONSTRAINTS);
        this.tutName = tutName;
    }
    void add(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
        }
    }

    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getTutName() {
        return this.tutName;
    }
    Student get(Name name) {
        return students.stream()
                    .filter(student -> student.getName().equals(name))
                    .findFirst()
                    .orElse(null); // Returns null if no student is found
    }
}
