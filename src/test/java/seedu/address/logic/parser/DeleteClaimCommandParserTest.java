package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLAIM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteClaimCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyType;

public class DeleteClaimCommandParserTest {

    private final DeleteClaimCommandParser parser = new DeleteClaimCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteClaimsCommand() throws Exception {
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_INDEX + "1";

        DeleteClaimCommand expectedCommand = new DeleteClaimCommand(
                INDEX_FIRST_CLIENT, PolicyType.HEALTH, INDEX_FIRST_CLAIM);

        DeleteClaimCommand actualCommand = parser.parse(userInput);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "a " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_INDEX + "1";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPolicyType_throwsParseException() {
        String userInput = "1 " + PREFIX_CLAIM_INDEX + "1";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingClaimIndex_throwsParseException() {
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allPrefixesMissing_throwsParseException() {
        String userInput = "1 health 1";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPolicyType_throwsParseException() {
        String userInput = "1 " + PREFIX_POLICY_TYPE + "unknown " + PREFIX_CLAIM_INDEX + "1";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_INDEX + "1 "
                + PREFIX_CLAIM_INDEX + "2";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArguments_throwsParseException() {
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_INDEX + "1 extraArgument";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsWithWhitespace_returnsDeleteClaimsCommand() throws Exception {
        String userInput = " 1 " + PREFIX_POLICY_TYPE + " health " + PREFIX_CLAIM_INDEX + " 1 ";

        DeleteClaimCommand expectedCommand = new DeleteClaimCommand(
                INDEX_FIRST_CLIENT, PolicyType.HEALTH, INDEX_FIRST_CLAIM);

        DeleteClaimCommand actualCommand = parser.parse(userInput);
        assertEquals(expectedCommand, actualCommand);
    }
}
