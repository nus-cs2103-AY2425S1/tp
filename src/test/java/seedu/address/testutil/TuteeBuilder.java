package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hours;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutee;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class TuteeBuilder {

    public static final int DEFAULT_ID = 0;
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "jurong, 123456";
    public static final String DEFAULT_HOURS = "10";
    public static final String DEFAULT_SUBJECT = "english";

    private int id;
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Hours hours;
    private Set<Subject> subjects;


    /**
     * Creates a {@code TuteeBuilder} with the default details.
     */
    public TuteeBuilder() {
        id = DEFAULT_ID;
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        hours = new Hours(DEFAULT_HOURS);
        subjects = new HashSet<>();
    }

    /**
     * Initializes the TuteeBuilder with the data of {@code tuteeToCopy}.
     */
    public TuteeBuilder(Tutee tuteeToCopy) {
        id = tuteeToCopy.getId();
        name = tuteeToCopy.getName();
        phone = tuteeToCopy.getPhone();
        email = tuteeToCopy.getEmail();
        address = tuteeToCopy.getAddress();
        hours = tuteeToCopy.getHours();
        subjects = new HashSet<>(tuteeToCopy.getSubjects());
    }

    /**
     * Sets the {@code Id} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Hours} of the {@code Tutee} that we are building.
     */
    public TuteeBuilder withHours(String hours) {
        this.hours = new Hours(hours);
        return this;
    }

    /**
     * Sets the {@code Subjects} of the {@code Tutee} that we are building
     */
    public TuteeBuilder withSubjects(String... subject) {
        this.subjects = SampleDataUtil.getSubjectSet(subject);
        return this;
    }

    public Tutee build() {
        return new Tutee(id, name, phone, email, address, hours, subjects);
    }

}
