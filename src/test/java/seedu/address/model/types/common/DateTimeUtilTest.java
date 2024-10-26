package seedu.address.model.types.common;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Note: testing of timeline is not possible without adding dependencies
 * Also, the DateTimeUtil class mainly acts as a facade for Timeline and LocalDateTime,
 * both of which are part of Java and not editable
 */
public class DateTimeUtilTest {

    @Test
    public void getCurrentDateTimeString() {
        String output = DateTimeUtil.getCurrentDateTimeString();
        assertTrue(output.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$"));
    }
}
