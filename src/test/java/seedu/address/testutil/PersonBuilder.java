package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Property;
import seedu.address.model.person.Seller;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_PROPERTY = "123 Geylang Lor 21";
    public static final String DEFAULT_DATE = "01/01/2024";
    public static final String DEFAULT_FROM = "0800";
    public static final String DEFAULT_TO = "0900";

    private Name name;
    private Phone phone;
    private Email email;
    private Property property;
    private Appointment appointment;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        property = new Property(DEFAULT_PROPERTY);
        appointment = new Appointment(new Date(DEFAULT_DATE), new From(DEFAULT_FROM),
                                        new To(DEFAULT_TO));
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        property = personToCopy.getProperty();
        appointment = personToCopy.getAppointment();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
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
     * Sets the {@code Tags} of the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
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
     * Sets the {@code Property} of the {@code Person} that we are building.
     */
    public PersonBuilder withProperty(String property) {
        this.property = new Property(property);
        return this;
    }

    /**
    * Sets the {@code Appointment} of the {@code Person} that we are building.
    */
    public PersonBuilder withAppointment(String date, String from, String to) {
        this.appointment = new Appointment(new Date(date), new From(from), new To(to));
        return this;
    }

    /**
     * Creates a {@code Buyer} instance.
     */
    public Buyer buildBuyer() {
        return new Buyer(name, phone, email, tags, appointment, property);
    }

    /**
     * Creates a {@code Seller} instance.
     */
    public Seller buildSeller() {
        return new Seller(name, phone, email, tags, appointment, property);
    }
}
