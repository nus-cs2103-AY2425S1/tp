package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private ObservableList<Property> buyingProperties = FXCollections.observableArrayList();
    private ObservableList<Property> sellingProperties = FXCollections.observableArrayList();
    private ObservableList<Property> propertiesBought = FXCollections.observableArrayList();
    private ObservableList<Property> propertiesSold = FXCollections.observableArrayList();
    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        buyingProperties = FXCollections.observableArrayList();;
        sellingProperties = FXCollections.observableArrayList();;
        propertiesBought = FXCollections.observableArrayList();;
        propertiesSold = FXCollections.observableArrayList();;
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
        buyingProperties = FXCollections.observableArrayList(personToCopy.getListOfBuyingProperties());
        sellingProperties = FXCollections.observableArrayList(personToCopy.getListOfSellingProperties());
        propertiesBought = FXCollections.observableArrayList(personToCopy.getListOfPropertiesBought());
        propertiesSold = FXCollections.observableArrayList(personToCopy.getListOfPropertiesSold());
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
     * Adds a new {@code property} to buy to the {@code buyingProperties} of the {@code Person} that we are building.
     */
    public PersonBuilder withBuyProperty(Property property) {
        this.buyingProperties.add(property);
        return this;
    }

    /**
     * Resets the {@code buyingProperties} of the {@code Person} that we are building.
     */
    public PersonBuilder withBuyProperty() {
        this.buyingProperties = FXCollections.observableArrayList();
        return this;
    }

    /**
     * Adds a new {@code property} to sell the {@code sellingProperties} of the {@code Person} that we are building.
     */
    public PersonBuilder withSellProperty(Property property) {
        this.sellingProperties.add(property);
        return this;
    }

    /**
     * Resets the {@code sellingProperties} of the {@code Person} that we are building.
     */
    public PersonBuilder withSellProperty() {
        this.sellingProperties = FXCollections.observableArrayList();
        return this;
    }

    /**
     * Adds a new {@code propertiesBought} of the {@code Person} that we are building.
     */
    public PersonBuilder withPropertyBought(Property property) {
        this.propertiesBought.add(property);
        return this;
    }

    /**
     * Resets the {@code propertiesBought} of the {@code Person} that we are building.
     */
    public PersonBuilder withPropertyBought() {
        this.propertiesBought = FXCollections.observableArrayList();
        return this;
    }

    /**
     * Adds a new {@code propertiesSold} of the {@code Person} that we are building.
     */
    public PersonBuilder withPropertySold(Property property) {
        this.propertiesSold.add(property);
        return this;
    }

    /**
     * Resets the {@code propertiesSold} of the {@code Person} that we are building.
     */
    public PersonBuilder withPropertySold() {
        this.propertiesSold = FXCollections.observableArrayList();
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, tags, sellingProperties, buyingProperties, propertiesSold,
                propertiesBought);
    }
}
