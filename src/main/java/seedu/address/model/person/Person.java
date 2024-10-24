package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.SortIndividualCommand;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    // Property details
    private ObservableList<Property> sellingProperties = FXCollections.observableArrayList();
    private ObservableList<Property> buyingProperties = FXCollections.observableArrayList();
    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for when there are properties to be added.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  ObservableList<Property> sellingProperties,
                  ObservableList<Property> buyingProperties) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.sellingProperties = sellingProperties;
        this.buyingProperties = buyingProperties;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public ObservableList<Property> getListOfSellingProperties() {
        return sellingProperties;
    }

    public ObservableList<Property> getListOfBuyingProperties() {
        return buyingProperties;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if property is in the list of properties to buy.
     * @param property Property to check
     * @return boolean
     */
    public boolean containsBuyProperty(Property property) {
        return buyingProperties.contains(property);
    }

    /**
     * Adds a property to the list of properties to buy.
     * @param property Property to add
     */
    public void addBuyProperty(Property property) {
        buyingProperties.add(property);
        int isSupposedToBeMaintainedInSortedState = SortIndividualCommand.getSortStatus();
        int isSortedFromLowToHigh = SortIndividualCommand.getIsFromLowToHighOrder();
        if (isSupposedToBeMaintainedInSortedState == 1 && isSortedFromLowToHigh == 1) {
            buyingProperties.sort((property1, property2) -> property1.getPrice().compareTo(property2.getPrice()));
        } else if (isSupposedToBeMaintainedInSortedState == 1 && isSortedFromLowToHigh == 0) {
            buyingProperties.sort((property1, property2) -> property2.getPrice().compareTo(property1.getPrice()));
        }
    }

    /**
     * Returns true if property is in the list of properties to sell.
     * @param property Property to check
     * @return boolean
     */
    public boolean containsSellProperty(Property property) {
        return sellingProperties.contains(property);
    }

    /**
     * Adds a property to the list of properties to sell.
     * @param property Property to add
     */
    public void addSellProperty(Property property) {
        sellingProperties.add(property);
        int isSupposedToBeMaintainedInSortedState = SortIndividualCommand.getSortStatus();
        int isSortedFromLowToHigh = SortIndividualCommand.getIsFromLowToHighOrder();
        if (isSupposedToBeMaintainedInSortedState == 1 && isSortedFromLowToHigh == 1) {
            sellingProperties.sort((property1, property2) -> property1.getPrice().compareTo(property2.getPrice()));
        } else if (isSupposedToBeMaintainedInSortedState == 1 && isSortedFromLowToHigh == 0) {
            sellingProperties.sort((property1, property2) -> property2.getPrice().compareTo(property1.getPrice()));
        }
    }

    /**
     * Deletes a property from the list of properties to sell.
     * @param index One based Index of property to delete based on user's view.
     */
    public void deleteSellProperty(Index index) {
        sellingProperties.remove(index.getZeroBased());
    }

    /**
     * Deletes a property from the list of properties to buy.
     * @param index One based Index of property to delete based on user's view.
     */
    public void deleteBuyProperty(Index index) {
        buyingProperties.remove(index.getZeroBased());
    }
    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
