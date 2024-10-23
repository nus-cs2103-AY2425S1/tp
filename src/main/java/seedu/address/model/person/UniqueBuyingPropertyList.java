package seedu.address.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static java.util.Objects.requireNonNull;

public class UniqueBuyingPropertyList {
    private int uniqueBuyingPropertiesCount;

    private ObservableList<Property> uniqueBuyingProperties = FXCollections.observableArrayList();

    public void addUniqueBuyingProperties(ObservableList<Property> properties) {
        requireNonNull(properties);
        for (Property p: properties) {
            if (!uniqueBuyingProperties.contains(p)) {
                uniqueBuyingProperties.add(p);
                uniqueBuyingPropertiesCount += 1;
            }
        }
    }

    public UniqueBuyingPropertyList getUniqueBuyingPropertiesList() {
        return (UniqueBuyingPropertyList) uniqueBuyingProperties;
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
