package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CloseTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutorial.Tutorial;

public class CloseTutorialCommandParserTest {

    private CloseTutorialCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new CloseTutorialCommandParser();
    }

    @Test
    public void parse_validInput_returnsCloseTutorialCommand() throws Exception {
        String args = " tut/Math";
        CloseTutorialCommand command = parser.parse(args);

        // Check that the parser returns a CloseTutorialCommand with the correct parameters
        assertEquals(new CloseTutorialCommand(new Tutorial("Math")), command);
    }

    @Test
    public void parse_missingTutorialPrefix_throwsParseException() {
        String args = "Math";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Verify that the exception message contains the expected usage message
        assertTrue(exception.getMessage().contains(CloseTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        String args = ""; // Empty input string
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Verify exception message matches expected usage information
        assertTrue(exception.getMessage().contains(CloseTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTutorialValue_throwsParseException() {
        String args = " tut/"; // Empty tutorial value
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Verify exception message includes usage details
        assertEquals(exception.getMessage(), Tutorial.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateTutorialPrefix_throwsParseException() {
        String args = " tut/Math tut/Physics"; // Duplicate tutorial prefix
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Check exception message for usage details
        assertEquals(exception.getMessage(), Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL));
    }

    @Test
    public void parse_validInputWithTrailingSpaces_returnsCloseTutorialCommand() throws Exception {
        String args = "    tut/Math    "; // Valid input with leading/trailing spaces
        CloseTutorialCommand command = parser.parse(args);

        // Check that the parser returns a CloseTutorialCommand with the correct parameters
        assertEquals(new CloseTutorialCommand(new Tutorial("Math")), command);
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        String args = " tut/Maths tut/Physics"; // Invalid format with multiple prefixes
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Check exception message for usage details
        assertEquals(exception.getMessage(), Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL));
    }
}

