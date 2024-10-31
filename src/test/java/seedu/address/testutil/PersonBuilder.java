package seedu.address.testutil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.group.Group;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Year;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_STUDENTID = "A8535525P";
    public static final String DEFAULT_EMAIL = "e1234567@u.nus.edu";
    public static final String DEFAULT_MAJOR = "Computer Science";
    public static final String DEFAULT_YEAR = "1";
    public static final String DEFAULT_GROUP = "group 1";
    public static final String DEFAULT_COMMENT = "Shes a very vocal person";


    private Name name;
    private StudentId studentId;
    private Email email;
    private Major major;
    private Year year;
    private Set<Group> groups;
    private Comment comment;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        studentId = new StudentId(DEFAULT_STUDENTID);
        email = Email.makeEmail(DEFAULT_EMAIL);
        major = Major.makeMajor(DEFAULT_MAJOR);
        year = Year.makeYear(DEFAULT_YEAR);
        groups = new HashSet<>(Collections.singleton(new Group(DEFAULT_GROUP)));
        comment = new Comment(DEFAULT_COMMENT);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        studentId = personToCopy.getStudentId();
        email = personToCopy.getEmail();
        major = personToCopy.getMajor();
        year = personToCopy.getYear();
        groups = new HashSet<>(personToCopy.getGroups());
        comment = personToCopy.getComment();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.groups = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Major} of the {@code Person} that we are building.
     */
    public PersonBuilder withMajor(String major) {
        this.major = Major.makeMajor(major);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withStudentId(String studentId) {
        this.studentId = new StudentId(studentId);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = Email.makeEmail(email);
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code Person} that we are building.
     */
    public PersonBuilder withYear(String year) {
        this.year = Year.makeYear(year);
        return this;
    }

    /**
     * Sets the {@code Comment} of the {@code Person} that we are building.
     */
    public PersonBuilder withComment(String comment) {
        this.comment = new Comment(comment);
        return this;
    }

    public Person build() {
        return new Person(name, studentId, email, major, groups, year, comment);
    }

}
