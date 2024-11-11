package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class RandomCommandParserTest {

    private final RandomCommandParser parser = new RandomCommandParser();

    @Test
    public void parse_returnsRandomCommand() {
        try {
            Command result = parser.parse("random");
            assertNotNull(result);
        } catch (ParseException e) {
            fail("ParseException should not have been thrown: " + e.getMessage());
        }
    }

    @Test
    public void parse_upperCase_returnsRandomCommand() {
        try {
            Command result = parser.parse("RANDOM");
            assertNotNull(result);
        } catch (ParseException e) {
            fail("ParseException should not have been thrown: " + e.getMessage());
        }
    }

    @Test
    public void parse_mixCase_returnsRandomCommand() {
        try {
            Command result = parser.parse("RaNDOm");
            assertNotNull(result);
        } catch (ParseException e) {
            fail("ParseException should not have been thrown: " + e.getMessage());
        }
    }

    @Test
    public void parse_trailingSpace_returnsRandomCommand() {
        try {
            Command result = parser.parse("    random      ");
            assertNotNull(result);
        } catch (ParseException e) {
            fail("ParseException should not have been thrown: " + e.getMessage());
        }
    }

    @Test
    public void parse_extraParameters_returnsRandomCommand() {
        try {
            Command result = parser.parse("random person");
            assertNotNull(result);
        } catch (ParseException e) {
            fail("ParseException should not have been thrown: " + e.getMessage());
        }
    }
}
