package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.getTypicalPrudy;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.policy.Policy;
import seedu.address.testutil.TypicalClients;

public class ListPoliciesCommandTest {

    // one index greater than the last index in the typical Prudy
    private static final Index INDEX_OUT_OF_BOUNDS = Index.fromOneBased(TypicalClients
            .getTypicalPrudy().getClientList().size() + 1);

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPrudy(), new UserPrefs());
    }

    @Test
    public void execute_validIndexWithPolicies_success() throws Exception {
        // Benson Meier is the second client in the list with one policy
        ListPoliciesCommand command = new ListPoliciesCommand(INDEX_SECOND_CLIENT);
        Client clientWithPolicies = model.getFilteredClientList().get(INDEX_SECOND_CLIENT.getZeroBased());

        String expectedMessage = clientWithPolicies.getPolicies().stream()
                .map(Policy::toString)
                .collect(Collectors.joining("\n"));
        expectedMessage = String.format(ListPoliciesCommand.MESSAGE_SUCCESS,
                clientWithPolicies.getName(), expectedMessage);

        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_validIndexWithNoPolicies_success() throws Exception {
        // Alice Pauline is the first client in the list with no policies
        ListPoliciesCommand command = new ListPoliciesCommand(INDEX_FIRST_CLIENT);
        Client clientWithoutPolicies = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());

        String expectedMessage = String.format(ListPoliciesCommand.MESSAGE_NO_POLICIES,
                clientWithoutPolicies.getName());

        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ListPoliciesCommand command = new ListPoliciesCommand(INDEX_OUT_OF_BOUNDS);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals() {
        ListPoliciesCommand command1 = new ListPoliciesCommand(INDEX_FIRST_CLIENT);
        ListPoliciesCommand command2 = new ListPoliciesCommand(INDEX_SECOND_CLIENT);
        ListPoliciesCommand command3 = new ListPoliciesCommand(INDEX_FIRST_CLIENT);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command3));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different index -> returns false
        assertFalse(command1.equals(command2));
    }
}
