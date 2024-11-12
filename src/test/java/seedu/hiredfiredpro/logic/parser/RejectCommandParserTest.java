package seedu.hiredfiredpro.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.hiredfiredpro.logic.commands.RejectCommand;
import seedu.hiredfiredpro.logic.parser.exceptions.ParseException;
import seedu.hiredfiredpro.model.person.Job;
import seedu.hiredfiredpro.model.person.Name;

public class RejectCommandParserTest {

    private RejectCommandParser parser = new RejectCommandParser();

    @Test
    public void parse_validArgs_returnsRejectCommand() throws Exception {
        RejectCommand expectedCommand = new RejectCommand(new Name("Amy Bee"), new Job("Software Engineer"));
        RejectCommand command = parser.parse(" n/Amy Bee j/Software Engineer");
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
