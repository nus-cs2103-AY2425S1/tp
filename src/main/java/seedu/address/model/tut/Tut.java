package seedu.address.model.tut;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialClass;
import seedu.address.model.tut.exceptions.TutDateNotFoundException;

/**
 * Represents a Tutorial in the address book.
 * Guarantees: details are present and not null
 */
public class Tut {

    // Constraints messages for name and ID
    public static final String MESSAGE_NAME_CONSTRAINTS = "Tutorial names should only contain alphanumeric"
            + " characters and spaces, "
            + "and it should not be blank.";
    public static final String MESSAGE_ID_CONSTRAINTS = TutorialClass.MESSAGE_CONSTRAINTS;

    // Example validation regex for tutorial name (customize as needed)
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    private final List<Student> students = new ArrayList<>();

    // TODO: Insert TutDate
    private final String tutName;
    private final TutorialClass tutorialClass;
    private List<TutDate> tutDates = new ArrayList<>();

    /**
     * Constructs a {@code Tut}.
     *
     * @param tutName A valid tutorial name.
     */
    public Tut(String tutName, TutorialClass tutorialClass) {
        requireNonNull(tutorialClass);
        requireNonNull(tutName);
        checkArgument(isValidName(tutName), MESSAGE_NAME_CONSTRAINTS);
        this.tutName = tutName;
        this.tutorialClass = tutorialClass;
    }

    /**
     * * Adds student to the tutorial
     **/
    public void add(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
        }
    }
    public List<Student> getStudents() {
        return this.students;
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
    public boolean tutorialDateInList(TutDate tutorialDate) {
        return tutDates.contains(tutorialDate);
    }
    public void addTutorialDate(TutDate tutorialDate) {
        tutDates.add(tutorialDate);
    }
    public TutDate getTutorialDate(Integer id) {
        return tutDates.get(id);
    }
    public boolean isValidTutorialDate(TutDate tutorialDate) {
        return tutorialDate.isValid();
    }
    public List<TutDate> getTutDates() {
        return tutDates;
    }

    /**
     *  Marks the attendance for the student for the particular tutorialDate
     */
    public void markAttendance(Student student, TutDate tutorialDate) throws TutDateNotFoundException {
        if (!isValidTutorialDate(tutorialDate)) {
            throw new TutDateNotFoundException();
        }
        if (!studentInList(student)) {
            add(student);
        }
        if (!tutorialDateInList(tutorialDate)) {
            addTutorialDate(tutorialDate);
        }
        tutorialDate.add(student);
    }
    private boolean studentInList(Student student) {
        return students.contains(student);
    }
    /**
     * Checks if two tutorials have the same id and name, primarily used for checking duplicates.
     */
    //TODO: Check duplicates
    public boolean equalsTutorial(Tut other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tut)) {
            return false;
        }

        Tut otherTutorial = (Tut) other;
        return tutorialClass.equals(otherTutorial.tutorialClass)
                && tutDates.equals(otherTutorial.tutDates)
                && tutName.equals(otherTutorial.tutName)
                && students.equals(otherTutorial.students);
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
        return tutorialClass.equals(otherTutorial.tutorialClass)
                && tutDates.equals(otherTutorial.tutDates)
                && tutName.equals(otherTutorial.tutName)
                && students.equals(otherTutorial.students);
    }
    @Override
    public String toString() {
        return tutName + ": Tutorial " + tutorialClass;
    }
}
