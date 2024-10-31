package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EnrollCommand;
import seedu.address.logic.parser.exceptions.ParseException;
public class EnrollCommandParserTest {

    private EnrollCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new EnrollCommandParser();
    }

    @Test
    public void parse_validArgs_returnsEnrollCommand() throws Exception {
        String args = "1 tut/physics";
        EnrollCommand command = parser.parse(args);

        // Check that the parser returns an EnrollCommand with the correct parameters
        assertEquals(new EnrollCommand(Index.fromOneBased(1), "physics"), command);
    }

    @Test
    public void parse_validArgsWrongTutorial_returnsEnrollCommand() throws Exception {
        String args = "1 tut/ahahahahhdgbfdh";
        EnrollCommand command = parser.parse(args);

        // Check that the parser returns an EnrollCommand with the correct parameters
        assertEquals(new EnrollCommand(Index.fromOneBased(1), "physics"), command);
    }

    @Test
    public void parse_validArgsWrongIndex_returnsEnrollCommand() throws Exception {
        String index = "100000";
        String args = index + " tut/physics";
        EnrollCommand command = parser.parse(args);

        // Check that the parser returns an EnrollCommand with the correct parameters
        assertEquals(new EnrollCommand(Index.fromOneBased(Integer.parseInt(index)), "physics"), command);
    }

    @Test
    public void parse_validArgsExtraSpace_returnsEnrollCommand() throws Exception {
        String args = " 1    tut/ physics  ";
        EnrollCommand command = parser.parse(args);

        // Check that the parser returns an EnrollCommand with the correct parameters
        assertEquals(new EnrollCommand(Index.fromOneBased(1), "physics"), command);
    }

    @Test
    public void parse_invalidInverseArgs_returnsEnrollCommand() throws Exception {
        String args = "tut/physics 1";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Verify that the exception message contains the expected usage message
        assertTrue(exception.getMessage().contains(EnrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTutorialPrefix_throwsParseException() {
        String args = "1 physics"; // Missing tutorial prefix "t/"
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Verify that the exception message contains the expected usage message
        assertTrue(exception.getMessage().contains(EnrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongTutorialPrefix_throwsParseException() {
        String args = String.format("1 %s/physics", "ffgfg" + PREFIX_TUTORIAL);
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Verify that the exception message contains the expected usage message
        assertTrue(exception.getMessage().contains(EnrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateTutorialPrefix_throwsParseException() {
        String args = "1 t/physics t/math"; // Duplicate tutorial prefix
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Check exception message for usage details
        assertTrue(exception.getMessage().contains(EnrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTutorialValue_throwsParseException() {
        String args = "1 t/"; // Empty tutorial value
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Verify exception message includes usage details
        assertTrue(exception.getMessage().contains(EnrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String args = "a t/physics"; // Non-integer index
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Check if the exception message matches expected output
        assertTrue(exception.getMessage().contains(EnrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        String args = "t/physics"; // No index provided
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Ensure exception message contains expected usage format
        assertTrue(exception.getMessage().contains(EnrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        String args = ""; // Empty input string
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Verify exception message matches expected usage information
        assertTrue(exception.getMessage().contains(EnrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void arePrefixesPresent_allPresent_returnsTrue() {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize("1 tut/physics", PREFIX_TUTORIAL);

        // Check that the prefix is correctly identified as present
        assertTrue(EnrollCommandParser.arePrefixesPresent(argumentMultimap, PREFIX_TUTORIAL));
    }

    @Test
    public void arePrefixesPresent_someMissing_returnsFalse() {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize("", PREFIX_TUTORIAL);

        // Verify that method returns false when required prefix is missing
        assertFalse(EnrollCommandParser.arePrefixesPresent(argumentMultimap, PREFIX_TUTORIAL));
    }
}

