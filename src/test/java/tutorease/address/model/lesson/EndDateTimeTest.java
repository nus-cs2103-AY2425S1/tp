package tutorease.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DAY;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DURATION_CHAR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DURATION_NEGATIVE;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DURATION_NOT_MULTIPLE_OF_POINT_FIVE;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DURATION_TWENTY_FIVE;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DURATION_TWENTY_FIVE_POINT_FIVE;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DURATION_ZERO;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_HOUR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_MINUTE;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_MONTH;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_YEAR;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_DURATION;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_DURATION_LOWER_BOUND;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_DURATION_UPPER_BOUND;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_DURATION_WITH_DECIMALS;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_DURATION_WITH_POINT_FIVE;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_START_DATE;
import static tutorease.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tutorease.address.logic.parser.exceptions.ParseException;

public class EndDateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> EndDateTime.createEndDateTime(null));
    }

    @Test
    public void constructor_invalidDateTimeD_throwsParseException() {
        assertThrows(ParseException.class, () -> EndDateTime.createEndDateTime(INVALID_DAY));
    }

    @Test
    public void constructor_invalidDateTimeM_throwsParseException() {
        assertThrows(ParseException.class, () -> EndDateTime.createEndDateTime(INVALID_MONTH));
    }

    @Test
    public void constructor_invalidDateTimeY_throwsParseException() {
        assertThrows(ParseException.class, () -> EndDateTime.createEndDateTime(INVALID_YEAR));
    }

    @Test
    public void constructor_invalidDateTimeH_throwsParseException() {
        assertThrows(ParseException.class, () -> EndDateTime.createEndDateTime(INVALID_HOUR));
    }

    @Test
    public void constructor_invalidDateTimeMM_throwsParseException() {
        assertThrows(ParseException.class, () -> EndDateTime.createEndDateTime(INVALID_MINUTE));
    }

    @Test
    public void constructor_invalidStartDateTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> EndDateTime.createEndDateTime(null, null));
    }

    @Test
    public void constructor_invalidHour_throwsParseException() throws ParseException {
        StartDateTime startDateTime = StartDateTime.createStartDateTime(VALID_START_DATE);

        assertThrows(IllegalArgumentException.class, () ->
                EndDateTime.createEndDateTime(startDateTime, INVALID_DURATION_ZERO));
        assertThrows(IllegalArgumentException.class, () ->
                EndDateTime.createEndDateTime(startDateTime, INVALID_DURATION_CHAR));
        assertThrows(IllegalArgumentException.class, () ->
                EndDateTime.createEndDateTime(startDateTime, INVALID_DURATION_TWENTY_FIVE));


    }
    @Test
    public void isValidHoursToAdd() {
        // null hours
        assertThrows(NullPointerException.class, () -> EndDateTime.isValidHoursToAdd(null));

        // invalid hours
        assertFalse(EndDateTime.isValidHoursToAdd("")); // empty string
        assertFalse(EndDateTime.isValidHoursToAdd(" ")); // spaces only
        assertFalse(EndDateTime.isValidHoursToAdd(INVALID_DURATION_CHAR));
        assertFalse(EndDateTime.isValidHoursToAdd(INVALID_DURATION_ZERO));
        assertFalse(EndDateTime.isValidHoursToAdd(INVALID_DURATION_TWENTY_FIVE));
        assertFalse(EndDateTime.isValidHoursToAdd(INVALID_DURATION_TWENTY_FIVE_POINT_FIVE));
        assertFalse(EndDateTime.isValidHoursToAdd(INVALID_DURATION_NOT_MULTIPLE_OF_POINT_FIVE));
        assertFalse(EndDateTime.isValidHoursToAdd(INVALID_DURATION_NEGATIVE));

        // valid hours
        assertTrue(EndDateTime.isValidHoursToAdd(VALID_DURATION));
        assertTrue(EndDateTime.isValidHoursToAdd(VALID_DURATION_WITH_POINT_FIVE));
        assertTrue(EndDateTime.isValidHoursToAdd(VALID_DURATION_LOWER_BOUND));
        assertTrue(EndDateTime.isValidHoursToAdd(VALID_DURATION_UPPER_BOUND));
        assertTrue(EndDateTime.isValidHoursToAdd(VALID_DURATION_WITH_DECIMALS));
    }
}
