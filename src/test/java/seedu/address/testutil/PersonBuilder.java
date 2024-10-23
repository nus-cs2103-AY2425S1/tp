package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.role.Role;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building {@link Person} objects.
 */
public class PersonBuilder {
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final EventName DEFAULT_EVENT_NAME = new EventName("IFG");

    private Name name;
    private Phone phone;
    private Email email;
    private Set<Event> events;
    private Set<Role> roles;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        events = new HashSet<>();
        events.add(new Event(DEFAULT_EVENT_NAME));
        roles = new HashSet<>();
    }

    /**
     * Initializes the {@code PersonBuilder} with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        events = new HashSet<>(personToCopy.getEvents());
        roles = new HashSet<>(personToCopy.getRoles());
    }

    /**
     * Sets the {@link Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code roles} into a {@code Set<Role>} and set it to the {@link Person} that we are building.
     */
    public PersonBuilder withRoles(String ... roles) {
        this.roles = SampleDataUtil.getRoleSet(roles);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@link Person} that we are building.
     */
    public PersonBuilder withEvents(String ... events) {
        this.events = SampleDataUtil.getEventSet(events);
        return this;
    }

    /**
     * Sets the {@link Phone} of the {@link Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@link Email} of the {@link Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, events, roles);
    }

}
