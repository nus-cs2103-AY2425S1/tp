package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wedding.Wedding;


/**
 * A utility class to help with building Vendor objects.
 */
public class VendorBuilder {

    public static final String DEFAULT_NAME = "Alison Longwood";
    // default address is blank string as address is optional
    public static final String DEFAULT_ADDRESS = "";
    // default phone number is blank string as phone number is optional
    public static final String DEFAULT_PHONE = "";
    // default email is blank string as email is optional
    public static final String DEFAULT_EMAIL = "";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Set<Wedding> weddings;

    private Set<Task> tasks;

    /**
     * Creates a {@code VendorBuilder} with the default details.
     */
    public VendorBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        weddings = new HashSet<>();
        tasks = new HashSet<>();
    }

    /**
     * Initializes the VendorBuilder with the data of {@code vendorToCopy}.
     */
    public VendorBuilder(Vendor vendorToCopy) {
        name = vendorToCopy.getName();
        phone = vendorToCopy.getPhone();
        email = vendorToCopy.getEmail();
        address = vendorToCopy.getAddress();
        tags = new HashSet<>(vendorToCopy.getTags());
        weddings = new HashSet<>(vendorToCopy.getWeddings());
        tasks = new HashSet<>(vendorToCopy.getTasks());
    }

    /**
     * Sets the {@code Name} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Vendor} that we are building.
     */
    public VendorBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code weddings} into a {@code Set<Wedding>} and set it to the {@code Vendor} that we are building.
     */
    public VendorBuilder withWeddings(String ... weddings) {
        this.weddings = SampleDataUtil.getWeddingSet(weddings);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Task} objects to the {@code Person} that we are building.
     * You can either modify this method to accept specific tasks like Todo, Deadline, or Event
     * or adapt the SampleDataUtil to properly convert them.
     */
    public VendorBuilder withTasks(String ... tasks) {
        this.tasks = SampleDataUtil.getTaskSet(tasks);
        return this;
    }

    public Vendor build() {
        return new Vendor(name, phone, email, address, tags, weddings, tasks);
    }
}
