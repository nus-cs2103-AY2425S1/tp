package seedu.address.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static java.util.Objects.requireNonNull;

public class UniqueSellingPropertyList {
    private int uniqueSellingPropertiesCount;

    private ObservableList<Property> uniqueSellingProperties = FXCollections.observableArrayList();

    public void addUniqueSellingProperties(ObservableList<Property> properties) {
        requireNonNull(properties);
        for (Property p: properties) {
            if (!uniqueSellingProperties.contains(p)) {
                uniqueSellingProperties.add(p);
                uniqueSellingPropertiesCount += 1;
            }
        }
    }

    public UniqueSellingPropertyList getUniqueSellingPropertiesList() {
        return (UniqueSellingPropertyList) uniqueSellingProperties;
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
