package seedu.address.logic.parser;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.FilterCommand.MESSAGE_INCOMPLETE_COMMAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_validArgs_returnsFilterCommand_multipleValuesOnePrefix() {
        // Test case with "n/Alice n/Bob" (multiple prefixes)
        PersonPredicate expectedPredicate = new PersonPredicate(
                List.of("Alice", "Bob"), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        assertParseSuccess(parser, "filter n/Alice n/Bob", expectedCommand);
        assertParseSuccess(parser, "filter n/Alice Bob", expectedCommand);

        //multiple whitespaces between keywords
        assertParseSuccess(parser, "filter \n n/Alice \n \t n/Bob  \t", expectedCommand);
    }

    @Test
    void parse_validArgs_returnsFilterCommand_multiplePrefixes() throws ParseException {

        PersonPredicate expectedPredicate = new PersonPredicate(
                List.of("Alice"), List.of("99999999"), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        assertParseSuccess(parser, "filter n/Alice p/99999999", expectedCommand);

        //multiple whitespaces between keywords
        assertParseSuccess(parser, "filter \n n/Alice \n \t p/99999999  \t", expectedCommand);
    }

    @Test
    void parse_invalidArgs_returnsFilterCommand_multiplePrefixes() throws ParseException {

        PersonPredicate expectedPredicate = new PersonPredicate(
                List.of("Alice"), List.of("98898989"), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

        // Parse the command
        FilterCommand actualCommand = parser.parse("filter n/Alice p/98898989");

        assertEquals(expectedCommand, actualCommand);
    }

   @Test
    void parse_validArgs_returnsFilterCommand_multiplePredicates() throws ParseException {

        PersonPredicate expectedPredicate = new PersonPredicate(
                List.of("Alice", "Bernice"), List.of("99999999", "99272758"), emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList());
        FilterCommand expectedCommand = new FilterCommand(expectedPredicate);

       assertParseSuccess(parser, "filter n/Alice Bernice p/99999999 99272758", expectedCommand);
       assertParseSuccess(parser, "filter n/Alice n/Bernice p/99999999 p/99272758", expectedCommand);
   }

    @Test
    void parse_invalidArgs_OneEmptyPrefix_FilterCommand_() {
        assertParseFailure(parser, "filter n/", MESSAGE_INCOMPLETE_COMMAND);
        assertParseFailure(parser, "filter p/", MESSAGE_INCOMPLETE_COMMAND);
        assertParseFailure(parser, "filter e/", MESSAGE_INCOMPLETE_COMMAND);
        assertParseFailure(parser, "filter a/", MESSAGE_INCOMPLETE_COMMAND);
        assertParseFailure(parser, "filter r/", MESSAGE_INCOMPLETE_COMMAND);
        assertParseFailure(parser, "filter s/", MESSAGE_INCOMPLETE_COMMAND);
        assertParseFailure(parser, "filter c/", MESSAGE_INCOMPLETE_COMMAND);
        assertParseFailure(parser, "filter en/", MESSAGE_INCOMPLETE_COMMAND);
        assertParseFailure(parser, "filter ep/", MESSAGE_INCOMPLETE_COMMAND);
        assertParseFailure(parser, "filter t/", MESSAGE_INCOMPLETE_COMMAND);
    }

    @Test
    void parse_invalidArgs_AllEmptyPredicates_FilterCommand_() {
        String exception_str = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "filter", exception_str);
        assertParseFailure(parser, "filter  ", exception_str);
        assertParseFailure(parser, "filter n", exception_str);
    }
}