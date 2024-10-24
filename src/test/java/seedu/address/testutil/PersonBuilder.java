package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Policy;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BIRTHDAY = "1990-10-10";
    public static final String DEFAULT_APPOINTMENT = "2024-12-12 12:00";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Birthday birthday;
    private Appointment appointment;
    private List<Policy> policies;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        birthday = new Birthday(DEFAULT_BIRTHDAY);
        appointment = new Appointment(DEFAULT_APPOINTMENT);
        policies = new ArrayList<>();
        tags = new HashSet<>();
        policies = new ArrayList<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        this.name = personToCopy.getName();
        this.phone = personToCopy.getPhone();
        this.email = personToCopy.getEmail();
        this.address = personToCopy.getAddress();
        this.birthday = personToCopy.getBirthday();
        this.appointment = personToCopy.getAppointment();
        this.policies = new ArrayList<>(personToCopy.getPolicies());
        this.tags = new HashSet<>(personToCopy.getTags());
        this.policies = new ArrayList<>(personToCopy.getPolicies());
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
     * Sets the {@code Appointment} of the {@code Person} that we are building.
     */
    public PersonBuilder withAppointment(String appointment) {
        this.appointment = new Appointment(appointment);
        return this;
    }

    /**
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        this.birthday = new Birthday(birthday);
        return this;
    }

    /**
     * Sets the {@code policies} of the {@code Person} that we are building.
     * Each policy is created with default values for description, coverage, and premium.
     *
     * @param policies Varargs of policy names to be added to the {@code Person}.
     * @return This {@code PersonBuilder} instance with the updated policies.
     */
    public PersonBuilder withPolicies(String... policies) {
        this.policies = new ArrayList<>();
        for (String policy : policies) {
            this.policies.add(new Policy("Policy", "2022-12-12",
                    "2023-12-12", "2022-12-12 300"));
        }
        return this;
    }


    /**
     * Builds and returns a {@code Person} object with the current state of the {@code PersonBuilder}.
     *
     * @return A {@code Person} object with the set attributes.
     */
    public Person build() {
        Person person = new Person(name, phone, email, address, tags, appointment, birthday);
        person.setPolicies(policies);
        return person;
    }

}
