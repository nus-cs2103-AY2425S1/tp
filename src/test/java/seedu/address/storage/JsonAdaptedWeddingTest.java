package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedWedding.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Client;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.WeddingBuilder;

public class JsonAdaptedWeddingTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE = "32-13-2020";
    private static final String INVALID_VENUE = " ";

    private static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withPhone("94351253").withEmail("alicePushingP@gmail.com").withAddress("123, Jurong West Ave 6, #08-111")
            .withTags("friends").build();
    private static final Wedding ALICE_WEDDING = new WeddingBuilder().withName("Alice Wedding")
            .withClient(ALICE).withDate("2020-10-10").withVenue("Alice's Wedding Venue").build();

    private static final String VALID_NAME = ALICE_WEDDING.getName().toString();
    private static final JsonAdaptedPerson VALID_CLIENT =
            new JsonAdaptedPerson(ALICE_WEDDING.getClient().getPerson());
    private static final String VALID_DATE = ALICE_WEDDING.getDate().toString();
    private static final String VALID_VENUE = ALICE_WEDDING.getVenue().toString();
    private static final List<Wedding> VALID_WEDDING_LIST = new ArrayList<>();

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
}
