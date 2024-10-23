package seedu.address.testutil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Tutorial;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_STUDENTID = "E0000007";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";

    private Name name;
    private StudentId studentId;
    private Phone phone;
    private Email email;
    private Set<Tag> tags;
    private Map<Tutorial, AttendanceStatus> tutorials;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        studentId = new StudentId(DEFAULT_STUDENTID);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        tags = new HashSet<>();
        tutorials = new LinkedHashMap<>();
        for (int i = 1; i <= Person.MAXIMUM_TUTORIALS; i++) {
            Tutorial tutorial = new Tutorial(String.valueOf(i));
            tutorials.put(tutorial, AttendanceStatus.NOT_TAKEN_PLACE);
        }
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        studentId = personToCopy.getStudentId();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        tags = new HashSet<>(personToCopy.getTags());
        tutorials = new HashMap<>(personToCopy.getTutorials());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code StudentId} of the {@code Person} that we are building.
     */
    public PersonBuilder withStudentId(String studentId) {
        this.studentId = new StudentId(studentId);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code tutorials} into a {@code Map<Tutorial, Boolean>} and set it to the {@code Person}
     * that we are building.
     */
    public PersonBuilder withTutorials(String ... tutorials) {
        this.tutorials = SampleDataUtil.getTutorialMap(tutorials);
        return this;
    }

    /**
     * Parses the {@code tutorials} and {@code attendance} into a {@code Map<Tutorial, Boolean>} and set it to the
     * {@code Person} that we are building.
     */
    public PersonBuilder withTutorials(String[] tutorials, AttendanceStatus[] attendance) {
        this.tutorials = SampleDataUtil.getTutorialMap(tutorials, attendance);
        return this;
    }

    /**
     * Amends the stated tutorial and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withAmendedTutorial(String tutorialIndex, AttendanceStatus attendanceStatus) {
        this.tutorials.put(new Tutorial(tutorialIndex), attendanceStatus);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Person build() {
        return new Person(name, studentId, phone, email, tags, tutorials);
    }

}
