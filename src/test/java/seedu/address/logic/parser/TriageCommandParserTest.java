package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRIAGE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRIAGE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TriageCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Triage;

public class TriageCommandParserTest {

    private final TriageCommandParser parser = new TriageCommandParser();

    @Test
    public void parse_validArgs_returnsTriageCommand() throws Exception {
        String userInput = VALID_NRIC_AMY + " " + PREFIX_TRIAGE + VALID_TRIAGE_AMY;
        TriageCommand expectedCommand = new TriageCommand(new Nric(VALID_NRIC_AMY), new Triage(VALID_TRIAGE_AMY));

        TriageCommand resultCommand = parser.parse(userInput);
        assertEquals(expectedCommand, resultCommand);
    }

    @Test
    public void parse_missingTriage_throwsParseException() {
        String userInput = VALID_NRIC_AMY;
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidTriageFormat_throwsParseException() {
        String userInput = VALID_NRIC_AMY + " " + PREFIX_TRIAGE + "invalid";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingNric_throwsParseException() {
        String userInput = PREFIX_TRIAGE + VALID_TRIAGE_AMY;
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidNricFormat_throwsParseException() {
        String userInput = "S1234A " + PREFIX_TRIAGE + VALID_TRIAGE_AMY; // NRIC is too short
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_extraWhitespace_returnsTriageCommand() throws Exception {
        String userInput = "  " + VALID_NRIC_AMY + "   " + PREFIX_TRIAGE + VALID_TRIAGE_AMY + "  ";
        TriageCommand expectedCommand = new TriageCommand(new Nric(VALID_NRIC_AMY), new Triage(VALID_TRIAGE_AMY));

        TriageCommand resultCommand = parser.parse(userInput.trim());
        assertEquals(expectedCommand, resultCommand);
    }
}
