package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONCERT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FindConcertContactCommand;

public class FindConcertContactCommandParserTest {

    private final FindConcertContactCommandParser parser = new FindConcertContactCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser,
                "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindConcertContactCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindConcertContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefixes_throwsParseException() {
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindConcertContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_inputWithPreamble_throwsParseException() {
        FindConcertContactCommand expectedFindConcertContact = new FindConcertContactCommand(
            INDEX_FIRST_PERSON, (Index) null);
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + " " + "preamble " + PREFIX_PERSON + INDEX_FIRST_PERSON.getOneBased(),
                expectedFindConcertContact);
    }

    @Test
    public void parse_validPersonArgs_returnsFindConcertContactCommand() {
        // no leading and trailing whitespaces
        String userInput1 = PREAMBLE_WHITESPACE + " " + PREFIX_PERSON + INDEX_FIRST_PERSON.getOneBased();
        assertDoesNotThrow(() -> parser.parse(userInput1), "Parse exception thrown");

        // multiple whitespaces between keywords
        String userInput2 = new StringBuilder().append(PREAMBLE_WHITESPACE).append(" ")
                .append(PREFIX_PERSON).append(" \n \n")
                .append(INDEX_FIRST_PERSON.getOneBased())
                .append("\t \t").toString();
        assertDoesNotThrow(() -> parser.parse(userInput2), "Parse exception thrown");
    }

    @Test
    public void parse_validConcertArg_returnsFindConcertContactCommand() {
        // no leading and trailing whitespaces
        String userInput1 = PREAMBLE_WHITESPACE + " " + PREFIX_CONCERT + INDEX_FIRST_CONCERT.getOneBased();
        assertDoesNotThrow(() -> parser.parse(userInput1), "Parse exception thrown");

        // leading and trailing whitespaces
        String userInput2 = new StringBuilder().append(PREAMBLE_WHITESPACE).append(" ")
                .append(PREFIX_CONCERT).append("  ")
                .append(INDEX_FIRST_CONCERT.getOneBased())
                .append("\t    \t").toString();;
        assertDoesNotThrow(() -> parser.parse(userInput2), "Parse exception thrown");
    }

    @Test
    public void parse_validPersonAndConcertArgs_returnsFindConcertContactCommand() {
        String userInput = new StringBuilder()
                .append(PREAMBLE_WHITESPACE).append(" ")
                .append(PREFIX_PERSON).append(INDEX_FIRST_PERSON.getOneBased()).append(" ")
                .append(PREFIX_CONCERT).append(INDEX_FIRST_CONCERT.getOneBased()).toString();
        assertDoesNotThrow(() -> parser.parse(userInput), "Parse exception thrown");
    }
}
