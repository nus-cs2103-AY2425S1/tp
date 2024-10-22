package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchPolicyCommand}.
 */
public class SearchPolicyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new seedu.address.model.UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new seedu.address.model.UserPrefs());

    @Test
    public void equals() throws CommandException {
        SearchPolicyCommand searchFirstCommand = new SearchPolicyCommand("HealthInsurance");
        SearchPolicyCommand searchSecondCommand = new SearchPolicyCommand("AutoInsurance");

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchPolicyCommand searchFirstCommandCopy = new SearchPolicyCommand("healthinsurance"); // different case
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different policyName -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void constructor_nullPolicyName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SearchPolicyCommand(null));
    }

    @Test
    public void constructor_invalidPolicyName_throwsCommandException() {
        String invalidPolicyName = "   ";
        assertThrows(CommandException.class, () -> new SearchPolicyCommand(invalidPolicyName));
    }

    @Test
    public void execute_noPersonWithPolicy_noPersonFound() {
        String policyName = "LifeInsurance";
        String expectedMessage = String.format(SearchPolicyCommand.MESSAGE_SUCCESS, policyName);

        try {
            SearchPolicyCommand command = new SearchPolicyCommand(policyName);
            expectedModel.updateFilteredPersonList(person -> false);

            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Collections.emptyList(), model.getFilteredPersonList());
        } catch (CommandException e) {
            fail("Execution of command should not fail.");
        }
    }

    @Test
    public void execute_oneMatch_personFound() {
        String policyName = "AutoInsurance";
        String expectedMessage = String.format(SearchPolicyCommand.MESSAGE_SUCCESS, policyName);

        try {
            SearchPolicyCommand command = new SearchPolicyCommand(policyName);
            expectedModel.updateFilteredPersonList(person ->
                    person.getPolicies().stream()
                            .anyMatch(policy -> policy.getPolicyName().replaceAll("\\s+", "").equalsIgnoreCase(policyName.replaceAll("\\s+", "")))
            );

            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Collections.singletonList(BOB), model.getFilteredPersonList());
        } catch (CommandException e) {
            fail("Execution of command should not fail.");
        }
    }

    @Test
    public void execute_multipleMatches_multiplePersonsFound() {
        String policyName = "HealthInsurance"; // Assume AMY and FIONA have this policy
        String expectedMessage = String.format(SearchPolicyCommand.MESSAGE_SUCCESS, policyName);

        try {
            SearchPolicyCommand command = new SearchPolicyCommand(policyName);
            // Update expectedModel to filter AMY and FIONA
            expectedModel.updateFilteredPersonList(person ->
                    person.getPolicies().stream()
                            .anyMatch(policy -> policy.getPolicyName().replaceAll("\\s+", "").equalsIgnoreCase(policyName.replaceAll("\\s+", "")))
            );

            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Arrays.asList(AMY, FIONA), model.getFilteredPersonList());
        } catch (CommandException e) {
            fail("Execution of command should not fail.");
        }
    }
}
