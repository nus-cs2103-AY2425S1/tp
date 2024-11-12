package careconnect.testutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import careconnect.logic.parser.ParserUtil;
import careconnect.logic.parser.exceptions.ParseException;
import careconnect.model.log.Log;
import careconnect.model.person.Address;
import careconnect.model.person.AppointmentDate;
import careconnect.model.person.Email;
import careconnect.model.person.Name;
import careconnect.model.person.Person;
import careconnect.model.person.Phone;
import careconnect.model.tag.Tag;
import careconnect.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private ArrayList<Log> logs;
    private AppointmentDate appointmentDate;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        logs = new ArrayList<>();
        appointmentDate = new AppointmentDate();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        logs = new ArrayList<>(personToCopy.getLogs());
        appointmentDate = personToCopy.getAppointmentDate();
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
     * Sets the (@code AppointmentDate} of the {code Person} that we are building.
     */
    public PersonBuilder withAppointmentDate(AppointmentDate appointmentDate) {
        this.appointmentDate = appointmentDate;
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
     * Adds the {@code Log} of the {@code Person} that we are building.
     */
    public PersonBuilder withLog(String date, String time) {
        try {
            this.logs.add(new Log(ParserUtil.parseLogDate(date), time));
            this.logs.sort(Log::compareTo);
            this.logs.sort(Collections.reverseOrder());
        } catch (ParseException e) {
            assert(false);
        }
        return this;
    }

    /**
     * Adds the {@code Logs} of the {@code Person} that we are building.
     */
    public PersonBuilder withLogs(ArrayList<Log> logs) {
        this.logs.addAll(logs);
        this.logs.sort(Log::compareTo);
        this.logs.sort(Collections.reverseOrder());
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, tags, logs, appointmentDate);
    }

}
