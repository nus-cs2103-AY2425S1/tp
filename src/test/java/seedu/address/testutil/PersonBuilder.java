package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Date;
import seedu.address.model.person.DonatedAmount;
import seedu.address.model.person.Donor;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hours;
import seedu.address.model.person.Name;
import seedu.address.model.person.Partner;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonFactory;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Volunteer;
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
    public static final String DEFAULT_HOURS = "123";
    public static final String DEFAULT_DONATED_AMOUNT = "1000";
    public static final String DEFAULT_PARTNERSHIP_END_DATE = "2023-12-31";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Hours hours;
    private DonatedAmount donatedAmount;
    private Date partnershipEndDate;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
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
        tags = new HashSet<>(personToCopy.getTags());
        if (personToCopy instanceof Volunteer) {
            hours = ((Volunteer) personToCopy).getHours();
        } else if (personToCopy instanceof Donor) {
            donatedAmount = ((Donor) personToCopy).getDonatedAmount();
        } else if (personToCopy instanceof Partner) {
            partnershipEndDate = ((Partner) personToCopy).getEndDate();
        }
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
     * Sets the {@code Hours} of the {@code Person} that we are building.
     */
    public PersonBuilder withHours(String hours) {
        this.hours = new Hours(hours);
        this.donatedAmount = null;
        this.partnershipEndDate = null;
        return this;
    }

    /**
     * Sets the {@code DonatedAmount} of the {@code Person} that we are building.
     */
    public PersonBuilder withDonatedAmount(String donatedAmount) {
        this.donatedAmount = new DonatedAmount(donatedAmount);
        this.hours = null;
        this.partnershipEndDate = null;
        return this;
    }

    /**
     * Sets the {@code PartnershipEndDate} of the {@code Person} that we are building.
     */
    public PersonBuilder withPartnershipEndDate(String partnershipEndDate) {
        this.partnershipEndDate = new Date(partnershipEndDate);
        this.hours = null;
        this.donatedAmount = null;
        return this;
    }

    public Person build() {
        return PersonFactory.createPerson(name, phone, email, address, tags, hours, donatedAmount, partnershipEndDate);
    }

}
