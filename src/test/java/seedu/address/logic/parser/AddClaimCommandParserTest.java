package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddClaimCommand;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.policy.PolicyType;

public class AddClaimCommandParserTest {

    private AddClaimCommandParser parser = new AddClaimCommandParser();

    @Test
    public void parse_compulsoryFieldPresent_success() {
        String userInput = INDEX_FIRST_CLIENT.getOneBased() + CommandTestUtil.POLICY_TYPE_DESC_HEALTH
                + CommandTestUtil.CLAIM_STATUS_PENDING + CommandTestUtil.CLAIM_DESC;
        Claim expectedClaim = new Claim(ClaimStatus.PENDING, CommandTestUtil.VALID_CLAIM_DESC);
        AddClaimCommand expectedCommand = new AddClaimCommand(INDEX_FIRST_CLIENT, expectedClaim,
                PolicyType.HEALTH);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClaimCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, "", expectedMessage);

        // no index
        assertParseFailure(parser, CommandTestUtil.POLICY_TYPE_DESC_HEALTH
                + CommandTestUtil.CLAIM_STATUS_PENDING + CommandTestUtil.CLAIM_DESC, expectedMessage);

        // negative index
        assertParseFailure(parser, "-1 " + CommandTestUtil.POLICY_TYPE_DESC_HEALTH
                + CommandTestUtil.CLAIM_STATUS_PENDING + CommandTestUtil.CLAIM_DESC, expectedMessage);

        // only index
        assertParseFailure(parser, String.valueOf(INDEX_FIRST_CLIENT.getOneBased()), expectedMessage);

        // no policy type
        assertParseFailure(parser, INDEX_FIRST_CLIENT.getOneBased()
                + CommandTestUtil.CLAIM_STATUS_PENDING + CommandTestUtil.CLAIM_DESC, expectedMessage);
    }

    @Test
    public void parse_invalidClaimDescription_failure() {
        String expectedMessage = Claim.MESSAGE_CONSTRAINTS;

        // Invalid claim description
        String userInput = INDEX_FIRST_CLIENT.getOneBased() + CommandTestUtil.POLICY_TYPE_DESC_HEALTH
                + CommandTestUtil.CLAIM_STATUS_PENDING + CommandTestUtil.INVALID_CLAIM_DESC;
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
