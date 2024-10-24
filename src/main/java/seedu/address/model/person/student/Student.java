package seedu.address.model.person.student;


import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.company.Industry;
import seedu.address.model.tag.Tag;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student extends Person {

    private final StudentId studentId;
    private int attendance;

    /**
     * Create a new student object
     * @param name
     * @param studentId
     * @param phone
     * @param email
     * @param address
     * @param tags
     */
    public Student(Name name, StudentId studentId, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        requireAllNonNull(studentId);
        this.studentId = studentId;
        attendance = 0;
    }
    @Override
    public StudentId getStudentId() {
        return studentId;
    }
    /**
     * Return null as student does not have an industry
     * @return null
     */
    @Override
    public Industry getIndustry() {
        return null;
    }

    public void incrementAttendance() {
        attendance++;
    }

    public int getAttendance() {
        return attendance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", super.getName())
                .add("student id", studentId)
                .add("phone", super.getPhone())
                .add("email", super.getEmail())
                .add("address", super.getAddress())
                .add("tags", super.getTags())
                .add("attendance", attendance)
                .toString();
    }
    @Override
    public String getCategoryDisplayName() {
        return "Student";
    }
    // Things to add: A list to keep track of event attended
}
