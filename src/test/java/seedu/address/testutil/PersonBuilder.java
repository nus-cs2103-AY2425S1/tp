package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.ClassId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Fees;
import seedu.address.model.person.MonthsPaid;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
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
    public static final String DEFAULT_FEES = "564";
    public static final String DEFAULT_CLASS_ID = "1";
    public static final String DEFAULT_MONTHS_PAID = "2024-01 2024-02";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Fees fees;
    private ClassId classId;
    private MonthsPaid monthsPaid;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        fees = new Fees(DEFAULT_FEES);
        classId = new ClassId(DEFAULT_CLASS_ID);
        monthsPaid = new MonthsPaid(DEFAULT_MONTHS_PAID);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        fees = personToCopy.getFees();
        classId = personToCopy.getClassId();
        monthsPaid = personToCopy.getMonthsPaid();
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
     * Sets the {@code Fees} of the {@code Person} that we are building.
     */
    public PersonBuilder withFees(String fees) {
        this.fees = new Fees(fees);
        return this;
    }

    /**
     * Sets the {@code ClassId} of the {@code Person} that we are building.
     */
    public PersonBuilder withClassId(String classId) {
        this.classId = new ClassId(classId);
        return this;
    }

    /**
     * Sets the {@code MonthsPaid} of the {@code Person} that we are building.
     */
    public PersonBuilder withMonthsPaid(String monthsPaid) {
        this.monthsPaid = new MonthsPaid(monthsPaid);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, fees, classId, monthsPaid, tags);
    }

}
