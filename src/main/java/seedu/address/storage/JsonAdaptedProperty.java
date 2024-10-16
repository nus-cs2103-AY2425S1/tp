package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Apartment;
import seedu.address.model.person.Bto;
import seedu.address.model.person.Condo;
import seedu.address.model.person.Hdb;
import seedu.address.model.person.OtherProperty;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.Price;
import seedu.address.model.person.Property;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Property}.
 */
class JsonAdaptedProperty {

    private final String propertyString;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedProperty(String propertyString) {
        this.propertyString = propertyString;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedProperty(Property source) {
        propertyString =
                source.toString() + " Price: " + source.getPrice() + " Tags: " + source.getTags().toString()
                        + " HousingType: " + source.getClass().getSimpleName();
    }

    @JsonValue
    public String getPropertyString() {
        return propertyString;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Property} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted property.
     */
    public Property toModelType() throws IllegalValueException {
        String housingType = propertyString.split(" HousingType: ")[1];
        PostalCode postalcode = new PostalCode(propertyString.split(" Price: ")[0].split(" ")[0]);
        UnitNumber unitnumber = new UnitNumber(propertyString.split(" Price: ")[0].split(" ")[1]);
        Price price = new Price(propertyString.split(" Price: ")[1].split(" Tags: ")[0]);
        Set<Tag> tag = new HashSet<>();
        switch (housingType.toUpperCase()) {
        case "HDB":
            return new Hdb(postalcode, unitnumber, price, tag);
        case "CONDO":
            return new Condo(postalcode, unitnumber, price, tag);
        case "APARTMENT":
            return new Apartment(postalcode, unitnumber, price, tag);
        case "BTO":
            return new Bto(postalcode, unitnumber, price, tag);
        case "OTHERPROPERTY":
            return new OtherProperty(postalcode, unitnumber, price, tag);
        default:
            throw new IllegalValueException(Property.MESSAGE_CONSTRAINTS);
        }
    }

}
