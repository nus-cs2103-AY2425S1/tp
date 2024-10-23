package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteInsuranceCommand;

/**
 * Contains unit tests for {@link DeleteInsuranceCommandParser}.
 */
public class DeleteInsuranceCommandParserTest {

    private static final int VALID_INSURANCE_ID = 0;
    private final DeleteInsuranceCommandParser parser = new DeleteInsuranceCommandParser();

    /**
     * Tests if {@code DeleteInsuranceCommandParser} correctly parses valid input
     * and returns a {@code DeleteInsuranceCommand}.
     */
    @Test
    public void parse_validArgs_returnsDeleteInsuranceCommand() {
        // Valid user input with client index and insurance ID
        String userInput = INDEX_FOURTH_CLIENT.getOneBased() + " "
                + PREFIX_INSURANCE_ID + VALID_INSURANCE_ID;

        DeleteInsuranceCommand expectedCommand = new DeleteInsuranceCommand(INDEX_FOURTH_CLIENT, VALID_INSURANCE_ID);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /**
     * Tests if {@code DeleteInsuranceCommandParser} throws a {@code ParseException}
     * when an invalid client index is provided.
     */
    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Invalid index input (non-numeric)
        String invalidUserInput = "invalidIndex " + PREFIX_INSURANCE_ID + VALID_INSURANCE_ID;
        assertParseFailure(parser, invalidUserInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteInsuranceCommand.MESSAGE_USAGE));
    }

    /**
     * Tests if {@code DeleteInsuranceCommandParser} throws a {@code ParseException}
     * when the insurance ID is missing in the user input.
     */
    @Test
    public void parse_missingInsuranceId_throwsParseException() {
        // Missing insurance ID
        String missingInsuranceIdInput = INDEX_FOURTH_CLIENT + " " + PREFIX_INSURANCE_ID;
        assertParseFailure(parser, missingInsuranceIdInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteInsuranceCommand.MESSAGE_USAGE));
    }

    /**
     * Tests if {@code DeleteInsuranceCommandParser} throws a {@code ParseException}
     * when an invalid insurance ID (non-numeric) is provided.
     */
    @Test
    public void parse_invalidInsuranceId_throwsParseException() {
        // Invalid insurance ID (non-numeric)
        String invalidInsuranceIdInput = INDEX_FOURTH_CLIENT + " " + PREFIX_INSURANCE_ID + "invalidId";
        assertParseFailure(parser, invalidInsuranceIdInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteInsuranceCommand.MESSAGE_USAGE));
    }
}
