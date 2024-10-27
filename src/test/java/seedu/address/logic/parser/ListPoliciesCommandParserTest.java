package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListPoliciesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListPoliciesCommandParserTest {

    private final ListPoliciesCommandParser parser = new ListPoliciesCommandParser();

    @Test
    public void parse_validArgs_returnsListPoliciesCommand() throws Exception {
        ListPoliciesCommand expectedCommand = new ListPoliciesCommand(INDEX_FIRST_PERSON);
        ListPoliciesCommand command = parser.parse("1");
        assertEquals(expectedCommand, command);

        expectedCommand = new ListPoliciesCommand(INDEX_SECOND_PERSON);
        command = parser.parse("2");
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty input
        assertThrows(ParseException.class, () -> parser.parse(""),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPoliciesCommand.MESSAGE_USAGE));

        // non-numeric input
        assertThrows(ParseException.class, () -> parser.parse("abc"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPoliciesCommand.MESSAGE_USAGE));

        // non-int input
        assertThrows(ParseException.class, () -> parser.parse("1.5"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPoliciesCommand.MESSAGE_USAGE));

        // negative input
        assertThrows(ParseException.class, () -> parser.parse("-1"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPoliciesCommand.MESSAGE_USAGE));
    }

}
