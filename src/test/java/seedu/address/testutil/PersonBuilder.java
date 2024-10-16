package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Property;
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
    public static final Property DEFAULT_BUYING_PROPERTY = new PropertyToBuyBuilder().build();
    public static final Property DEFAULT_SELLING_PROPERTY = new PropertyToSellBuilder().build();

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private List<Property> buyingProperties = new ArrayList<>();
    private List<Property> sellingProperties;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        sellingProperties = new ArrayList<>((Collection<Property>) DEFAULT_SELLING_PROPERTY);
        buyingProperties = new ArrayList<>((Collection<Property>) DEFAULT_BUYING_PROPERTY);
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
        sellingProperties = new ArrayList<>(personToCopy.getListOfSellingProperties());
        buyingProperties = new ArrayList<>(personToCopy.getListOfBuyingProperties());
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
     * Sets the {@code buyingProperties} of the {@code Person} that we are building.
     */
    public PersonBuilder withBuyProperty(Property property) {
        this.buyingProperties.add(property);
        return this;
    }

    /**
     * Sets the {@code sellingProperties} of the {@code Person} that we are building.
     */
    public PersonBuilder withSellProperty(Property property) {
        this.sellingProperties.add(property);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, tags, sellingProperties, buyingProperties);
    }

}
