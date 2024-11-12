package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CreateTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutorial.Tutorial;

public class CreateTutorialCommandParserTest {

    private CreateTutorialCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new CreateTutorialCommandParser();
    }

    @Test
    public void parse_validInput_returnsCreateTutorialCommand() throws Exception {
        String args = " tut/Math";
        // Check that the parser returns a CreateTutorialCommand with the correct parameters
        assertParseSuccess(parser, args, new CreateTutorialCommand(new Tutorial("Math")));
    }

    @Test
    public void parse_missingTutorialPrefix_throwsParseException() {
        String args = "Math";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));
        assertParseFailure(parser, args, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                CreateTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        String args = ""; // Empty input string
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Verify exception message matches expected usage information
        assertParseFailure(parser, args, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                CreateTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleTutorialPrefixes_throwsParseException() {
        String args = " tut/Math tut/Science"; // Duplicate tutorial prefix
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        assertParseFailure(parser, args, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        String args = "tut/";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        assertParseFailure(parser, args, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                CreateTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraWhitespace_returnsCreateTutorialCommand() throws Exception {
        String args = "   tut/History   "; // Valid input with extra whitespace
        CreateTutorialCommand command = parser.parse(args);
        // Check that the parser returns a CreateTutorialCommand with the correct parameters
        assertParseSuccess(parser, args, new CreateTutorialCommand(new Tutorial("History")));
    }

    @Test
    public void parse_invalidCharactersInTutorialName_throwsParseException() {
        String args = " tut/Math123!";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        assertParseFailure(parser, args, Tutorial.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void arePrefixesPresent_allPresent_returnsTrue() {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(" tut/Physics", PREFIX_TUTORIAL);

        // Check that the prefix is correctly identified as present
        assertEquals(argumentMultimap.getValue(PREFIX_TUTORIAL).get(), "Physics");
    }

    @Test
    public void arePrefixesPresent_someMissing_returnsFalse() {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize("", PREFIX_TUTORIAL);

        // Verify that method returns false when required prefix is missing
        assertThrows(NoSuchElementException.class, () -> argumentMultimap.getValue(PREFIX_TUTORIAL).get());
    }
}

