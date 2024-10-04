package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.*;

import seedu.address.commons.util.ToStringBuilder;
//import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    //private final Email email;
    private final StudentId studentId;

    // Data fields
    private final Address address;
    private final Course course;
    private final HashMap<Module, Grade> moduleGrades = new HashMap<>();
    //private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(StudentId studentId, Name name, Phone phone, Address address, Course course) {
        requireAllNonNull(studentId, name, phone, address, course);
        this.studentId = studentId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.course = course;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

//    public Email getEmail() {
//        return email;
//    }

    public Address getAddress() {
        return address;
    }

    public Course getCourse() {
        return course;
    }

    /**
     * Sets the module grades to the provided map.
     *
     * @param newModuleGrades A map of Module and Grade pairs to set.
     */
    public void setModuleGrades(Map<Module, Grade> newModuleGrades) {
        moduleGrades.clear();
        moduleGrades.putAll(newModuleGrades);
    }

    /**
     * Adds a module grade. If the module already exists, it updates the grade.
     *
     * @param module The module for which to add or update the grade.
     * @param grade The grade to associate with the module.
     */
    public void addModuleGrade(Module module, Grade grade) {
        requireNonNull(module, "Module cannot be null");
        requireNonNull(grade, "Grade cannot be null");
        moduleGrades.put(module, grade);
    }

    /**
     * Returns an immutable course grades map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<Module, Grade> getModuleGrades() {
        return Collections.unmodifiableMap(moduleGrades);
    }

//    /**
//     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
//     * if modification is attempted.
//     */
//    public Set<Tag> getTags() {
//        return Collections.unmodifiableSet(tags);
//    }

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
        return studentId.equals(otherPerson.studentId)
                && name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && address.equals(otherPerson.address)
                && course.equals(otherPerson.course)
                && moduleGrades.equals(otherPerson.moduleGrades);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(studentId, name, phone, address, course, moduleGrades);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .add("name", name)
                .add("phone", phone)
                .add("address", address)
                .add("course", course)
                .add("module grades", moduleGrades)
                .toString();
    }

}
