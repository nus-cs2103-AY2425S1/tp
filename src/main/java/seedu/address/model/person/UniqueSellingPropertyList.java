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

}