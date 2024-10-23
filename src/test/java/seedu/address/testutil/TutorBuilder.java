package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hours;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Tutor objects.
 */
public class TutorBuilder {

    public static final String DEFAULT_NAME = "James Yeo";
    public static final String DEFAULT_PHONE = "91112222";
    public static final String DEFAULT_EMAIL = "james@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #12-111";
    public static final String DEFAULT_HOURS = "10";
    public static final String DEFAULT_SUBJECT = "english";


    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Hours hours;
    private Set<Tag> tags;
    private Set<Subject> subjects;


    /**
     * Creates a {@code TutorBuilder} with the default details.
     */
    public TutorBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        hours = new Hours(DEFAULT_HOURS);
        tags = new HashSet<>();
        subjects = new HashSet<>();

    }

    /**
     * Initializes the TutorBuilder with the data of {@code tutorToCopy}.
     */
    public TutorBuilder(Tutor tutorToCopy) {
        name = tutorToCopy.getName();
        phone = tutorToCopy.getPhone();
        email = tutorToCopy.getEmail();
        address = tutorToCopy.getAddress();
        hours = tutorToCopy.getHours();
        tags = new HashSet<>(tutorToCopy.getTags());
        subjects = new HashSet<>(tutorToCopy.getSubjects());

    }

    /**
     * Sets the {@code Name} of the {@code Tutor} that we are building.
     */
    public TutorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public TutorBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public TutorBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public TutorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public TutorBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Hours} of the {@code Person} that we are building.
     */
    public TutorBuilder withHours(String hours) {
        this.hours = new Hours(hours);
        return this;
    }

    /**
     * Sets the {@code Subjects} of the {@code Tutor} that we are building
     */
    public TutorBuilder withSubjects(String... subject) {
        this.subjects = SampleDataUtil.getSubjectSet(subject);
        return this;
    }

    public Tutor build() {
        return new Tutor(name, phone, email, address, hours, tags, subjects);
    }

}
