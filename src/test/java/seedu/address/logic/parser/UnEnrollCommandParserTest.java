package seedu.address.logic.parser;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnEnrollCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.logging.Logger;

public class UnEnrollCommandParserTest {

    private UnEnrollCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new UnEnrollCommandParser();
    }

    @Test
    public void parse_validArgs_returnsUnEnrollCommand() throws Exception {
        String args = "1 tut/physics";
        UnEnrollCommand command = parser.parse(args);

        assertEquals(new UnEnrollCommand(Index.fromOneBased(1), "physics"), command);
    }

    @Test
    public void parse_missingTutorialPrefix_throwsParseException() {
        String args = "1 physics"; // Missing prefix "t/"
        assertThrows(ParseException.class, () -> parser.parse(args), UnEnrollCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String args = "a t/physics"; // Non-integer index
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Check error message format
        assertTrue(exception.getMessage().contains(UnEnrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        String args = ""; // Empty input
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));
        assertTrue(exception.getMessage().contains(UnEnrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateTutorialPrefix_throwsParseException() {
        String args = "1 t/physics t/math";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Check error message format
        assertTrue(exception.getMessage().contains(UnEnrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        String args = "t/physics"; // No index provided
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        assertTrue(exception.getMessage().contains(UnEnrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTutorialValue_throwsParseException() {
        String args = "1 t/"; // Empty tutorial value
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        assertTrue(exception.getMessage().contains(UnEnrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void arePrefixesPresent_allPresent_returnsTrue() {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize("1 tut/physics", PREFIX_TUTORIAL);
        assertTrue(UnEnrollCommandParser.arePrefixesPresent(argumentMultimap, PREFIX_TUTORIAL));
    }

    @Test
    public void arePrefixesPresent_someMissing_returnsFalse() {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize("", PREFIX_TUTORIAL);
        assertFalse(UnEnrollCommandParser.arePrefixesPresent(argumentMultimap, PREFIX_TUTORIAL));
    }
}

