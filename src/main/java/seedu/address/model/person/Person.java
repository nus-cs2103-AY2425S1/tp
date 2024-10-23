package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.task.TaskList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final EmergencyContact emergencyContact;

    // Data fields
    private final Address address;
    private final Note note;
    private final Set<Subject> subjects = new HashSet<>();
    private final Level level;
    private final TaskList taskList;
    private final Set<LessonTime> lessonTimes = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, EmergencyContact emergencyContact,
                  Address address, Note note, Set<Subject> subjects,
                  Level level, TaskList tasklist, Set<LessonTime> lessonTimes) {
        requireAllNonNull(name, phone, address, subjects);
        this.name = name;
        this.phone = phone;
        this.emergencyContact = emergencyContact;
        this.address = address;
        this.note = (note != null) ? note : new Note("");
        if (!subjects.isEmpty()) {
            this.subjects.addAll(subjects);
        }
        this.level = level;
        this.taskList = tasklist.copy();
        if (!lessonTimes.isEmpty()) {
            this.lessonTimes.addAll(lessonTimes);
        }
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    public Address getAddress() {
        return address;
    }

    public Note getNote() {
        return note;
    }

    /**
     * Returns an immutable subject set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Subject> getSubjects() {
        return Collections.unmodifiableSet(subjects);
    }

    public Level getLevel() {
        return level;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    /**
     * Returns an immutable lesson time set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<LessonTime> getLessonTimes() {
        return Collections.unmodifiableSet(lessonTimes);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && emergencyContact.equals(otherPerson.emergencyContact)
                && address.equals(otherPerson.address)
                && note.equals(otherPerson.note)
                && level.equals(otherPerson.level)
                && subjects.equals(otherPerson.subjects)
                && taskList.equals(otherPerson.taskList)
                && lessonTimes.equals(otherPerson.lessonTimes);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, emergencyContact, address, note, subjects, level, taskList, lessonTimes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("emergency contact", emergencyContact)
                .add("address", address)
                .add("note", note)
                .add("subjects", subjects)
                .add("level", level)
                .add("task list", taskList)
                .add("lesson times", lessonTimes)
                .toString();
    }

}
