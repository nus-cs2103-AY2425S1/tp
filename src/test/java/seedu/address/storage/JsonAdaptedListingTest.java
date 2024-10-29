package seedu.address.storage;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedListing.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;

public class JsonAdaptedListingTest {

    private static final String INVALID_LISTING_NAME = "@##$";
    private static final String INVALID_PRICE = "12!1";
    private static final String INVALID_AREA = "!!!";
    private static final String INVALID_REGION = "NE";
    private static final String INVALID_ADDRESS = " ";

    private static final String VALID_LISTING_NAME = PASIR_RIS.getName().toString();
    private static final String VALID_AREA = PASIR_RIS.getArea().toString();
    private static final String VALID_PRICE = PASIR_RIS.getPrice().toString();
    private static final String VALID_ADDRESS = PASIR_RIS.getAddress().toString();
    private static final String VALID_REGION = PASIR_RIS.getRegion().toString();
    private static final JsonAdaptedPerson VALID_SELLER = new JsonAdaptedPerson(PASIR_RIS.getSeller());
    private static final List<JsonAdaptedPerson> VALID_BUYERS = PASIR_RIS.getBuyers()
            .stream()
            .map(JsonAdaptedPerson::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validListingDetails_returnsListing() throws Exception {
        JsonAdaptedListing listing = new JsonAdaptedListing(PASIR_RIS);
        assertEquals(PASIR_RIS, listing.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedListing listing = new JsonAdaptedListing(INVALID_LISTING_NAME,
                VALID_PRICE, VALID_AREA, VALID_REGION, VALID_ADDRESS, VALID_SELLER, VALID_BUYERS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, listing::toModelType);
    }

    @Test
    public void toModelType_nullListingName_throwsIllegalValueException() {
        JsonAdaptedListing listing = new JsonAdaptedListing(null,
                VALID_PRICE, VALID_AREA, VALID_REGION, VALID_ADDRESS, VALID_SELLER, VALID_BUYERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, listing::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedListing listing = new JsonAdaptedListing(VALID_LISTING_NAME,
                INVALID_PRICE, VALID_AREA, VALID_REGION, VALID_ADDRESS, VALID_SELLER, VALID_BUYERS);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, listing::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedListing listing = new JsonAdaptedListing(VALID_LISTING_NAME,
                null, VALID_AREA, VALID_REGION, VALID_ADDRESS, VALID_SELLER, VALID_BUYERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, listing::toModelType);
    }

    @Test
    public void toModelType_invalidArea_throwsIllegalValueException() {
        JsonAdaptedListing listing = new JsonAdaptedListing(VALID_LISTING_NAME,
                VALID_PRICE, INVALID_AREA, VALID_REGION, VALID_ADDRESS, VALID_SELLER, VALID_BUYERS);
        String expectedMessage = Area.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, listing::toModelType);
    }

    @Test
    public void toModelType_nullArea_throwsIllegalValueException() {
        JsonAdaptedListing listing = new JsonAdaptedListing(VALID_LISTING_NAME,
                VALID_PRICE, null, VALID_REGION, VALID_ADDRESS, VALID_SELLER, VALID_BUYERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Area.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, listing::toModelType);
    }

    @Test
    public void toModelType_invalidRegion_throwsIllegalValueException() {
        JsonAdaptedListing listing = new JsonAdaptedListing(VALID_LISTING_NAME,
                VALID_PRICE, VALID_AREA, INVALID_REGION, VALID_ADDRESS, VALID_SELLER, VALID_BUYERS);
        String expectedMessage = Region.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, listing::toModelType);
    }

    @Test
    public void toModelType_nullRegion_throwsIllegalValueException() {
        JsonAdaptedListing listing = new JsonAdaptedListing(VALID_LISTING_NAME,
                VALID_PRICE, VALID_AREA, null, VALID_ADDRESS, VALID_SELLER, VALID_BUYERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Region.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, listing::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedListing listing = new JsonAdaptedListing(VALID_LISTING_NAME,
                VALID_PRICE, VALID_AREA, VALID_REGION, INVALID_ADDRESS, VALID_SELLER, VALID_BUYERS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, listing::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedListing listing = new JsonAdaptedListing(VALID_LISTING_NAME,
                VALID_PRICE, VALID_AREA, VALID_REGION, null, VALID_SELLER, VALID_BUYERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, listing::toModelType);
    }

    @Test
    public void toModelType_nullSeller_throwsIllegalValueException() {
        JsonAdaptedListing listing = new JsonAdaptedListing(VALID_LISTING_NAME,
                VALID_PRICE, VALID_AREA, VALID_REGION, VALID_ADDRESS, null, VALID_BUYERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, listing::toModelType);
    }

}
