package seedu.address.model.tut;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.tut.exceptions.NoTutorialException;
import seedu.address.model.tut.exceptions.TutDateNotFoundException;

/**
 * Represents an abstract Tutorial entity.
 * A Tutorial can either be an existing tutorial or none.
 */
public abstract class Tutorial {
    public static final String MESSAGE_NAME_CONSTRAINTS = "Tutorial names should only contain alphanumeric"
            + " characters and spaces, "
            + "and it should not be blank.";

    public static final String MESSAGE_ID_CONSTRAINTS = TutorialId.MESSAGE_CONSTRAINTS;

    /**
     * Adds a student to the tutorial.
     *
     * @param student The student to be added.
     */
    public abstract void add(Student student);

    /**
     * Returns the list of students in the tutorial.
     *
     * @return List of students.
     */
    public abstract List<Student> getStudents();

    /**
     * Sets the attendance for a specific student on a given date.
     *
     * @param date   The date of the tutorial session.
     * @param target The Student ID of the student.
     * @return True if the attendance is successfully set; otherwise false.
     * @throws NoTutorialException if called on a None instance.
     */
    public abstract boolean setAttendance(Date date, StudentId target);

    public abstract boolean setAbsent(Date date, StudentId target);

    /**
     * Returns the name of the tutorial.
     *
     * @return The tutorial name.
     * @throws NoTutorialException if called on a None instance.
     */
    public abstract TutName getTutName();


    /**
     * Checks if a tutorial date is present in the list of tutorial dates.
     *
     * @param date The tutorial date to check.
     * @return True if the date is present; otherwise false.
     * @throws NoTutorialException if called on a None instance.
     */
    public abstract boolean tutorialDateInList(TutDate date);

    /**
     * Adds a tutorial date to the list of tutorial dates.
     *
     * @param tutorialDate The tutorial date to be added.
     * @throws NoTutorialException if called on a None instance.
     */
    public abstract void addTutorialDate(TutDate tutorialDate);

    /**
     * Retrieves the tutorial date based on the given date.
     *
     * @param date The date of the tutorial session.
     * @return The tutorial date object.
     * @throws NoTutorialException if called on a None instance.
     */
    public abstract TutDate getTutorialDate(Date date);

    /**
     * Validates if the given tutorial date is valid.
     *
     * @param tutorialDate The tutorial date to validate.
     * @return True if the tutorial date is valid; otherwise false.
     * @throws NoTutorialException if called on a None instance.
     */
    public abstract boolean isValidTutorialDate(TutDate tutorialDate);

    /**
     * Returns the list of all tutorial dates.
     *
     * @return List of tutorial dates.
     * @throws NoTutorialException if called on a None instance.
     */
    public abstract List<TutDate> getTutDates();

    /**
     * Returns the tutorial class information.
     *
     * @return The tutorial class.
     * @throws NoTutorialException if called on a None instance.
     */
    public abstract TutorialId getTutorialId();

    /**
     * Checks if a student is in the list of students.
     *
     * @param student The student to check.
     * @return True if the student is in the list; otherwise false.
     */
    public abstract boolean studentInList(Student student);

    /**
     * Deletes a student from the tutorial.
     *
     * @param student The student to be removed.
     */
    public abstract void deleteStudent(Student student);

    private static Tutorial exist(TutName tutName, TutorialId tutorialId) {
        return new Exist(tutName, tutorialId);
    }

    /**
     * Creates a tutorial that represents no tutorial.
     *
     * @return A None tutorial object.
     */
    public static Tutorial none() {
        return None.none();
    }

    /**
     * Creates an instance of an existing tutorial.
     *
     * @param tutName The name of the tutorial.
     * @param tutorialId The class of the tutorial.
     * @return A new tutorial object.
     */
    public static Tutorial of(TutName tutName, TutorialId tutorialId) {
        requireNonNull(tutName);
        requireNonNull(tutorialId);
        if (tutorialId.equals(TutorialId.none())) {
            return Tutorial.none();
        }
        return Tutorial.exist(tutName, tutorialId);
    }

    /**
     * Represents a None type of tutorial (non-existent).
     */
    private static final class None extends Tutorial {
        private static final None none = new None();

        private final List<Student> students = new ArrayList<>();
        private final TutorialId tutorialId = TutorialId.none();

        public static None none() {
            return none;
        }

        @Override
        public void add(Student student) {
            requireNonNull(student);
            if (!students.contains(student)) {
                students.add(student);
            }
        }

