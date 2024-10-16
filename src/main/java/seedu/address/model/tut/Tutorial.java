package seedu.address.model.tut;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialClass;
import seedu.address.model.tut.exceptions.TutDateNotFoundException;
/**
 * Represents a Tutorial in the address book.
 * Guarantees: details are present and not null
 */
public class Tutorial {

    // Constraints messages for name and ID
    public static final String MESSAGE_NAME_CONSTRAINTS = "Tutorial names should only contain alphanumeric"
            + " characters and spaces, "
            + "and it should not be blank.";
    public static final String MESSAGE_ID_CONSTRAINTS = TutorialClass.MESSAGE_CONSTRAINTS;

    private final List<Student> students = new ArrayList<>();
    private final HashMap<Date, TutDate> tutDates = new HashMap<>();
    private final TutName tutName;
    private final TutorialClass tutorialClass;

    /**
     * Constructs a {@code Tut}.
     *
     * @param tutName A valid tutorial name.
     */
    public Tutorial(TutName tutName, TutorialClass tutorialClass) {
        requireNonNull(tutorialClass);
        requireNonNull(tutName);
        this.tutName = tutName;
        this.tutorialClass = tutorialClass;
    }

    /**
     * Constructs a {@code Tut}.
     *
     * @param tutName A valid tutorial name.
     */
    public Tutorial(TutName tutName, String tutClass) {
        requireNonNull(tutClass);
        requireNonNull(tutName);
        this.tutorialClass = new TutorialClass(tutClass);
        this.tutName = tutName;
    }

    /**
     * Adds a student to the tutorial.
     */
    public void add(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
        }
    }

    public List<Student> getStudents() {
        return this.students;
    }

    /**
     * Sets the attendance for the given student on a specific date.
     */
    public boolean setAttendance(Date date, StudentId target) {
        requireNonNull(date);
        requireNonNull(target);
        TutDate tutDate = tutDates.computeIfAbsent(date, TutDate::new);
        return students.stream()
                .filter(s -> s.getStudentId().equals(target))
                .findFirst()
                .map(student -> {
                    student.setAttendance(tutDate);
                    tutDate.add(target);
                    return true;
                }).orElse(false);
    }

    public TutName getTutName() {
        return this.tutName;
    }

    public Student get(Name name) {
        return students.stream()
                .filter(student -> student.getName().equals(name))
                .findFirst()
                .orElse(null); // Returns null if no student is found
    }

    public boolean tutorialDateInList(TutDate date) {
        return tutDates.containsKey(date.getDate());
    }

    /**
     * Adds a tutorial date to the tutorial.
     */
    public void addTutorialDate(TutDate tutorialDate) {
        requireNonNull(tutorialDate);
        tutDates.putIfAbsent(tutorialDate.getDate(), tutorialDate);
    }

    public TutDate getTutorialDate(Date date) {
        return tutDates.get(date);
    }

    public boolean isValidTutorialDate(TutDate tutorialDate) {
        return tutorialDate.isValid();
    }

    public List<TutDate> getTutDates() {
        return new ArrayList<>(tutDates.values());
    }

    public TutorialClass getTutorialClass() {
        return tutorialClass;
    }

    /**
     * Marks the attendance for the student for the particular tutorial date.
     */
    public void markAttendance(Student student, TutDate tutorialDate) throws TutDateNotFoundException {
        if (!isValidTutorialDate(tutorialDate)) {
            throw new TutDateNotFoundException();
        }
        if (!studentInList(student)) {
            add(student);
        }
        addTutorialDate(tutorialDate);
        tutorialDate.add(student.getStudentId());
    }

    private boolean studentInList(Student student) {
        return students.contains(student);
    }

    /**
     * Checks if two tutorials have the same id and name, primarily used for checking duplicates.
     */
    //    public boolean equalsTutorial(Tut other) {
    //        if (other == this) {
    //            return true;
    //        }
    //
    //        if (!(other instanceof Tut)) {
    //            return false;
    //        }
    //
    //        Tut otherTutorial = (Tut) other;
    //        return tutorialClass.equals(otherTutorial.tutorialClass)
    //                && tutDates.equals(otherTutorial.tutDates)
    //                && tutName.equals(otherTutorial.tutName)
    //                && students.equals(otherTutorial.students);
    //    }
    //TODO: Use the equalsTutorial function to remove duplicates

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Tutorial otherTutorial)) {
            return false;
        }

        return tutorialClass.equals(otherTutorial.tutorialClass);

    }

    @Override
    public String toString() {
        return tutName + ": Tutorial " + tutorialClass;
    }
}
