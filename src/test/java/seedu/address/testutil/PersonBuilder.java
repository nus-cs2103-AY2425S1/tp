package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.group.GroupName;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_EMAIL = "amy@u.nus.edu";
    public static final String DEFAULT_STUDENT_NUMBER = "A0123456X";

    private Name name;
    private Email email;
    private Set<Tag> tags;
    private StudentNumber studentNumber;
    private Optional<GroupName> groupName;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        email = new Email(DEFAULT_EMAIL);
        tags = new HashSet<>();
        studentNumber = new StudentNumber(DEFAULT_STUDENT_NUMBER);
        groupName = Optional.empty();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code studentToCopy}.
     */
    public PersonBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        email = studentToCopy.getEmail();
        tags = new HashSet<>(studentToCopy.getTags());
        studentNumber = studentToCopy.getStudentNumber();
        groupName = studentToCopy.getGroupName();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code String} group name of the {@code Student} that we are building.
     */
    public PersonBuilder withGroup(String group) {
        GroupName groupName = new GroupName(group);
        this.groupName = Optional.of(groupName);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code StudentNumber} of the {@code Student} that we are building.
     */
    public PersonBuilder withStudentNumber(String studentNumber) {
        this.studentNumber = new StudentNumber(studentNumber);
        return this;
    }

    /**
     * Builds a {@code Student}.
     *
     * @returns A Student.
     */
    public Student build() {
        return groupName.isPresent()
            ? new Student(name, email, tags, studentNumber, groupName) : new Student(name, email, tags, studentNumber);
    }

}
