package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_CLIENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.CARL;
import static seedu.address.testutil.TypicalClients.ELLE;
import static seedu.address.testutil.TypicalClients.FIONA;
import static seedu.address.testutil.TypicalClients.getTypicalPrudy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.CompositePredicate;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.model.client.PhoneMatchesPredicate;
import seedu.address.model.client.PolicyTypeMatchesPredicate;
import seedu.address.model.policy.PolicyType;

/**
 * Contains integration tests (interaction with the Model) for {@code FindClientCommand}.
 */
public class FindClientCommandTest {
    private Model model = new ModelManager(getTypicalPrudy(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPrudy(), new UserPrefs());

    @Test
    public void execute_modelReturnsNullList_throwsNullPointerException() {
        // Create a custom Model implementation
        Model modelWithNullFilteredList = new ModelManager(getTypicalPrudy(), new UserPrefs()) {
            @Override
            public ObservableList<Client> getFilteredClientList() {
                return null; // Return null to simulate the edge case
            }

            @Override
            public void updateFilteredClientList(Predicate<Client> predicate) {
                // Do nothing or retain existing behavior
            }
        };

        FindClientCommand command = new FindClientCommand(client -> true);

        assertThrows(NullPointerException.class, () -> command.execute(modelWithNullFilteredList));
    }

    @Test
    public void constructor_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FindClientCommand(null));
    }

    @Test
    public void constructor_validPredicate_success() {
        Predicate<Client> validPredicate = client -> true;
        FindClientCommand command = new FindClientCommand(validPredicate);
        assertNotNull(command);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Predicate<Client> validPredicate = client -> true;
        FindClientCommand command = new FindClientCommand(validPredicate);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void equals_otherCommandWithNullPredicate_returnsFalse() {
        Predicate<Client> validPredicate = client -> true;
        FindClientCommand command = new FindClientCommand(validPredicate);
        FindClientCommand otherCommand = new FindClientCommand(validPredicate);
        try {
            Field predicateField = FindClientCommand.class.getDeclaredField("predicate");
            predicateField.setAccessible(true);
            predicateField.set(otherCommand, null);
        } catch (Exception e) {
            fail("Failed to set predicate to null using reflection");
        }
        assertFalse(command.equals(otherCommand));
    }

    @Test
    public void equals() {
        // Create different predicates
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        PhoneMatchesPredicate phonePredicate = new PhoneMatchesPredicate("12345678");

        List<Predicate<Client>> predicatesList1 = new ArrayList<>();
        predicatesList1.add(namePredicate);

        List<Predicate<Client>> predicatesList2 = new ArrayList<>();
        predicatesList2.add(phonePredicate);

        CompositePredicate compositePredicate1 = new CompositePredicate(predicatesList1);
        CompositePredicate compositePredicate2 = new CompositePredicate(predicatesList2);

        FindClientCommand findClientCommand1 = new FindClientCommand(compositePredicate1);
        FindClientCommand findClientCommand2 = new FindClientCommand(compositePredicate2);

        // same object -> returns true
        assertTrue(findClientCommand1.equals(findClientCommand1));

        // same values -> returns true
        FindClientCommand findClientCommand1Copy = new FindClientCommand(compositePredicate1);
        assertTrue(findClientCommand1.equals(findClientCommand1Copy));

        // different types -> returns false
        assertFalse(findClientCommand1.equals(1));

        // null -> returns false
        assertFalse(findClientCommand1.equals(null));

        // different predicates -> returns false
        assertFalse(findClientCommand1.equals(findClientCommand2));
    }

    @Test
    public void execute_zeroKeywords_noClientFound() {
        String expectedMessage = String.format(MESSAGE_CLIENTS_LISTED_OVERVIEW, 0);

        NameContainsKeywordsPredicate namePredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("!@#$%^&*()"));
        List<Predicate<Client>> predicatesList = new ArrayList<>();
        predicatesList.add(namePredicate);
        CompositePredicate compositePredicate = new CompositePredicate(predicatesList);

        FindClientCommand command = new FindClientCommand(compositePredicate);
        expectedModel.updateFilteredClientList(compositePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredClientList());
    }

    @Test
    public void execute_multiplePredicates_multipleClientsFound() {
        String expectedMessage = String.format(MESSAGE_CLIENTS_LISTED_OVERVIEW, 1);

        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("Kurz"));
        PhoneMatchesPredicate phonePredicate = new PhoneMatchesPredicate("95352563"); // CARL's phone

        List<Predicate<Client>> predicatesList = new ArrayList<>();
        predicatesList.add(namePredicate);
        predicatesList.add(phonePredicate);
        CompositePredicate compositePredicate = new CompositePredicate(predicatesList);

        FindClientCommand command = new FindClientCommand(compositePredicate);
        expectedModel.updateFilteredClientList(compositePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CARL), model.getFilteredClientList());
    }

    @Test
    public void execute_singlePredicate_multipleClientsFound() {
        String expectedMessage = String.format(MESSAGE_CLIENTS_LISTED_OVERVIEW, 3);

        NameContainsKeywordsPredicate namePredicate =
            new NameContainsKeywordsPredicate(Arrays.asList("Kurz", "Elle", "Kunz"));

        List<Predicate<Client>> predicatesList = new ArrayList<>();
        predicatesList.add(namePredicate);
        CompositePredicate compositePredicate = new CompositePredicate(predicatesList);

        FindClientCommand command = new FindClientCommand(compositePredicate);
        expectedModel.updateFilteredClientList(compositePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredClientList());
    }

    @Test
    public void execute_invalidPolicyType_noClientFound() {
        String expectedMessage = String.format(MESSAGE_CLIENTS_LISTED_OVERVIEW, 0);

        // Assuming no client has the EDUCATION policy type
        PolicyTypeMatchesPredicate policyTypePredicate = new PolicyTypeMatchesPredicate(PolicyType.EDUCATION);

        List<Predicate<Client>> predicatesList = new ArrayList<>();
        predicatesList.add(policyTypePredicate);
        CompositePredicate compositePredicate = new CompositePredicate(predicatesList);

        FindClientCommand command = new FindClientCommand(compositePredicate);
        expectedModel.updateFilteredClientList(compositePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredClientList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        List<Predicate<Client>> predicatesList = new ArrayList<>();
        predicatesList.add(predicate);
        CompositePredicate compositePredicate = new CompositePredicate(predicatesList);

        FindClientCommand findClientCommand = new FindClientCommand(compositePredicate);
        String expected = FindClientCommand.class.getCanonicalName() + "{predicate=" + compositePredicate + "}";
        assertEquals(expected, findClientCommand.toString());
    }
}
