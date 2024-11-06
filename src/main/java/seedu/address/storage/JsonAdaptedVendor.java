package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;

import seedu.address.model.person.Vendor;

class JsonAdaptedVendor {

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedVendor} with the given vendor details.
     */
    @JsonCreator
    public JsonAdaptedVendor(String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Vendor} into this class for Jackson use.
     */
    public JsonAdaptedVendor(Vendor source) {
        this.name = source.getName().toString();
    }

    /**
     * Returns the JsonAdaptedVendor's name
     */
    public String getVendorName() {
        return this.name;
    }
}
