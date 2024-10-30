package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditClaimCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.claim.EditClaimDescriptor;
import seedu.address.model.policy.PolicyType;


public class EditClaimCommandParserTest {

    private final EditClaimCommandParser parser = new EditClaimCommandParser();

    @Test
    public void parse_validArgs_returnsEditClaimCommand() throws Exception {
        // Example of valid input
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_INDEX + "2 "
                + PREFIX_CLAIM_STATUS + "approved " + PREFIX_CLAIM_DESC + "Updated claim details.";

        EditClaimDescriptor descriptor = new EditClaimDescriptor();
        descriptor.setStatus(ClaimStatus.APPROVED);
        descriptor.setDescription("Updated claim details.");

        EditClaimCommand expectedCommand = new EditClaimCommand(
                Index.fromOneBased(1), PolicyType.HEALTH, Index.fromOneBased(2), descriptor);

        EditClaimCommand actualCommand = parser.parse(userInput);

        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_missingPolicyType_throwsParseException() {
        // Missing policy type
        String userInput = "1 " + PREFIX_CLAIM_INDEX + "2 " + PREFIX_CLAIM_STATUS + "approved";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingClaimIndex_throwsParseException() {
        // Missing claim index
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_STATUS + "approved";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPersonIndex_throwsParseException() {
        // Missing person index (preamble)
        String userInput = PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_INDEX + "2 "
                + PREFIX_CLAIM_STATUS + "approved";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClaimCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noFieldsToEdit_throwsParseException() {
        // No fields provided to edit (only required fields are provided)
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_INDEX + "2";

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                EditClaimCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_emptyDescription_ignoresDescription() throws Exception {
        // Description provided but empty, should be ignored
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_INDEX + "2 "
                + PREFIX_CLAIM_STATUS + "approved " + PREFIX_CLAIM_DESC;

        EditClaimDescriptor descriptor = new EditClaimDescriptor();
        descriptor.setStatus(ClaimStatus.APPROVED);

        EditClaimCommand expectedCommand = new EditClaimCommand(
                Index.fromOneBased(1), PolicyType.HEALTH, Index.fromOneBased(2), descriptor);

        EditClaimCommand actualCommand = parser.parse(userInput);

        assertEquals(expectedCommand, actualCommand);
    }
}
