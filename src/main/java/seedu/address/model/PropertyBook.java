package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.property.Property;

public class PropertyBook {
    private final List<Property> properties = new ArrayList<>();

    public void addProperty(Property property) {
        properties.add(property);
    }

    public boolean hasProperty(Property property) {
        return properties.contains(property);
    }

    public List<Property> getPropertyList() {
        return new ArrayList<>(properties);
    }
}
