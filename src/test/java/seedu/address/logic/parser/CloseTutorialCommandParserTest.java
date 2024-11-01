package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

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
        assertParseSuccess(parser, args, new CloseTutorialCommand(new Tutorial("Math")));
    }

    @Test
    public void parse_missingTutorialPrefix_throwsParseException() {
        String args = "Math";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Verify that the exception message contains the expected usage message
        assertParseFailure(parser, args, exception.getMessage());
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        String args = ""; // Empty input string
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));
        assertParseFailure(parser, args, exception.getMessage());
        // Verify exception message matches expected usage information
    }

    @Test
    public void parse_invalidTutorialValue_throwsParseException() {
        String args = " tut/"; // Empty tutorial value
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Verify exception message includes usage details
        assertParseFailure(parser, args, String.format(Tutorial.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_duplicateTutorialPrefix_throwsParseException() {
        String args = " tut/Math tut/Physics"; // Duplicate tutorial prefix
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));
        assertParseFailure(parser, args, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL));
    }

    @Test
    public void parse_validInputWithTrailingSpaces_returnsCloseTutorialCommand() {
        String args = "    tut/Math    "; // Valid input with leading/trailing spaces
        assertParseSuccess(parser, args, new CloseTutorialCommand(new Tutorial("Math")));
    }
}

