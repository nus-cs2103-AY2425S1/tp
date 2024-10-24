package seedu.address.model.tut;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.exceptions.DuplicateTutorialException;
import seedu.address.model.tut.exceptions.TutNoFoundException;

/**
 * Represents a tutorial list with a title, due date, and completion statuses for students.
 */
public class TutorialList {

    private final ArrayList<Tutorial> tutorials;

    /**
     * Constructs an empty TutorialList.
     */
    public TutorialList() {
        this.tutorials = new ArrayList<>();
        tutorials.add(Tutorial.none());
    }

    /**
     * Constructs a tutorial list with specified tutorials.
     *
     * @param tutorials The tutorials in the list.
     */
    public TutorialList(ArrayList<Tutorial> tutorials) {
        requireNonNull(tutorials);
        this.tutorials = tutorials;
        if (!tutorials.contains(Tutorial.none())) {
            tutorials.add(Tutorial.none());
        }
    }

    /**
     * Adds a tutorial to the list.
     *
     * @param tutorial The tutorial to be added.
     */
    public void addTutorial(Tutorial tutorial) {
        if (hasTutorial(tutorial)) {
            throw new DuplicateTutorialException();
        }

        tutorials.add(tutorial);
    }

    /**
     * Check if the list contains a specified tutorial.
     *
     * @param tutorial The tutorial to check for.
     * @return True if the list contains the tutorial, false otherwise.
     */
    public boolean hasTutorial(Tutorial tutorial) {
        return this.tutorials.contains(tutorial);
    }

    /**
     * Check if the list contains a specified tutorial class.
     *
     * @param tutorialId The tutorial class to check for
     * @return True if list contains he tutorial, false otherwise.
     */
    public boolean hasTutorial(TutorialId tutorialId) {
        Tutorial tutorial = Tutorial.of(new TutName("ran"), tutorialId);
        return this.tutorials.stream().anyMatch(x -> x.equals(tutorial));
    }

    public ArrayList<Tutorial> getTutorials() {
        return this.tutorials;
    }

    /**
     * Deletes the specified tutorial from the tutorial list.
     * The tutorial must exist in the list.
     *
     * @param tutorial The tutorial to be deleted.
     * @throws TutNoFoundException if the tutorial does not exist in the list.
     */
    public void deleteTutorial(Tutorial tutorial) {
        if (!hasTutorial(tutorial)) {
            throw new TutNoFoundException();
        }
        this.tutorials.remove(tutorial);
    }

    /**
     * Assign student to the tutorial class.
     *
     * @param student Student to be assigned
     * @param tutorialId Target tutorial
     */
    public void assignStudent(Student student, TutorialId tutorialId) {
        Tutorial ran = Tutorial.of(new TutName("ran"), tutorialId);
        tutorials.stream()
                .filter(t -> t.equals(ran))
                .forEach(t -> t.add(student));
    }

    /**
     * Deletes the specified student from all tutorials where the student is present.
     *
     * @param student The student to be removed from the tutorials.
     */
    public void deleteStudent(Student student) {
        tutorials.stream()
                .filter(t -> t.studentInList(student))
                .forEach(t -> t.deleteStudent(student));
    }

    /**
     * Resets the existing data of this {@code TutorialList} with {@code newData}.
     */
    public void resetData(TutorialList newData) {
        requireNonNull(newData);
        this.tutorials.clear();
        this.tutorials.addAll(newData.tutorials);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TutorialList)) {
            return false;
        }
        TutorialList otherList = (TutorialList) other;
        return this.tutorials.equals(otherList.tutorials);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int j = 0;
        for (int i = 0; i < tutorials.size(); i++) {
            if (tutorials.get(i).equals(Tutorial.none())) {
                continue;
            }
            sb.append(j + 1).append(". ").append(tutorials.get(i).toString()).append("\n");
            j = j + 1;
        }
        return sb.toString();
    }

}