        @Override
        public List<Student> getStudents() {
            return this.students;
        }

        @Override
        public boolean setAttendance(Date date, StudentId target) {
            throw new NoTutorialException();
        }

        @Override
        public boolean setAbsent(Date date, StudentId target) {
            throw new NoTutorialException();
        }

        @Override
        public TutName getTutName() {
            return new TutName("empty");
        }

        @Override
        public boolean tutorialDateInList(TutDate date) {
            throw new NoTutorialException();
        }

        @Override
        public void addTutorialDate(TutDate tutorialDate) {
            throw new NoTutorialException();
        }

        @Override
        public TutDate getTutorialDate(Date date) {
            return new TutDate(new Date());
        }

        @Override
        public boolean isValidTutorialDate(TutDate tutorialDate) {
            throw new NoTutorialException();
        }

        @Override
        public List<TutDate> getTutDates() {
            List<TutDate> tutDates = new ArrayList<>();
            return tutDates;
        }

        @Override
        public TutorialId getTutorialId() {
            return tutorialId;
        }

        @Override
        public boolean studentInList(Student student) {
            return students.contains(student);
        }

        @Override
        public void deleteStudent(Student student) {
            if (!students.contains(student)) {
                throw new StudentNotFoundException();
            }
            students.remove(student);
        }

    }

    /**
     * Represents an existing tutorial with a list of students and tutorial dates.
     */
    private static final class Exist extends Tutorial {
        private final TutName tutName;
        private final TutorialId tutorialId;
        private final List<Student> students = new ArrayList<>();
        private final HashMap<Date, TutDate> tutDates = new HashMap<>();

        public Exist(TutName tutName, TutorialId tutorialId) {
            this.tutName = tutName;
            this.tutorialId = tutorialId;
        }

        @Override
        public void add(Student student) {
            requireNonNull(student);
            if (!students.contains(student)) {
                students.add(student);
            }
        }

        @Override
        public List<Student> getStudents() {
            return this.students;
        }

        @Override
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

        @Override
        public boolean setAbsent(Date date, StudentId target) {
            requireNonNull(date);
            requireNonNull(target);
            if (!tutDates.containsKey(date)) {
                throw new TutDateNotFoundException();
            }
            TutDate tutDate = tutDates.get(date);
            return students.stream()
                    .filter(s -> s.getStudentId().equals(target))
                    .findFirst()
                    .map(student -> {
                        student.setAbsent(tutDate);
                        tutDate.remove(target);
                        if (tutDate.isEmptyStudent()) {
                            tutDates.remove(date, tutDate);
                        }
                        return true;
                    }).orElse(false);
        }

        @Override
        public TutName getTutName() {
            return this.tutName;
        }

        @Override
        public boolean tutorialDateInList(TutDate date) {
            return tutDates.containsKey(date.getDate());
        }

        @Override
        public void addTutorialDate(TutDate tutorialDate) {
            requireNonNull(tutorialDate);
            tutDates.putIfAbsent(tutorialDate.getDate(), tutorialDate);
        }

        @Override
        public TutDate getTutorialDate(Date date) {
            return tutDates.get(date);
        }

        @Override
        public boolean isValidTutorialDate(TutDate tutorialDate) {
            return tutorialDate.isValid();
        }

        @Override
        public List<TutDate> getTutDates() {
            return new ArrayList<>(tutDates.values());
        }

        @Override
        public TutorialId getTutorialId() {
            return tutorialId;
        }

        @Override
        public boolean studentInList(Student student) {
            return students.contains(student);
        }

        @Override
        public void deleteStudent(Student student) {
            if (!students.contains(student)) {
                throw new StudentNotFoundException();
            }
            for (TutDate tutDate : tutDates.values()) {
                if (tutDate.isInTutDate(student.getStudentId())) {
                    tutDate.remove(student.getStudentId());
                    if (tutDate.isEmptyStudent()) {
                        tutDates.remove(tutDate.getDate(), tutDate);
                    }
                }
            }
            students.remove(student);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (other instanceof Exist) {
                Exist exist = (Exist) other;

                if (this.tutorialId == exist.tutorialId) {
                    return true;
                }

                if (this.tutorialId == null || exist.tutorialId == null) {
                    return false;
                }
                return tutorialId.equals(exist.tutorialId);
            }

            return false;
        }
        @Override
        public String toString() {
            return tutName + ": Tutorial " + tutorialId;
        }
    }
}
