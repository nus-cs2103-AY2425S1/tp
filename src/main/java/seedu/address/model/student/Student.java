package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;
import seedu.address.model.tut.TutDate;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;

    // Data fields
    private final StudentId studentId;
    private final TutorialId tutorialId;
    private final Set<Tag> tags = new HashSet<>();
    private final ObjectProperty<PresentDates> presentDates;

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, StudentId studentId, TutorialId tutorialId, PresentDates presentDates) {
        requireAllNonNull(name, studentId, tutorialId);
        this.name = name;
        this.tags.addAll(tags);
        this.presentDates = new
                SimpleObjectProperty<>(presentDates != null ? presentDates : new PresentDates(new HashSet<>()));
        this.studentId = studentId;
        this.tutorialId = tutorialId;
    }

    public Name getName() {
        return name;
    }
    public PresentDates getPresentDates() {
        return presentDates.get();
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public TutorialId getTutorialId() {
        return tutorialId;
    }

    /**
     * Returns true if both students have the same name.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName());
    }

    public void setAttendance(TutDate tutDate) {
        presentDates.get().setAttendance(tutDate);
    }

    public void setAbsent(TutDate tutDate) {
        presentDates.get().setAbsent(tutDate);
    }

    /**
     * Returns true if both students have the same student id.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudentId(StudentId otherStudentId) {
        if (otherStudentId == this.studentId) {
            return true;
        }

        return otherStudentId != null
                && otherStudentId.equals(getStudentId());
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Student otherStudent)) {
            return false;
        }

        return name.equals(otherStudent.name)
                && studentId.equals(otherStudent.studentId)
                && tutorialId.equals(otherStudent.tutorialId);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, studentId, tutorialId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("student id", studentId)
                .add("tutorial id", tutorialId)
                .toString();
    }

    public ObjectProperty<PresentDates> presentDatesProperty() {
        return presentDates;
    }
}
