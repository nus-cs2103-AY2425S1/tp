package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of unique properties that users want to buy.
 */
public class UniqueBuyingPropertyList {
    private int uniqueBuyingPropertiesCount;

    private ObservableList<Property> uniqueBuyingProperties = FXCollections.observableArrayList();

    /**
     * Adds {@code properties} to a list of unique property to buy list only if the current list
     * does not contain this property.
     *
     * @param properties Properties that all users in the address book want to buy.
     */
    public void addUniqueBuyingProperties(ObservableList<Property> properties) {
        requireNonNull(properties);
        for (Property p: properties) {
            if (!uniqueBuyingProperties.contains(p)) {
                uniqueBuyingProperties.add(p);
                uniqueBuyingPropertiesCount += 1;
            }
        }
    }

    /**
     * Returns the number of unique properties to buy by all persons in the address book.
     */
    public int getUniqueBuyingPropertiesCount() {
        return uniqueBuyingPropertiesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UniqueBuyingPropertyList)) {
            return false;
        }

        UniqueBuyingPropertyList that = (UniqueBuyingPropertyList) o;
        return uniqueBuyingProperties.equals(that.uniqueBuyingProperties); // Compare the lists
    }

    @Override
    public int hashCode() {
        return uniqueBuyingProperties.hashCode(); // Use the list's hashcode
    }

}
