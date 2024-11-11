package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListPoliciesCommand;
import seedu.address.logic.parser.exceptions.ParseException;


public class ListPoliciesCommandParserTest {

    private final ListPoliciesCommandParser parser = new ListPoliciesCommandParser();

    private void assertParseSuccess(String userInput, Index expectedIndex) throws Exception {
        ListPoliciesCommand expectedCommand = new ListPoliciesCommand(expectedIndex);
        ListPoliciesCommand command = parser.parse(userInput);
        assertEquals(expectedCommand, command);
    }

    private void assertParseFailure(String userInput, String expectedMessage) {
        assertThrows(ParseException.class, () -> parser.parse(userInput), expectedMessage);
    }

    @Test
    public void parse_validArgs_returnsListPoliciesCommand() throws Exception {
        assertParseSuccess("1", INDEX_FIRST_CLIENT);
        assertParseSuccess("2", INDEX_SECOND_CLIENT);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPoliciesCommand.MESSAGE_USAGE);

        assertParseFailure("", expectedMessage); // empty input
        assertParseFailure("abc", expectedMessage); // non-numeric input
        assertParseFailure("1.5", expectedMessage); // non-int input
        assertParseFailure("-1", expectedMessage); // negative input
    }
}
