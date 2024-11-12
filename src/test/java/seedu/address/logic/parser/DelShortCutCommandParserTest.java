package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FULLTAGNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DelShortCutCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.shortcut.Alias;
import seedu.address.model.shortcut.FullTagName;
import seedu.address.model.shortcut.ShortCut;

public class DelShortCutCommandParserTest {

    private final DelShortCutCommandParser parser = new DelShortCutCommandParser();

    @Test
    void parse_validArgs_returnsDelShortCutCommand() {
        // Valid input with alias and fullTagName
        String userInput = " " + PREFIX_ALIAS + "v " + PREFIX_FULLTAGNAME + "Vegan";

        ShortCut shortCut = new ShortCut(new Alias("v"), new FullTagName("Vegan"));
        DelShortCutCommand expectedCommand = new DelShortCutCommand(shortCut);

        // Expect the parser to return a valid command when input is correct
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    void parse_missingAlias_throwsParseException() {
        // Missing alias, this should cause a failure
        String userInput = " " + PREFIX_FULLTAGNAME + "Vegan";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            DelShortCutCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_missingFullTagName_throwsParseException() {
        // Missing fullTagName, this should cause a failure
        String userInput = " " + PREFIX_ALIAS + "v";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            DelShortCutCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_emptyArgs_throwsParseException() {
        // No arguments provided, this should cause a failure
        String userInput = " ";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            DelShortCutCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_duplicatePrefixes_throwsParseException() {
        // Duplicate prefixes
        String userInput = " " + PREFIX_ALIAS + "v " + PREFIX_ALIAS + "v2 " + PREFIX_FULLTAGNAME + "Vegan";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
