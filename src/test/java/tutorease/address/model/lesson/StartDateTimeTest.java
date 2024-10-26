package tutorease.address.model.lesson;

import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DAY;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_HOUR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_MINUTE;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_MONTH;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_YEAR;
import static tutorease.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tutorease.address.logic.parser.exceptions.ParseException;

public class StartDateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StartDateTime.createStartDateTime(null));
    }

    @Test
    public void constructor_invalidDateTimeD_throwsParseException() {
        assertThrows(ParseException.class, () -> StartDateTime.createStartDateTime(INVALID_DAY));
    }

    @Test
    public void constructor_invalidDateTimeM_throwsParseException() {
        assertThrows(ParseException.class, () -> StartDateTime.createStartDateTime(INVALID_MONTH));
    }

    @Test
    public void constructor_invalidDateTimeY_throwsParseException() {
        assertThrows(ParseException.class, () -> StartDateTime.createStartDateTime(INVALID_YEAR));
    }

    @Test
    public void constructor_invalidDateTimeH_throwsParseException() {
        assertThrows(ParseException.class, () -> StartDateTime.createStartDateTime(INVALID_HOUR));
    }

    @Test
    public void constructor_invalidDateTimeMM_throwsParseException() {
        assertThrows(ParseException.class, () -> StartDateTime.createStartDateTime(INVALID_MINUTE));
    }

    @Test
    public void constructor_invalidStartDateTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StartDateTime.createStartDateTime(null));
    }
}
