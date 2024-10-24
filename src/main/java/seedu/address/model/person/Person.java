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
    private ObservableList<Property> propertiesSold = FXCollections.observableArrayList();
    private ObservableList<Property> propertiesBought = FXCollections.observableArrayList();


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
                  ObservableList<Property> buyingProperties,
                  ObservableList<Property> propertiesSold,
                  ObservableList<Property> propertiesBought) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.sellingProperties = sellingProperties;
        this.buyingProperties = buyingProperties;
        this.propertiesSold = propertiesSold;
        this.propertiesBought = propertiesBought;
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

    public ObservableList<Property> getListOfPropertiesSold() {
        return propertiesSold;
    }

    public ObservableList<Property> getListOfPropertiesBought() {
        return propertiesBought;
    }

    /**
     * Returns the total sales revenue for this person who has sold properties.
     */
    public int getSalesRevenue() {
        return this.getListOfPropertiesSold().stream().mapToInt(property -> (property.getActualPrice() != null)
                ? property.getActualPrice().getPrice() : new Price("0").getPrice()).sum();
    }

    /**
     * Returns the total purchase expense for this person who has bought properties.
     */
    public int getPurchaseExpense() {
        return this.getListOfPropertiesBought().stream().mapToInt(property -> (property.getActualPrice() != null)
                ? property.getActualPrice().getPrice() : new Price("0").getPrice()).sum();
    }

    /**
     * Returns the number of properties sold by this person.
     */
    public int getNumberOfPropertiesSold() {
        return this.propertiesSold.size();
    }

    /**
     * Returns the number of properties bought by this person.
     */
    public int getNumberOfPropertiesBought() {
        return this.propertiesBought.size();
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
     *
     * @param property Property to check
     * @return boolean
     */
    public boolean containsBuyProperty(Property property) {
        return buyingProperties.contains(property);
    }

    /**
     * Adds a property to the list of properties to buy.
     *
     * @param property Property to add
     */
    public void addBuyProperty(Property property) {
        buyingProperties.add(property);
    }

    /**
     * Returns true if property is in the list of properties to sell.
     *
     * @param property Property to check
     * @return boolean
     */
    public boolean containsSellProperty(Property property) {
        return sellingProperties.contains(property);
    }

    /**
     * Adds a property to the list of properties to sell.
     *
     * @param property Property to add
     */
    public void addSellProperty(Property property) {
        sellingProperties.add(property);
    }

    /**
     * Deletes a property from the list of properties to sell.
     *
     * @param index One based Index of property to delete based on user's view.
     */
    public void deleteSellProperty(Index index) {
        sellingProperties.remove(index.getZeroBased());
    }

    /**
     * Deletes a property from the list of properties to buy.
     *
     * @param index One based Index of property to delete based on user's view.
     */
    public void deleteBuyProperty(Index index) {
        buyingProperties.remove(index.getZeroBased());
    }


    /**
     * Returns the {@code Property} purchased with its updated price.
     *
     * @param index One based Index of property bought in the list of properties to buy.
     * @param actualPrice {@code Price} of the actual price of the property provided by the user.
     */
    public Property getBoughtProperty(Index index, Price actualPrice) {
        Property propertyToBeUpdated = buyingProperties.get(index.getZeroBased());
        propertyToBeUpdated.setActualPrice(actualPrice);
        return propertyToBeUpdated;
    }

    /**
     * Updates the properties to buy list and bought properties list with the {@code Property} purchased.
     *
     * @param updatedProperty Property bought with the updated actual price.
     * @param oldPropertyIndex One based Index of property bought in the list of properties to buy.
     */
    public void updateBoughtProperty(Property updatedProperty, Index oldPropertyIndex) {
        requireAllNonNull(updatedProperty, oldPropertyIndex);
        propertiesBought.add(updatedProperty);
        buyingProperties.remove(oldPropertyIndex.getZeroBased());
    }

    /**
     * Returns the {@code Property} sold with its updated price.
     *
     * @param index One based Index of property sold in the list of properties to sell.
     * @param actualPrice {@code Price} of the actual price of the property provided by the user.
     */
    public Property getSoldProperty(Index index, Price actualPrice) {
        Property propertyToBeUpdated = sellingProperties.get(index.getZeroBased());
        propertyToBeUpdated.setActualPrice(actualPrice);
        return propertyToBeUpdated;
    }

    /**
     * Updates the properties to sell list and sold properties list with the {@code Property} purchased.
     *
     * @param updatedProperty Property sold with the updated actual price.
     * @param oldPropertyIndex One based Index of property bought in the list of properties to sell.
     */
    public void updateSoldProperty(Property updatedProperty, Index oldPropertyIndex) {
        requireAllNonNull(updatedProperty, oldPropertyIndex);
        propertiesSold.add(updatedProperty);
        sellingProperties.remove(oldPropertyIndex.getZeroBased());
    }



    /**
     * Returns True if the propertyIndex {@code Index} is within the range of the list of selling properties.
     *
     * @param propertyIndex One based index of the property in the property list as seen by the user.
     */
    public boolean isValidSellingPropertyIndex(Index propertyIndex) {
        int index = propertyIndex.getZeroBased();
        return (index >= 0 && index < sellingProperties.size());
    }

    /**
     * Returns True if the propertyIndex {@code Index} is within the range of the list of selling properties.
     *
     * @param propertyIndex One based index of the property in the property list as seen by the user.
     */
    public boolean isValidBuyingPropertyIndex(Index propertyIndex) {
        int index = propertyIndex.getZeroBased();
        return (index >= 0 && index < buyingProperties.size());
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
