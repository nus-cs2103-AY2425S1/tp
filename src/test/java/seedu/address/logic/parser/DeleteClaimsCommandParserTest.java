package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteClaimsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.policy.PolicyType;

public class DeleteClaimsCommandParserTest {

    private final DeleteClaimsCommandParser parser = new DeleteClaimsCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteClaimsCommand() throws Exception {
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_STATUS + "PENDING "
                + PREFIX_CLAIM_DESC + "Surgery";

        DeleteClaimsCommand expectedCommand = new DeleteClaimsCommand(
                INDEX_FIRST_PERSON, PolicyType.HEALTH, ClaimStatus.PENDING, "Surgery");

        DeleteClaimsCommand actualCommand = parser.parse(userInput);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "a " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_STATUS + "PENDING "
                + PREFIX_CLAIM_DESC + "Surgery";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPolicyType_throwsParseException() {
        String userInput = "1 " + PREFIX_CLAIM_STATUS + "PENDING " + PREFIX_CLAIM_DESC + "Surgery";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingClaimStatus_throwsParseException() {
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_DESC + "Surgery";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingClaimDescription_throwsParseException() {
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_STATUS + "PENDING";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allPrefixesMissing_throwsParseException() {
        String userInput = "1 Surgery";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPolicyType_throwsParseException() {
        String userInput = "1 " + PREFIX_POLICY_TYPE + "unknown " + PREFIX_CLAIM_STATUS + "PENDING "
                + PREFIX_CLAIM_DESC + "Surgery";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidClaimStatus_throwsParseException() {
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_STATUS + "UNKNOWN "
                + PREFIX_CLAIM_DESC + "Surgery";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_STATUS + "PENDING "
                + PREFIX_CLAIM_STATUS + "APPROVED " + PREFIX_CLAIM_DESC + "Surgery";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE));
    }

    //    @Test
    //    public void parse_extraArguments_throwsParseException() {
    //        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_STATUS + "PENDING "
    //                + PREFIX_CLAIM_DESC + "Surgery extraArgument";
    //
    //        assertThrows(ParseException.class, () -> parser.parse(userInput),
    //                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimsCommand.MESSAGE_USAGE));
    //    }

    @Test
    public void parse_validArgsWithWhitespace_returnsDeleteClaimsCommand() throws Exception {
        String userInput = " 1 " + PREFIX_POLICY_TYPE + " health " + PREFIX_CLAIM_STATUS + " PENDING "
                + PREFIX_CLAIM_DESC + " Surgery ";

        DeleteClaimsCommand expectedCommand = new DeleteClaimsCommand(
                INDEX_FIRST_PERSON, PolicyType.HEALTH, ClaimStatus.PENDING, "Surgery");

        DeleteClaimsCommand actualCommand = parser.parse(userInput);
        assertEquals(expectedCommand, actualCommand);
    }
}
