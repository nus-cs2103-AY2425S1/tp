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

    private final AddClaimCommandParser parser = new AddClaimCommandParser();

    private String buildUserInput(String index, String policyTypeDesc, String claimStatus, String claimDesc) {
        return index + policyTypeDesc + claimStatus + claimDesc;
    }

    private void assertCompulsoryFieldFailure(String userInput) {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClaimCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldPresent_success() {
        String userInput = buildUserInput(
                String.valueOf(INDEX_FIRST_CLIENT.getOneBased()),
                CommandTestUtil.POLICY_TYPE_DESC_HEALTH,
                CommandTestUtil.CLAIM_STATUS_PENDING,
                CommandTestUtil.CLAIM_DESC
        );
        Claim expectedClaim = new Claim(ClaimStatus.PENDING, CommandTestUtil.VALID_CLAIM_DESC);
        AddClaimCommand expectedCommand = new AddClaimCommand(INDEX_FIRST_CLIENT, expectedClaim, PolicyType.HEALTH);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        // no parameters
        assertCompulsoryFieldFailure("");

        // no index
        assertCompulsoryFieldFailure(buildUserInput(
                "", CommandTestUtil.POLICY_TYPE_DESC_HEALTH,
                CommandTestUtil.CLAIM_STATUS_PENDING, CommandTestUtil.CLAIM_DESC
        ));

        // negative index
        assertCompulsoryFieldFailure(buildUserInput(
                "-1", CommandTestUtil.POLICY_TYPE_DESC_HEALTH,
                CommandTestUtil.CLAIM_STATUS_PENDING, CommandTestUtil.CLAIM_DESC
        ));

        // only index
        assertCompulsoryFieldFailure(String.valueOf(INDEX_FIRST_CLIENT.getOneBased()));

        // no policy type
        assertCompulsoryFieldFailure(buildUserInput(
                String.valueOf(INDEX_FIRST_CLIENT.getOneBased()), "",
                CommandTestUtil.CLAIM_STATUS_PENDING, CommandTestUtil.CLAIM_DESC
        ));

        // missing claim description
        assertCompulsoryFieldFailure(buildUserInput(
                String.valueOf(INDEX_FIRST_CLIENT.getOneBased()), CommandTestUtil.POLICY_TYPE_DESC_HEALTH,
                CommandTestUtil.CLAIM_STATUS_PENDING, ""
        ));

        // missing claim status
        assertCompulsoryFieldFailure(buildUserInput(
                String.valueOf(INDEX_FIRST_CLIENT.getOneBased()), CommandTestUtil.POLICY_TYPE_DESC_HEALTH,
                "", CommandTestUtil.CLAIM_DESC
        ));
    }

    @Test
    public void parse_invalidClaimDescription_failure() {
        String expectedMessage = Claim.MESSAGE_CONSTRAINTS;

        String userInput = buildUserInput(
                String.valueOf(INDEX_FIRST_CLIENT.getOneBased()),
                CommandTestUtil.POLICY_TYPE_DESC_HEALTH,
                CommandTestUtil.CLAIM_STATUS_PENDING,
                CommandTestUtil.INVALID_CLAIM_DESC
        );
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
