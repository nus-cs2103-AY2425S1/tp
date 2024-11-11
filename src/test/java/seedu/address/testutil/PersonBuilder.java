package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.reminder.ReminderCommandTestUtil;
import seedu.address.model.clienttype.ClientType;
import seedu.address.model.person.Address;
import seedu.address.model.person.Description;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DESCRIPTION = "Likes to eat a lot";
    public static final String DEFAULT_CLIENT_TYPE = "A";



    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<ClientType> clientTypes;
    private Description description;
    private Set<Reminder> reminders;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        clientTypes = new HashSet<>(SampleDataUtil.getClientTypeSet(DEFAULT_CLIENT_TYPE));
        description = new Description(DEFAULT_DESCRIPTION);
        reminders = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        clientTypes = new HashSet<>(personToCopy.getClientTypes());
        description = personToCopy.getDescription();
        reminders = new HashSet<>(personToCopy.getReminders());
    }

    /**
     * Returns a {@code Person} with Alice's details used in EditCommandTest.
     */
    public static Person personBuilderAlice() {
        Name name = new Name("Alice Pauline");
        Phone phone = new Phone(DEFAULT_PHONE);
        Email email = new Email(DEFAULT_EMAIL);
        Address address = new Address(DEFAULT_ADDRESS);
        Set<ClientType> clientTypes = new HashSet<>(SampleDataUtil.getClientTypeSet(DEFAULT_CLIENT_TYPE));
        Description description = new Description(DEFAULT_DESCRIPTION);
        Set<Reminder> reminders = ReminderCommandTestUtil.DEFAULT_REMINDER_SET;

        return new Person(name, phone, email, address, clientTypes, description, reminders);
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code clientTypes} into a {@code Set<ClientType>}
     * and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withClientTypes(String ... clientTypes) {
        this.clientTypes = SampleDataUtil.getClientTypeSet(clientTypes);
        return this;
    }

    /**
     * Parses the {@code reminders} into a {@code Set<Reminder>}
     * and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withReminders(Reminder ... reminders) {
        this.reminders = SampleDataUtil.getReminderSet(reminders);
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
     * Sets the {@code Description} of the {@code Person} that we are building.
     */
    public PersonBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, clientTypes, description, reminders);
    }

}
