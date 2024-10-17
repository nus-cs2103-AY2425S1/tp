package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Client objects.
 */
public class PersonBuilder {
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";

    private Name name;
    private Phone phone;
    private Email email;
    private Set<Tag> tags;
    private List<RentalInformation> rentalInformationList;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        tags = new HashSet<>();
        rentalInformationList = new ArrayList<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code clientToCopy}.
     */
    public PersonBuilder(Client clientToCopy) {
        name = clientToCopy.getName();
        phone = clientToCopy.getPhone();
        email = clientToCopy.getEmail();
        tags = new HashSet<>(clientToCopy.getTags());
        rentalInformationList = new ArrayList<>(clientToCopy.getRentalInformation());
    }

    /**
     * Sets the {@code Name} of the {@code Client} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Client} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code RentalInformation} of the {@code Client} that we are building.
     */
    public PersonBuilder withRentalInformation(RentalInformation... rentalInformation) {
        this.rentalInformationList = List.of(rentalInformation);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Client} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        if (phone.isEmpty()) {
            this.phone = new Phone();
        } else {
            this.phone = new Phone(phone);
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Client} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        if (email.isEmpty()) {
            this.email = new Email();
        } else {
            this.email = new Email(email);
        }
        return this;
    }

    /**
     * Returns the {@code Client} that we have built.
     */
    public Client build() {
        return new Client(name, phone, email, tags, rentalInformationList);
    }
}
