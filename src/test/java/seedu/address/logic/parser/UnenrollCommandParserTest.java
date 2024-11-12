package seedu.address.logic.parser;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnenrollCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UnenrollCommandParserTest {

    private UnenrollCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new UnenrollCommandParser();
    }

    @Test
    public void parse_validArgs_returnsUnEnrollCommand() throws Exception {
        String args = "1 tut/physics";
        UnenrollCommand command = parser.parse(args);

        assertEquals(new UnenrollCommand(Index.fromOneBased(1), "physics"), command);
    }

    @Test
    public void parse_validArgsExtraSpace_returnsUnEnrollCommand() throws Exception {
        String args = " 1  tut/  physics ";
        UnenrollCommand command = parser.parse(args);

        assertEquals(new UnenrollCommand(Index.fromOneBased(1), "physics"), command);
    }

    @Test
    public void parse_missingTutorialPrefix_throwsParseException() {
        String args = "1 physics"; // Missing prefix "t/"
        assertThrows(ParseException.class, () -> parser.parse(args), UnenrollCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_wrongTutorialPrefix_throwsParseException() {
        String args = String.format("1 %sphysics", "fghudfhbd" + PREFIX_TUTORIAL); // Missing prefix "t/"
        assertThrows(ParseException.class, () -> parser.parse(args), UnenrollCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_duplicateTutorialPrefix_throwsParseException() {
        String args = "1 t/physics t/math";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Check error message format
        assertTrue(exception.getMessage().contains(UnenrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        String args = "t/physics"; // No index provided
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        assertTrue(exception.getMessage().contains(UnenrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String args = "a t/physics"; // Non-integer index
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        // Check error message format
        assertTrue(exception.getMessage().contains(UnenrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        String args = ""; // Empty input
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));
        assertTrue(exception.getMessage().contains(UnenrollCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_emptyTutorialValue_throwsParseException() {
        String args = "1 t/"; // Empty tutorial value
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(args));

        assertTrue(exception.getMessage().contains(UnenrollCommand.MESSAGE_USAGE));
    }

    @Test
    public void arePrefixesPresent_allPresent_returnsTrue() {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize("1 tut/physics", PREFIX_TUTORIAL);
        assertTrue(UnenrollCommandParser.arePrefixesPresent(argumentMultimap, PREFIX_TUTORIAL));
    }

    @Test
    public void arePrefixesPresent_someMissing_returnsFalse() {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize("", PREFIX_TUTORIAL);
        assertFalse(UnenrollCommandParser.arePrefixesPresent(argumentMultimap, PREFIX_TUTORIAL));
    }
}

