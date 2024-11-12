package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.FamilySize;
import seedu.address.model.person.Income;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.person.UpdatedAt;
import seedu.address.model.scheme.Scheme;
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
    public static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.of(2000, 1, 1);
    public static final double DEFAULT_INCOME = 0;
    public static final int DEFAULT_FAMILY_SIZE = 1;
    public static final LocalDateTime DEFAULT_UPDATED_AT =
            LocalDateTime.of(2024, 1, 1, 0, 0);
    public static final boolean DEFAULT_IS_ARCHIVED = false;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Priority priority;
    private DateOfBirth dateOfBirth;
    private Income income;
    private FamilySize familySize;
    private Set<Tag> tags;
    private ArrayList<Scheme> schemes;
    private UpdatedAt updatedAt;
    private boolean isArchived;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        priority = Priority.valueOf(DEFAULT_PRIORITY);
        dateOfBirth = new DateOfBirth(DEFAULT_DATE_OF_BIRTH);
        income = new Income(DEFAULT_INCOME);
        familySize = new FamilySize(DEFAULT_FAMILY_SIZE);
        tags = new HashSet<>();
        schemes = new ArrayList<>();
        updatedAt = new UpdatedAt(DEFAULT_UPDATED_AT);
        isArchived = DEFAULT_IS_ARCHIVED;
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
        dateOfBirth = personToCopy.getDateOfBirth();
        income = personToCopy.getIncome();
        familySize = personToCopy.getFamilySize();
        tags = new HashSet<>(personToCopy.getTags());
        schemes = new ArrayList<>(personToCopy.getSchemes());
        updatedAt = personToCopy.getUpdatedAt();
        isArchived = personToCopy.isArchived();
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
     * Sets the {@code dateOfBirth} of the {@code Person} that we are building.
     */
    public PersonBuilder withDateOfBirth(LocalDate dateOfBirth) {
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
     * Sets the {@code familySize} of the {@code Person} that we are building.
     */
    public PersonBuilder withFamilySize(int familySize) {
        this.familySize = new FamilySize(familySize);
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
    public PersonBuilder withUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = new UpdatedAt(updatedAt);
        return this;
    }

    /**
     * Sets the {@code isArchived} of the {@code Person} that we are building.
     */
    public PersonBuilder withArchived(boolean isArchived) {
        this.isArchived = isArchived;
        return this;
    }

    /**
     * Builds the {@code Person}.
     */
    public Person build() {
        return new Person(name, phone, email, address, priority, dateOfBirth, income, familySize, tags, schemes,
                updatedAt, isArchived);
    }
}
