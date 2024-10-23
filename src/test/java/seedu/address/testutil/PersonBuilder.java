package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Income;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Remark;
import seedu.address.model.person.UpdatedAt;
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
    public static final String DEFAULT_PRIORITY = "LOW";
    public static final String DEFAULT_REMARK = "";
    public static final double DEFAULT_INCOME = 0;
    public static final String DEFAULT_DATE_OF_BIRTH = "1 Jan 2000";
    public static final LocalDateTime DEFAULT_UPDATED_AT =
            LocalDateTime.of(2024, 1, 1, 0, 0);

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Priority priority;
    private Remark remark;
    private DateOfBirth dateOfBirth;
    private Income income;
    private Appointment appointment;
    private Set<Tag> tags;
    private UpdatedAt updatedAt;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        priority = Priority.valueOf(DEFAULT_PRIORITY);
        remark = new Remark(DEFAULT_REMARK);
        dateOfBirth = new DateOfBirth(DEFAULT_DATE_OF_BIRTH);
        income = new Income(DEFAULT_INCOME);
        appointment = null;
        tags = new HashSet<>();
        updatedAt = new UpdatedAt(DEFAULT_UPDATED_AT);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        priority = personToCopy.getPriority();
        remark = personToCopy.getRemark();
        dateOfBirth = personToCopy.getDateOfBirth();
        income = personToCopy.getIncome();
        appointment = personToCopy.getAppointment();
        tags = new HashSet<>(personToCopy.getTags());
        updatedAt = personToCopy.getUpdatedAt();
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
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
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
     * Sets the {@code Priority} of the {@code Person} that we are building.
     */
    public PersonBuilder withPriority(String priority) {
        this.priority = Priority.valueOf(priority);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code dateOfBirth} of the {@code Person} that we are building.
     */
    public PersonBuilder withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = new DateOfBirth(dateOfBirth);
        return this;
    }

    /**
     * Sets the {@code income} of the {@code Person} that we are building.
     */
    public PersonBuilder withIncome(double income) {
        this.income = new Income(income);
        return this;
    }

    /**
     * Sets the {@code Appointment} of the {@code Person} that we are building.
     */
    public PersonBuilder withAppointment(String date, String startTime, String endTime) {
        this.appointment =
                new Appointment(LocalDate.parse(date), LocalTime.parse(startTime), LocalTime.parse(endTime));
        return this;
    }

    /**
     * Sets the {@code Appointment} of the {@code Person} that we are building.
     */
    public PersonBuilder withAppointment(Appointment appointment) {
        this.appointment = appointment;
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
     * Sets the {@code UpdatedAt} of the {@code Person} that we are building.
     */
    public PersonBuilder withUpdateAt(LocalDateTime updatedAt) {
        this.updatedAt = new UpdatedAt(updatedAt);
        return this;
    }

    /**
     * Builds the {@code Person}.
     */
    public Person build() {
        return new Person(name, phone, email, address, priority, remark, dateOfBirth, income, appointment, tags,
                updatedAt);
    }
}
