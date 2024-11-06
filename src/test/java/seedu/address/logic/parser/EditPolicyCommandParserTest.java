package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_COVERAGE_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_PREMIUM_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.CoverageAmount;
import seedu.address.model.policy.EditPolicyDescriptor;
import seedu.address.model.policy.ExpiryDate;
import seedu.address.model.policy.PolicyType;
import seedu.address.model.policy.PremiumAmount;


public class EditPolicyCommandParserTest {

    private static final PolicyType VALID_POLICY_TYPE_HEALTH = PolicyType.HEALTH;
    private static final PremiumAmount VALID_POLICY_PREMIUM = new PremiumAmount("2000");
    private static final CoverageAmount VALID_POLICY_COVERAGE = new CoverageAmount("50000");
    private static final ExpiryDate VALID_POLICY_EXPIRY_DATE = new ExpiryDate("12/31/2025");

    private static final String INVALID_POLICY_TYPE = "HHH";
    private static final String INVALID_PREMIUM_AMOUNT = "-100";
    private static final String INVALID_EXPIRY_DATE = "15/31/2025";

    private EditPolicyCommandParser parser = new EditPolicyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        // All fields provided correctly
        String userInput = "1 " + PREFIX_POLICY_TYPE + VALID_POLICY_TYPE_HEALTH + " "
                + PREFIX_POLICY_PREMIUM_AMOUNT + "2000" + " "
                + PREFIX_POLICY_COVERAGE_AMOUNT + "50000" + " "
                + PREFIX_POLICY_EXPIRY_DATE + "12/31/2025";

        EditPolicyDescriptor descriptor = new EditPolicyDescriptor(VALID_POLICY_TYPE_HEALTH);
        descriptor.setPremiumAmount(VALID_POLICY_PREMIUM);
        descriptor.setCoverageAmount(VALID_POLICY_COVERAGE);
        descriptor.setExpiryDate(VALID_POLICY_EXPIRY_DATE);

        EditPolicyCommand expectedCommand = new EditPolicyCommand(Index.fromOneBased(1), descriptor);

        assertEquals(expectedCommand, parser.parse(userInput));
    }

    @Test
    public void parse_optionalFieldsMissing_failure() {
        // Only policy type provided, no optional fields
        String userInput = "1 " + PREFIX_POLICY_TYPE + VALID_POLICY_TYPE_HEALTH;

        // Expecting a ParseException to be thrown with the "Not Edited" message
        assertThrows(ParseException.class,
                EditPolicyCommand.MESSAGE_NOT_EDITED, () -> parser.parse(userInput));
    }


    @Test
    public void parse_invalidValue_failure() {
        // Invalid policy type (in this case, assume UNKNOWN is invalid)
        String userInputInvalidType = "1 " + PREFIX_POLICY_TYPE + INVALID_POLICY_TYPE;
        assertThrows(ParseException.class, () -> parser.parse(userInputInvalidType));

        // Invalid premium amount (non-numeric or negative)
        String userInputInvalidPremium = "1 " + PREFIX_POLICY_TYPE + VALID_POLICY_TYPE_HEALTH + " "
                + PREFIX_POLICY_PREMIUM_AMOUNT + INVALID_PREMIUM_AMOUNT;
        assertThrows(ParseException.class, () -> parser.parse(userInputInvalidPremium));

        // Invalid expiry date (malformed)
        String userInputInvalidDate = "1 " + PREFIX_POLICY_TYPE + VALID_POLICY_TYPE_HEALTH + " "
                + PREFIX_POLICY_EXPIRY_DATE + INVALID_EXPIRY_DATE;
        assertThrows(ParseException.class, () -> parser.parse(userInputInvalidDate));
    }

    @Test
    public void parse_missingPolicyType_failure() {
        // Missing policy type field
        String userInput = "1 " + PREFIX_POLICY_PREMIUM_AMOUNT + "2000";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_noFieldEdited_failure() {
        // No optional fields and no edit descriptor
        String userInput = "1 " + PREFIX_POLICY_TYPE + VALID_POLICY_TYPE_HEALTH;
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidIndex_failure() {
        // Invalid index (non-integer input)
        String userInput = "a " + PREFIX_POLICY_TYPE + VALID_POLICY_TYPE_HEALTH;
        assertThrows(ParseException.class, () -> parser.parse(userInput));

        // Index out of bounds
        String userInputOutOfBounds = "99999 " + PREFIX_POLICY_TYPE + VALID_POLICY_TYPE_HEALTH;
        assertThrows(ParseException.class, () -> parser.parse(userInputOutOfBounds));
    }
}
