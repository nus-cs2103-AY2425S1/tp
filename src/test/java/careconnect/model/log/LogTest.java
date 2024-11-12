package careconnect.model.log;

import static careconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import careconnect.logic.commands.CommandTestUtil;

public class LogTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Log(null));
    }

    @Test
    public void constructor_invalidLogRemark_throwsIllegalArgumentException() {
        String invalidLogRemark = "";
        assertThrows(IllegalArgumentException.class, () -> new Log(invalidLogRemark));
    }

    @Test
    public void isValidTagName() {
        assertTrue(Log.isValidLogRemark("test"));
    }

    @Test
    public void equals() {
        Log log1 = new Log(CommandTestUtil.DATE, "test1");
        Log log2 = new Log(CommandTestUtil.DATE2, "test2");
        Log log3 = new Log(CommandTestUtil.DATE2, "test2");
        Log log4 = new Log(CommandTestUtil.DATE2, "test1");

        // same object -> returns true
        assertTrue(log1.equals(log1));

        // same values -> returns true
        assertTrue(log2.equals(log3));

        // different dates -> returns false
        assertFalse(log1.equals(log4));

        // different remarks -> returns false
        assertFalse(log2.equals(log4));
    }
}
