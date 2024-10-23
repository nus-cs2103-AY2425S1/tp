package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.Policy;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchPolicyCommand}.
 */
public class SearchPolicyCommandTest {

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
        Model model = new ModelManager();

        // Create policy object
        Policy lifeInsurance = new Policy("life insurance", "2024-10-10",
                "2030-10-10", "2024-11-10 100.00");

        String nonExistentPolicyName = "non-existent insurance";
        String expectedMessage = String.format(SearchPolicyCommand.MESSAGE_SUCCESS, nonExistentPolicyName);

        // create a person with health insurance policy
        Person alice = new PersonBuilder().withName("Alice").build();
        alice.setPolicies(new ArrayList<>(Arrays.asList(lifeInsurance)));

        model.addPerson(alice);


        try {
            SearchPolicyCommand command = new SearchPolicyCommand(nonExistentPolicyName);
            Model expectedModel = new ModelManager();
            expectedModel.addPerson(alice);
            expectedModel.updateFilteredPersonList(person -> false);

            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Collections.emptyList(), model.getFilteredPersonList());
        } catch (CommandException e) {
            fail("Execution of command should not fail.");
        }
    }

    @Test
    public void execute_oneMatch_personFound() {
        Model model = new ModelManager();

        // Create policy object
        Policy healthInsurance = new Policy("health insurance", "2024-10-10",
                "2030-10-10", "2024-11-10 100.00");

        // create a person with health insurance policy
        Person alice = new PersonBuilder().withName("Alice").build();
        alice.setPolicies(new ArrayList<>(Arrays.asList(healthInsurance)));

        model.addPerson(alice);

        String policyName = "health insurance";
        String expectedMessage = String.format(SearchPolicyCommand.MESSAGE_SUCCESS, policyName);

        try {
            SearchPolicyCommand command = new SearchPolicyCommand(policyName);
            Model expectedModel = new ModelManager();
            expectedModel.addPerson(alice);
            expectedModel.updateFilteredPersonList(person ->
                    person.getPolicies().stream()
                            .anyMatch(policy -> policy.getPolicyName().replaceAll("\\s+", "")
                                    .equalsIgnoreCase(policyName.replaceAll("\\s+", "")))
            );

            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Collections.singletonList(alice), model.getFilteredPersonList());
        } catch (CommandException e) {
            fail("Execution of command should not fail.");
        }
    }

    @Test
    public void execute_multipleMatches_multiplePersonsFound() {

        Model model = new ModelManager();

        // Create policy object
        Policy healthInsurance = new Policy("health insurance", "2024-10-10",
                "2030-10-10", "2024-11-10 100.00");

        // create a person with health insurance policy
        Person alice = new PersonBuilder().withName("Alice").build();
        alice.setPolicies(new ArrayList<>(Arrays.asList(healthInsurance)));
        model.addPerson(alice);

        // create another person with the same insurance policy
        Person bob = new PersonBuilder().withName("Bob").build();
        bob.setPolicies(new ArrayList<>(Arrays.asList(healthInsurance)));
        model.addPerson(bob);

        String policyName = "HealthInsurance";
        String expectedMessage = String.format(SearchPolicyCommand.MESSAGE_SUCCESS, policyName);

        try {
            SearchPolicyCommand command = new SearchPolicyCommand(policyName);
            Model expectedModel = new ModelManager();
            expectedModel.addPerson(alice);
            expectedModel.addPerson(bob);
            expectedModel.updateFilteredPersonList(person ->
                    person.getPolicies().stream()
                            .anyMatch(policy -> policy.getPolicyName().replaceAll("\\s+", "")
                                    .equalsIgnoreCase(policyName.replaceAll("\\s+", "")))
            );

            assertCommandSuccess(command, model, expectedMessage, expectedModel);
            assertEquals(Arrays.asList(alice, bob), model.getFilteredPersonList());
        } catch (CommandException e) {
            fail("Execution of command should not fail.");
        }
    }
}
