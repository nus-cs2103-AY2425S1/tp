package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FULLTAGNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddShortCutCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.shortcut.Alias;
import seedu.address.model.shortcut.FullTagName;
import seedu.address.model.shortcut.ShortCut;

class AddShortCutCommandParserTest {

    private AddShortCutCommandParser parser = new AddShortCutCommandParser();

    @Test
    void parse_validArgs_returnsAddShortCutCommand() throws Exception {
        // Valid input with alias and fullTagName
        String userInput = " " + PREFIX_ALIAS + "v " + PREFIX_FULLTAGNAME + "Vegan";

        AddShortCutCommand expectedCommand = new AddShortCutCommand(
                new ShortCut(new Alias("v"), new FullTagName("Vegan"))
        );

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    void parse_missingAlias_throwsParseException() {
        // Missing alias
        String userInput = " " + PREFIX_FULLTAGNAME + "Vegan";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AddShortCutCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_missingFullTagName_throwsParseException() {
        // Missing fullTagName
        String userInput = " " + PREFIX_ALIAS + "v";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AddShortCutCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_emptyArgs_throwsParseException() {
        // No arguments provided
        String userInput = " ";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AddShortCutCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_invalidAlias_throwsParseException() {
        // Invalid alias (assuming "!" is invalid for Alias)
        String userInput = " " + PREFIX_ALIAS + "! " + PREFIX_FULLTAGNAME + "Vegan";
        assertParseFailure(parser, userInput, Alias.MESSAGE_CONSTRAINTS);
    }

    @Test
    void parse_invalidFullTagName_throwsParseException() {
        // Invalid fullTagName (assuming "123" is invalid for FullTagName)
        String userInput = " " + PREFIX_ALIAS + "v " + PREFIX_FULLTAGNAME + "!123";
        assertParseFailure(parser, userInput, FullTagName.MESSAGE_CONSTRAINTS);
    }

    @Test
    void parse_duplicatePrefixes_throwsParseException() {
        // Duplicate prefixes
        String userInput = " " + PREFIX_ALIAS + "v " + PREFIX_ALIAS + "v2 " + PREFIX_FULLTAGNAME + "Vegan";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

}
