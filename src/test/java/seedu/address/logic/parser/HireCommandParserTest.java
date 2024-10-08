package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HireCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;

public class HireCommandParserTest {

    private HireCommandParser parser = new HireCommandParser();

    @Test
    public void parse_validArgs_returnsHireCommand() throws Exception {
        HireCommand expectedCommand = new HireCommand(new Name("Amy Bee"), new Job("Software Engineer"));
        HireCommand command = parser.parse(" n/Amy Bee j/Software Engineer");
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("Amy Bee"));
    }

    @Test
    public void parse_missingName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" j/Software Engineer"));
    }

    @Test
    public void parse_missingJob_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" n/Amy Bee "));
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("invalidPreamble n/Amy Bee j/Software Engineer"));
    }

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("Amy Bee Software Engineer"));
    }
}
