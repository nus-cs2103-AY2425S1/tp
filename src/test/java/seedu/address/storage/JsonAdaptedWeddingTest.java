package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.JsonAdaptedWedding.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWeddings.ALICE_WEDDING;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.wedding.Client;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;


public class JsonAdaptedWeddingTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE = "32-13-2020";
    private static final String INVALID_VENUE = " ";

    private static final String VALID_NAME = ALICE_WEDDING.getName().toString();
    private static final JsonAdaptedPerson VALID_CLIENT =
            new JsonAdaptedPerson(ALICE_WEDDING.getClient().getPerson());
    private static final String VALID_DATE = ALICE_WEDDING.getDate().toString();
    private static final String VALID_VENUE = ALICE_WEDDING.getVenue().toString();
    private static final List<Wedding> VALID_WEDDING_LIST = new ArrayList<>();

    private static final JsonAdaptedWedding ALICE_JSON = new JsonAdaptedWedding(
            VALID_NAME, VALID_CLIENT, VALID_DATE, VALID_VENUE);

    @Test
    public void toModelType_validWeddingDetails_returnsWedding() throws Exception {
        JsonAdaptedWedding wedding = new JsonAdaptedWedding(ALICE_WEDDING);
        assertEquals(ALICE_WEDDING, wedding.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedWedding wedding = new JsonAdaptedWedding(
                INVALID_NAME, VALID_CLIENT, VALID_DATE, VALID_VENUE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                wedding.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedWedding wedding = new JsonAdaptedWedding(
                null, VALID_CLIENT, VALID_DATE, VALID_VENUE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                wedding.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_nullClient_throwsIllegalValueException() {
        JsonAdaptedWedding wedding = new JsonAdaptedWedding(
                VALID_NAME, null, VALID_DATE, VALID_VENUE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Client.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                wedding.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedWedding wedding = new JsonAdaptedWedding(
                VALID_NAME, VALID_CLIENT, INVALID_DATE, VALID_VENUE);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                wedding.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedWedding wedding = new JsonAdaptedWedding(
                VALID_NAME, VALID_CLIENT, null, VALID_VENUE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                wedding.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_invalidVenue_throwsIllegalValueException() {
        JsonAdaptedWedding wedding = new JsonAdaptedWedding(
                VALID_NAME, VALID_CLIENT, VALID_DATE, INVALID_VENUE);
        String expectedMessage = Venue.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                wedding.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void toModelType_nullVenue_throwsIllegalValueException() {
        JsonAdaptedWedding wedding = new JsonAdaptedWedding(
                VALID_NAME, VALID_CLIENT, VALID_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () ->
                wedding.toModelType(VALID_WEDDING_LIST));
    }

    @Test
    public void equals_SameObject_ReturnsTrue() {
        assertTrue(ALICE_JSON.equals(ALICE_JSON));
    }

    @Test
    public void equals_NullObject_ReturnsFalse() {
        assertFalse(ALICE_JSON.equals(null));
    }

    @Test
    public void equals_DifferentClass_ReturnsFalse() {
        assertFalse(ALICE_JSON.equals("not a wedding"));
    }

    @Test
    public void equals_SameValues_ReturnsTrue() {
        JsonAdaptedWedding wedding1 = new JsonAdaptedWedding(
                VALID_NAME, VALID_CLIENT, VALID_DATE, VALID_VENUE);
        assertTrue(ALICE_JSON.equals(wedding1));
    }

    @Test
    public void equals_DifferentValues_ReturnsFalse() {
        JsonAdaptedWedding wedding1 = new JsonAdaptedWedding(
                "Not Alice", VALID_CLIENT, VALID_DATE, VALID_VENUE);
        assertFalse(ALICE_JSON.equals(wedding1));
    }
    @Test
    public void hashCode_SameValues_SameHashCode() {
        JsonAdaptedWedding wedding1 = new JsonAdaptedWedding(
                VALID_NAME, VALID_CLIENT, VALID_DATE, VALID_VENUE);
        assertEquals(ALICE_JSON.hashCode(), wedding1.hashCode());
    }

    @Test
    public void hashCode_DifferentValues_DifferentHashCode() {
        JsonAdaptedWedding wedding1 = new JsonAdaptedWedding(
                "Not Alice", VALID_CLIENT, VALID_DATE, VALID_VENUE);
        assertFalse(ALICE_WEDDING.hashCode() == wedding1.hashCode());
    }

    @Test
    public void hashCode_ConsistentResults() {
        // Hash code should be consistent across multiple calls
        int initialHashCode = ALICE_JSON.hashCode();
        assertEquals(initialHashCode, ALICE_JSON.hashCode());
        assertEquals(initialHashCode, ALICE_JSON.hashCode());
    }
}
