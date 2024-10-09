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
    private final Integer id;
    /**
     * Constructs a {@code Tut}.
     *
     * @param tutName A valid tutorial name.
     */
    public Tut(String tutName, int id) {
        requireNonNull(id);
        requireNonNull(tutName);
        checkArgument(isValidName(tutName), MESSAGE_CONSTRAINTS);
        this.tutName = tutName;
        this.id = id;
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tut)) {
            return false;
        }

        Tut otherTutorial = (Tut) other;
        return id.equals(otherTutorial.id)
                && tutName.equals(otherTutorial.tutName)
                && students.equals(otherTutorial.students);
    }
    @Override
    public String toString() {
        return tutName + ": Tutorial" + id;
    }
}
