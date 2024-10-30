package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

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
        CreateTutorialCommand command = parser.parse(args);

        // Check that the parser returns a CreateTutorialCommand with the correct parameters
        assertEquals(new CreateTutorialCommand(new Tutorial("Math")), command);
    }

    @Test
    public void parse_missingTutorialPrefix_throwsParseException() {
        String args = "Math";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Verify that the exception message contains the expected usage message
        assertTrue(exception.getMessage().contains(CreateTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        String args = ""; // Empty input string
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Verify exception message matches expected usage information
        assertTrue(exception.getMessage().contains(CreateTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleTutorialPrefixes_throwsParseException() {
        String args = " tut/Math tut/Science"; // Duplicate tutorial prefix
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Check exception message for usage details
        assertEquals(exception.getMessage(), Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TUTORIAL));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        String args = "tut/";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Ensure exception message contains expected usage format
        assertTrue(exception.getMessage().contains(CreateTutorialCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraWhitespace_returnsCreateTutorialCommand() throws Exception {
        String args = "   tut/History   "; // Valid input with extra whitespace
        CreateTutorialCommand command = parser.parse(args);

        // Check that the parser returns a CreateTutorialCommand with the correct parameters
        assertEquals(new CreateTutorialCommand(new Tutorial("History")), command);
    }

    @Test
    public void parse_invalidCharactersInTutorialName_throwsParseException() {
        String args = " tut/Math123!";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Check if the exception message matches expected output
        assertTrue(exception.getMessage().contains(Tutorial.MESSAGE_CONSTRAINTS));
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

