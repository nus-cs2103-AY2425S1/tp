package seedu.address.testutil;

import static seedu.address.model.person.Student.STUDENT_TYPE;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects, if unspecified, the default is Student.
 */
public class PersonBuilder {

    public static final String DEFAULT_TYPE = STUDENT_TYPE;
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_GENDER = "female";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SUBJECT = "Mathematics";
    public static final String DEFAULT_CLASSES = "Class 1, Class 2";

    private String type;
    private Name name;
    private Gender gender;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Set<Subject> subjects;
    private Set<String> classes;
    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        type = DEFAULT_TYPE;
        name = new Name(DEFAULT_NAME);
        gender = new Gender(DEFAULT_GENDER);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        subjects = new HashSet<>(SampleDataUtil.getSubjectSet(DEFAULT_SUBJECT));
        classes = new HashSet<>(SampleDataUtil.getClassSet(DEFAULT_CLASSES));
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        type = personToCopy.getType();
        name = personToCopy.getName();
        gender = personToCopy.getGender();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        subjects = new HashSet<>(personToCopy.getSubjects());
        classes = new HashSet<>(personToCopy.getClasses());
    }

    /**
     * Sets the {@code Type} of the {@code Person} that we are building.
     */
    public PersonBuilder withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withGender(String gender) {
        this.gender = new Gender(gender);
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
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
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

    /**
     * Sets the {@code Subject} of the {@code Person} that we are building.
     */
    public PersonBuilder withSubject(String... subjects) {
        this.subjects = SampleDataUtil.getSubjectSet(subjects);
        return this;
    }

    /**
     * Parses the {@code classes} into a {@code Set<String>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withClasses(String... classes) {
        this.classes = SampleDataUtil.getClassSet(classes);
        return this;
    }

    /**
     * Builds and returns a {@code Person} object with the current attributes of the {@code PersonBuilder}.
     *
     * @return a {@code Person} object with the specified attributes.
     */
    public Person build() {
        return Person.createPerson(type, name, gender, phone, email, address, tags, subjects, classes,
                null, null, null
        );
    }
}
