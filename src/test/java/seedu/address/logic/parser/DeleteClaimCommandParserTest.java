package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteClaimCommand;
import seedu.address.model.client.insurance.claim.Claim;

/**
 * Contains unit tests for {@link DeleteClaimCommandParser}.
 */
public class DeleteClaimCommandParserTest {

    private static final String VALID_CLAIM_ID = "A1001";
    private static final int VALID_INSURANCE_ID = 0;
    private final DeleteClaimCommandParser parser = new DeleteClaimCommandParser();

    /**
     * Tests if {@code DeleteClaimCommandParser} correctly parses valid user input
     * Returns a {@code DeleteClaimCommand}.
     */
    @Test
    public void parse_validArgs_returnsDeleteClaimCommand() {
        String userInput = INDEX_FOURTH_CLIENT.getOneBased() + " "
                + PREFIX_INSURANCE_ID + VALID_INSURANCE_ID + " "
                + PREFIX_CLAIM_ID + VALID_CLAIM_ID;

        DeleteClaimCommand expectedCommand = new DeleteClaimCommand(INDEX_FOURTH_CLIENT,
                VALID_INSURANCE_ID, VALID_CLAIM_ID);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests if {@code DeleteClaimCommandParser} throws a {@code ParseException} when given invalid user input.
     */
    @Test
    public void parse_invalidArgs_throwsParseException() {
        String invalidInput = "invalid_input";

        assertParseFailure(parser, invalidInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClaimCommand.MESSAGE_USAGE));
    }

    /**
     * Tests if {@code DeleteClaimCommandParser}
     * throws a {@code ParseException} when the claim ID is missing from user input.
     */
    @Test
    public void parse_emptyClaimId_throwsParseException() {
        // Test empty claim ID
        String userInputEmptyClaimId = INDEX_FOURTH_CLIENT.getOneBased() + " "
                + PREFIX_INSURANCE_ID + VALID_INSURANCE_ID + " "
                + PREFIX_CLAIM_ID; // Claim ID left empty

        assertParseFailure(parser, userInputEmptyClaimId, Claim.INVALID_CLAIM_ID);
    }

    /**
     * Tests if {@code DeleteClaimCommandParser}
     * throws a {@code ParseException} when the insurance ID is missing from user input.
     */
    @Test
    public void parse_emptyInsuranceId_throwsParseException() {
        // Test empty insurance ID
        String userInputEmptyInsuranceId = INDEX_FOURTH_CLIENT.getOneBased() + " "
                + PREFIX_INSURANCE_ID + " "
                + PREFIX_CLAIM_ID + VALID_CLAIM_ID; // Insurance ID left empty

        assertParseFailure(parser, userInputEmptyInsuranceId, ParserUtil.MESSAGE_UNPARSABLE_INSURANCE_ID);
    }
}
