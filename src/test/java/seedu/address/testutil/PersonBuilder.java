package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.ClientStatus;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PaymentStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProjectStatus;
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
    public static final String DEFAULT_PROJECT_STATUS = "in progress";
    public static final String DEFAULT_PAYMENT_STATUS = "pending";
    public static final String DEFAULT_CLIENT_STATUS = "active";
    public static final String DEFAULT_DEADLINE = "10-10-2024";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private ProjectStatus projectStatus;
    private PaymentStatus paymentStatus;
    private ClientStatus clientStatus;
    private Deadline deadline;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        projectStatus = new ProjectStatus(DEFAULT_PROJECT_STATUS);
        paymentStatus = new PaymentStatus(DEFAULT_PAYMENT_STATUS);
        clientStatus = new ClientStatus(DEFAULT_CLIENT_STATUS);
        deadline = new Deadline(DEFAULT_DEADLINE);
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
        projectStatus = personToCopy.getProjectStatus();
        paymentStatus = personToCopy.getPaymentStatus();
        clientStatus = personToCopy.getClientStatus();
        deadline = personToCopy.getDeadline();
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
     * Sets the {@code ProjectStatus} of the {@code Person} that we are building.
     */
    public PersonBuilder withProjectStatus(String projectStatus) {
        this.projectStatus = new ProjectStatus(projectStatus);
        return this;
    }

    /**
     * Sets the {@code PaymentStatus} of the {@code Person} that we are building.
     */
    public PersonBuilder withPaymentStatus(String paymentStatus) {
        this.paymentStatus = new PaymentStatus(paymentStatus);
        return this;
    }
    /**
     * Sets the {@code ClientStatus} of the {@code Person} that we are building.
     */
    public PersonBuilder withClientStatus(String clientStatus) {
        this.clientStatus = new ClientStatus(clientStatus);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Person} that we are building
     */
    public PersonBuilder withDeadline(String deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, tags, projectStatus, paymentStatus, clientStatus, deadline);
    }

}
