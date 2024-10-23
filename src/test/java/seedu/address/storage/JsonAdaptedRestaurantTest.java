package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedRestaurant.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRestaurants.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Phone;

public class JsonAdaptedRestaurantTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final Integer INVALID_RATING = 123123;
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final Integer VALID_RATING = BENSON.getRating().value;
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validRestaurantDetails_returnsRestaurant() throws Exception {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(BENSON);
        assertEquals(BENSON, restaurant.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant =
                new JsonAdaptedRestaurant(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_RATING,
                        VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(
                null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_RATING, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant =
                new JsonAdaptedRestaurant(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_RATING,
                        VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(
                VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_RATING, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant =
                new JsonAdaptedRestaurant(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_RATING,
                        VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(
                VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_RATING, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant =
                new JsonAdaptedRestaurant(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_RATING,
                        VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_RATING, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedRestaurant restaurant =
                new JsonAdaptedRestaurant(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_RATING,
                        invalidTags);
        assertThrows(IllegalValueException.class, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidRating_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant =
                new JsonAdaptedRestaurant(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_RATING,
                        VALID_TAGS);
        assertThrows(IllegalValueException.class, restaurant::toModelType);
    }

}
