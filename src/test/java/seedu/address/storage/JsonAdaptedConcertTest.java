package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.concert.ConcertDate.INPUT_DATE_FORMATTER;
import static seedu.address.model.concert.ConcertDate.OUTPUT_DATE_FORMATTER;
import static seedu.address.storage.JsonAdaptedConcert.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalConcerts.COACHELLA;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commons.Address;
import seedu.address.model.commons.Name;
import seedu.address.model.concert.ConcertDate;

public class JsonAdaptedConcertTest {
    private static final String INVALID_NAME = "@pw!12W";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_DATE = "qwerty";

    private static final String VALID_NAME = COACHELLA.getName().toString();
    private static final String VALID_ADDRESS = COACHELLA.getAddress().toString();
    private static final String VALID_DATE = ConcertDate.processDate(COACHELLA
            .getDate().concertDate, OUTPUT_DATE_FORMATTER, INPUT_DATE_FORMATTER);

    @Test
    public void toModelType_validConcertDetails_returnsConcert() throws Exception {
        JsonAdaptedConcert conert = new JsonAdaptedConcert(COACHELLA);
        assertEquals(COACHELLA, conert.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedConcert concert = new JsonAdaptedConcert(INVALID_NAME, VALID_ADDRESS,
                VALID_DATE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, concert::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedConcert concert = new JsonAdaptedConcert(null, VALID_ADDRESS, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class
                .getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, concert::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedConcert concert = new JsonAdaptedConcert(VALID_NAME, INVALID_ADDRESS,
                VALID_DATE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, concert::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedConcert concert = new JsonAdaptedConcert(VALID_NAME, null, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class
                .getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, concert::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedConcert concert = new JsonAdaptedConcert(VALID_NAME, VALID_ADDRESS,
                INVALID_DATE);
        String expectedMessage = ConcertDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, concert::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedConcert concert = new JsonAdaptedConcert(VALID_NAME, VALID_ADDRESS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ConcertDate.class
                .getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, concert::toModelType);
    }
}
