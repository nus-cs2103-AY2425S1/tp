package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedWedding.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.WeddingName;

public class JsonAdaptedWeddingTest {
    private static final String INVALID_WEDDINGNAME = "adam and steve";
    private static final String INVALID_VENUE = "";
    private static final String INVALID_DATE = "22 JUN 2024";

    private static final String VALID_WEDDINGNAME = WEDDING_ONE.getWeddingName().toString();
    private static final String VALID_VENUE = WEDDING_ONE.getVenue().toString();
    private static final String VALID_DATE = WEDDING_ONE.getDate().toString();
    private static final List<JsonAdaptedPerson> VALID_PARTICIPANTS = WEDDING_ONE.getParticipants().stream()
            .map(JsonAdaptedPerson::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validWeddingDetails_returnsWedding() throws Exception {
        JsonAdaptedWedding wedding = new JsonAdaptedWedding(WEDDING_ONE);
        assertEquals(WEDDING_ONE, wedding.toModelType());
    }

    @Test
    public void toModelType_invalidWeddingName_throwsIllegalValueException() {
        JsonAdaptedWedding wedding =
                new JsonAdaptedWedding(INVALID_WEDDINGNAME, VALID_VENUE, VALID_DATE, VALID_PARTICIPANTS);
        String expectedMessage = WeddingName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, wedding::toModelType);
    }

    @Test
    public void toModelType_nullWeddingName_throwsIllegalValueException() {
        JsonAdaptedWedding wedding = new JsonAdaptedWedding(null, VALID_VENUE, VALID_DATE,
                VALID_PARTICIPANTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, WeddingName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, wedding::toModelType);
    }

    @Test
    public void toModelType_invalidVenue_throwsIllegalValueException() {
        JsonAdaptedWedding wedding =
                new JsonAdaptedWedding(VALID_WEDDINGNAME, INVALID_VENUE, VALID_DATE, VALID_PARTICIPANTS);
        String expectedMessage = Venue.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, wedding::toModelType);
    }

    @Test
    public void toModelType_nullVenue_throwsIllegalValueException() {
        JsonAdaptedWedding wedding = new JsonAdaptedWedding(VALID_WEDDINGNAME, null, VALID_DATE,
                VALID_PARTICIPANTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, wedding::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedWedding wedding = new JsonAdaptedWedding(VALID_WEDDINGNAME, VALID_VENUE, INVALID_DATE,
                VALID_PARTICIPANTS);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, wedding::toModelType);
    }

    @Test
    public void toModelType_nullDateTome_throwsIllegalValueException() {
        JsonAdaptedWedding wedding = new JsonAdaptedWedding(VALID_WEDDINGNAME, VALID_VENUE, null,
                VALID_PARTICIPANTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, wedding::toModelType);
    }

    @Test
    public void toModelType_invalidParticipants_throwsIllegalValueException() {
        List<JsonAdaptedPerson> invalidParticipants = new ArrayList<>(VALID_PARTICIPANTS);
        JsonAdaptedPerson invalidPerson = new JsonAdaptedPerson("#432", "12345678",
                "test@test.com", "address", "job", null);
        invalidParticipants.add(invalidPerson);
        JsonAdaptedWedding wedding =
                new JsonAdaptedWedding(VALID_WEDDINGNAME, VALID_VENUE, VALID_DATE, invalidParticipants);
        assertThrows(IllegalValueException.class, wedding::toModelType);
    }
}
