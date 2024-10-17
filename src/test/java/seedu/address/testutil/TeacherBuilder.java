package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Teacher;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Teacher objects.
 */
public class TeacherBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_GENDER = "female";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@example.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SUBJECT = "Mathematics";
    public static final String DEFAULT_CLASSES = "Class 1, Class 2";

    private Name name;
    private Gender gender;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Subject subject;
    private Set<String> classes;

    /**
     * Creates a {@code TeacherBuilder} with the default details.
     */
    public TeacherBuilder() {
        name = new Name(DEFAULT_NAME);
        gender = new Gender(DEFAULT_GENDER);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>(SampleDataUtil.getTagSet("friends"));
        subject = new Subject(DEFAULT_SUBJECT);
        classes = new HashSet<>(SampleDataUtil.getClassSet(DEFAULT_CLASSES));
    }

    /**
     * Initializes the TeacherBuilder with the data of {@code teacherToCopy}.
     */
    public TeacherBuilder(Teacher teacherToCopy) {
        name = teacherToCopy.getName();
        gender = teacherToCopy.getGender();
        phone = teacherToCopy.getPhone();
        email = teacherToCopy.getEmail();
        address = teacherToCopy.getAddress();
        tags = new HashSet<>(teacherToCopy.getTags());
        subject = teacherToCopy.getSubject();
        classes = new HashSet<>(teacherToCopy.getClasses());
    }

    /**
     * Sets the {@code Name} of the {@code Teacher} that we are building.
     */
    public TeacherBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Teacher} that we are building.
     */
    public TeacherBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Teacher} that we are building.
     */
    public TeacherBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Teacher} that we are building.
     */
    public TeacherBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Teacher} that we are building.
     */
    public TeacherBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Teacher} that we are building.
     */
    public TeacherBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Teacher} that we are building.
     */
    public TeacherBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Parses the {@code classes} into a {@code Set<String>} and set it to the {@code Teacher} that we are building.
     */
    public TeacherBuilder withClasses(String... classes) {
        this.classes = SampleDataUtil.getClassSet(classes);
        return this;
    }

    public Teacher build() {
        return new Teacher(name, gender, phone, email, address, tags, subject, classes);
    }
}
