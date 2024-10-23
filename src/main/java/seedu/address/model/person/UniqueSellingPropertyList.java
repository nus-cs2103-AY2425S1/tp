package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of unique properties that users want to sell.
 */
public class UniqueSellingPropertyList {
    private int uniqueSellingPropertiesCount;

    private ObservableList<Property> uniqueSellingProperties = FXCollections.observableArrayList();

    /**
     * Adds {@code properties} to a list of unique property to sell list only if the current list
     * does not contain this property.
     *
     * @param properties Properties that all users in the address book want to sell.
     */
    public void addUniqueSellingProperties(ObservableList<Property> properties) {
        requireNonNull(properties);
        for (Property p: properties) {
            if (!uniqueSellingProperties.contains(p)) {
                uniqueSellingProperties.add(p);
                uniqueSellingPropertiesCount += 1;
            }
        }
    }

    /**
     * Returns the number of unique properties to sell by all persons in the address book.
     */
    public int getUniqueSellingPropertiesCount() {
        return uniqueSellingPropertiesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UniqueSellingPropertyList)) {
            return false;
        }

        UniqueSellingPropertyList that = (UniqueSellingPropertyList) o;
        return uniqueSellingProperties.equals(that.uniqueSellingProperties); // Compare the lists
    }

    @Override
    public int hashCode() {
        return uniqueSellingProperties.hashCode(); // Use the list's hashcode
    }

}
