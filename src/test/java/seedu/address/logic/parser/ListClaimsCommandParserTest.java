package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListClaimsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyType;

public class ListClaimsCommandParserTest {

    private final ListClaimsCommandParser parser = new ListClaimsCommandParser();

    private void assertParseSuccess(String userInput, ListClaimsCommand expectedCommand) throws Exception {
        ListClaimsCommand actualCommand = parser.parse(userInput);
        assertEquals(expectedCommand, actualCommand);
    }

    private void assertParseFailure(String userInput, String expectedMessage) {
        assertThrows(ParseException.class, () -> parser.parse(userInput), expectedMessage);
    }

    @Test
    public void parse_validArgs_returnsListClaimsCommand() throws Exception {
        String userInput = INDEX_FIRST_CLIENT.getOneBased() + " " + PREFIX_POLICY_TYPE + "health";
        ListClaimsCommand expectedCommand = new ListClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.HEALTH);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_missingPolicyType_throwsParseException() {
        String userInput = INDEX_FIRST_CLIENT.getOneBased() + " ";
        assertParseFailure(userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "a " + PREFIX_POLICY_TYPE + "health";
        assertParseFailure(userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPolicyType_throwsParseException() {
        String userInput = INDEX_FIRST_CLIENT.getOneBased() + " " + PREFIX_POLICY_TYPE + "invalidPolicy";
        assertParseFailure(userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArgumentsPresent_throwsParseException() {
        String userInput = INDEX_FIRST_CLIENT.getOneBased() + " " + PREFIX_POLICY_TYPE + "health extraArg";
        assertParseFailure(userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        String userInput = "";
        assertParseFailure(userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListClaimsCommand.MESSAGE_USAGE));
    }
}
