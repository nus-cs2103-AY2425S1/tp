package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CloseClaimCommand;

public class CloseClaimCommandParserTest {

    private static final int VALID_INSURANCE_ID = 0;
    private static final String VALID_CLAIM_ID = "A1001";
    private final CloseClaimCommandParser parser = new CloseClaimCommandParser();

    /**
     * Tests if {@code CloseClaimCommandParser} correctly parses valid input
     * and returns a {@code CloseClaimCommand}.
     */
    @Test
    public void parse_validArgs_returnsCloseClaimCommand() {
        String userInput = INDEX_SECOND_CLIENT.getOneBased() + " "
                + PREFIX_INSURANCE_ID + VALID_INSURANCE_ID + " "
                + PREFIX_CLAIM_ID + VALID_CLAIM_ID;

        CloseClaimCommand expectedCommand = new CloseClaimCommand(INDEX_SECOND_CLIENT, VALID_INSURANCE_ID,
                VALID_CLAIM_ID);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests if {@code CloseClaimCommandParser} throws a {@code ParseException}
     * when the insurance ID is missing in the user input.
     */
    @Test
    public void parse_missingInsuranceId_throwsParseException() {
        String missingInsuranceIdInput = INDEX_SECOND_CLIENT.getOneBased() + " "
                + PREFIX_CLAIM_ID + VALID_CLAIM_ID + " ";
        assertParseFailure(parser, missingInsuranceIdInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseClaimCommand.MESSAGE_USAGE));
    }

    /**
     * Tests if {@code CloseClaimCommandParser} throws a {@code ParseException}
     * when the claim ID is missing in the user input.
     */
    @Test
    public void parse_missingClaimId_throwsParseException() {
        String missingClaimIdInput = INDEX_SECOND_CLIENT.getOneBased() + " "
                + PREFIX_INSURANCE_ID + VALID_INSURANCE_ID;
        assertParseFailure(parser, missingClaimIdInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseClaimCommand.MESSAGE_USAGE));
    }
}
