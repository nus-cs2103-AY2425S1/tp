package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPEND_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIER;
import static seedu.address.testutil.Assert.assertThrows;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AgentAssist;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.predicates.NameContainsSubstringPredicate;
import seedu.address.testutil.EditClientDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "91111111";
    public static final String VALID_PHONE_BOB = "82222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_JOB_AMY = "Teacher";
    public static final String VALID_JOB_BOB = "Developer";
    public static final String VALID_INCOME_AMY = "999999999";
    public static final String VALID_INCOME_BOB = "0";
    public static final String VALID_TIER_REJECT = "REJECT";
    public static final String VALID_TIER_GOLD = "GOLD";



    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String JOB_DESC_AMY = " " + PREFIX_JOB + VALID_JOB_AMY;
    public static final String JOB_DESC_BOB = " " + PREFIX_JOB + VALID_JOB_BOB;
    public static final String INCOME_DESC_AMY = " " + PREFIX_INCOME + VALID_INCOME_AMY;
    public static final String INCOME_DESC_BOB = " " + PREFIX_INCOME + VALID_INCOME_BOB;
    public static final String VALID_REMARK_AMY = "Like skiing.";
    public static final String VALID_REMARK_BOB = "Favourite pastime: Eating";
    public static final String TIER_DESC_REJECT = " " + PREFIX_TIER + VALID_TIER_REJECT;
    public static final String TIER_DESC_GOLD = " " + PREFIX_TIER + VALID_TIER_GOLD;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_BOB;
    public static final String NEW_REMARK_DESC_BOB = " " + PREFIX_NEW_REMARK + VALID_REMARK_BOB;
    public static final String APPEND_REMARK_DESC_BOB = " " + PREFIX_APPEND_REMARK + VALID_REMARK_BOB;
    public static final String VALID_STATUS_NON_URGENT = "non_urgent";
    public static final String VALID_STATUS_NA = "na";
    public static final String STATUS_DESC_AMY = " " + PREFIX_STATUS + VALID_STATUS_NA;
    public static final String STATUS_DESC_BOB = " " + PREFIX_STATUS + VALID_STATUS_NON_URGENT;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_JOB_DESC = " " + PREFIX_JOB; // empty string not allowed for job
    public static final String INVALID_INCOME_DESC = " " + PREFIX_INCOME + "-999"; // negative numbers should not be
    // allowed
    public static final String INVALID_TIER_DESC = " " + PREFIX_TIER + "platinum"; // not one of the 4 TierEnums
    public static final String INVALID_REMARK_DESC = " " + PREFIX_REMARK + "漢";
    public static final String INVALID_NEW_REMARK_DESC = " " + PREFIX_NEW_REMARK;
    public static final String INVALID_APPEND_REMARK_DESC = " " + PREFIX_APPEND_REMARK;
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS + "null";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";


    public static final EditCommand.EditClientDescriptor DESC_AMY;
    public static final EditCommand.EditClientDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditClientDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withJob(VALID_JOB_AMY).withIncome(new BigInteger(VALID_INCOME_AMY))
                .withTier(VALID_TIER_GOLD).withNewRemark(VALID_REMARK_AMY).build();

        DESC_BOB = new EditClientDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withJob(VALID_JOB_BOB).withIncome(new BigInteger(VALID_INCOME_BOB))
                .withTier(VALID_TIER_REJECT).withNewRemark(VALID_REMARK_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered client list and selected client in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AgentAssist expectedAgentAssist = new AgentAssist(actualModel.getAgentAssist());
        List<Client> expectedFilteredList = new ArrayList<>(actualModel.getFilteredClientList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel, false));
        assertEquals(expectedAgentAssist, actualModel.getAgentAssist());
        assertEquals(expectedFilteredList, actualModel.getFilteredClientList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the client at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showClientAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredClientList().size());

        Client client = model.getFilteredClientList().get(targetIndex.getZeroBased());
        final String[] splitName = client.getName().fullName.split("\\s+");
        model.updateFilteredClientList(new NameContainsSubstringPredicate(splitName[0]));

        assertEquals(1, model.getFilteredClientList().size());
    }

}
