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

}
