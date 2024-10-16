package tutorease.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_DAY;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_HOUR;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_MINUTE;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_MONTH;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_YEAR;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_START_DATE;
import static tutorease.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StartDateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StartDateTime.createStartDateTime(null));
    }

    @Test
    public void constructor_invalidDateTimeD_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> StartDateTime.createStartDateTime(INVALID_DAY));
    }

    @Test
    public void constructor_invalidDateTimeM_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> StartDateTime.createStartDateTime(INVALID_MONTH));
    }

    @Test
    public void constructor_invalidDateTimeY_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> StartDateTime.createStartDateTime(INVALID_YEAR));
    }

    @Test
    public void constructor_invalidDateTimeH_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> StartDateTime.createStartDateTime(INVALID_HOUR));
    }

    @Test
    public void constructor_invalidDateTimeMM_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> StartDateTime.createStartDateTime(INVALID_MINUTE));
    }

    @Test
    public void constructor_invalidStartDateTime_throwsIllegalArgumentException() {
        assertThrows(NullPointerException.class, () -> StartDateTime.createStartDateTime(null));
    }

    @Test
    public void isValidStartDateTime() {
        assertFalse(StartDateTime.isValidDateTime(null));
        assertFalse(StartDateTime.isValidDateTime(INVALID_DAY));
        assertFalse(StartDateTime.isValidDateTime(INVALID_MONTH));
        assertFalse(StartDateTime.isValidDateTime(INVALID_YEAR));
        assertFalse(StartDateTime.isValidDateTime(INVALID_HOUR));
        assertFalse(StartDateTime.isValidDateTime(INVALID_MINUTE));
        assertTrue(StartDateTime.isValidDateTime(VALID_START_DATE));
    }
}
