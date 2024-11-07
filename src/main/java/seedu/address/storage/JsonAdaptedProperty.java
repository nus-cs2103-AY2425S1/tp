package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Apartment;
import seedu.address.model.person.Bto;
import seedu.address.model.person.Condo;
import seedu.address.model.person.Hdb;
import seedu.address.model.person.HousingType;
import seedu.address.model.person.OtherProperty;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.Price;
import seedu.address.model.person.Property;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Property}.
 */
class JsonAdaptedProperty {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Property's %s field is missing!";

    private final String housingType;
    private final String postalCode;
    private final String unitNumber;
    private final String price;
    private final String actualPrice;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedProperty} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedProperty(@JsonProperty("housingType") String housingType,
                               @JsonProperty("postalCode") String postalCode,
                               @JsonProperty("unitNumber") String unitNumber,
                               @JsonProperty("price") String price,
                               @JsonProperty("actualPrice") String actualPrice,
                               @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.housingType = housingType;
        this.postalCode = postalCode;
        this.unitNumber = unitNumber;
        this.price = price;
        this.actualPrice = actualPrice;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Property} into this class for Jackson use.
     */
    public JsonAdaptedProperty(Property source) {
        housingType = source.getClass().getSimpleName();
        postalCode = source.getPostalCode().toString();
        unitNumber = source.getUnitNumber().toString();
        price = source.getPrice().toString();
        actualPrice = source.getActualPrice().toString();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted property object into the model's {@code Property} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted property.
     */
    public Property toModelType() throws IllegalValueException {
        final List<Tag> propertyTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            propertyTags.add(tag.toModelType());
        }

        if (housingType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    HousingType.class.getSimpleName()));
        }

        if (postalCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PostalCode.class.getSimpleName()));
        }
        if (!PostalCode.isValidPostalCode(postalCode)) {
            throw new IllegalValueException(PostalCode.MESSAGE_CONSTRAINTS);
        }
        final PostalCode modelPostalCode = new PostalCode(postalCode);

        if (unitNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UnitNumber.class.getSimpleName()));
        }
        if (!UnitNumber.isValidUnitNumber(unitNumber)) {
            throw new IllegalValueException(UnitNumber.MESSAGE_CONSTRAINTS);
        }
        final UnitNumber modelUnitNUmber = new UnitNumber(unitNumber);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (actualPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(actualPrice)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelActualPrice = new Price(actualPrice);

        final Set<Tag> modelTags = new HashSet<>(propertyTags);

        switch (housingType.toUpperCase()) {
        case "HDB":
            return new Hdb(modelPostalCode, modelUnitNUmber, modelPrice, modelActualPrice, modelTags);
        case "BTO":
            return new Bto(modelPostalCode, modelUnitNUmber, modelPrice, modelActualPrice, modelTags);
        case "CONDO":
            return new Condo(modelPostalCode, modelUnitNUmber, modelPrice, modelActualPrice, modelTags);
        case "APARTMENT":
            return new Apartment(modelPostalCode, modelUnitNUmber, modelPrice, modelActualPrice, modelTags);
        case "OTHERPROPERTY":
            return new OtherProperty(modelPostalCode, modelUnitNUmber, modelPrice, modelActualPrice, modelTags);
        default:
            throw new IllegalValueException("Invalid housing type");
        }
    }
}
