package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddClaimCommand;

public class AddClaimCommandParserTest {

    private static final int VALID_INSURANCE_ID = 0;
    private static final String VALID_CLAIM_ID = "B1234";
    private static final String VALID_CLAIM_AMOUNT_STRING = "1.00";
    private static final int VALID_CLAIM_AMOUNT_INTEGER = 100;
    private final AddClaimCommandParser parser = new AddClaimCommandParser();

    /**
     * Tests if {@code AddClaimCommandParser} correctly parses valid input
     * and returns a {@code AddClaimCommand}.
     */
    @Test
    public void parse_validArgs_returnsAddClaimCommand() {
        String userInput = INDEX_SECOND_CLIENT.getOneBased() + " "
                + PREFIX_INSURANCE_ID + VALID_INSURANCE_ID + " "
                + PREFIX_CLAIM_ID + VALID_CLAIM_ID + " "
                + PREFIX_CLAIM_AMOUNT + VALID_CLAIM_AMOUNT_STRING;

        AddClaimCommand expectedCommand = new AddClaimCommand(INDEX_SECOND_CLIENT, VALID_INSURANCE_ID,
                VALID_CLAIM_ID, VALID_CLAIM_AMOUNT_INTEGER);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests if {@code AddClaimCommandParser} throws a {@code ParseException}
     * when the insurance ID is missing in the user input.
     */
    @Test
    public void parse_missingInsuranceId_throwsParseException() {
        String missingInsuranceIdInput = INDEX_SECOND_CLIENT.getOneBased() + " "
                + PREFIX_CLAIM_ID + VALID_CLAIM_ID + " "
                + PREFIX_CLAIM_AMOUNT + VALID_CLAIM_AMOUNT_STRING;
        assertParseFailure(parser, missingInsuranceIdInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClaimCommand.MESSAGE_USAGE));
    }

    /**
     * Tests if {@code AddClaimCommandParser} throws a {@code ParseException}
     * when the claim ID is missing in the user input.
     */
    @Test
    public void parse_missingClaimId_throwsParseException() {
        String missingClaimIdInput = INDEX_SECOND_CLIENT.getOneBased() + " "
                + PREFIX_INSURANCE_ID + VALID_INSURANCE_ID + " "
                + PREFIX_CLAIM_AMOUNT + VALID_CLAIM_AMOUNT_STRING;
        assertParseFailure(parser, missingClaimIdInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClaimCommand.MESSAGE_USAGE));
    }

    /**
     * Tests if {@code AddClaimCommandParser} throws a {@code ParseException}
     * when the claim amount is missing in the user input.
     */
    @Test
    public void parse_missingClaimAmount_throwsParseException() {
        String missingClaimAmountInput = INDEX_SECOND_CLIENT.getOneBased() + " "
                + PREFIX_INSURANCE_ID + VALID_INSURANCE_ID + " "
                + PREFIX_CLAIM_ID + VALID_CLAIM_ID;
        assertParseFailure(parser, missingClaimAmountInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClaimCommand.MESSAGE_USAGE));
    }
}
