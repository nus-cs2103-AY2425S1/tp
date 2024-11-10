package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.getTypicalPrudy;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import java.util.List;
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

    private static final Index INDEX_OUT_OF_BOUNDS = Index.fromOneBased(TypicalClients
            .getTypicalPrudy().getClientList().size() + 1);

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPrudy(), new UserPrefs());
    }

    @Test
    public void execute_validIndexWithPolicies_success() throws Exception {
        executeAndAssertSuccess(INDEX_SECOND_CLIENT, true);
    }

    @Test
    public void execute_validIndexWithNoPolicies_success() throws Exception {
        executeAndAssertSuccess(INDEX_FIRST_CLIENT, false);
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

    /**
     * Executes a ListPoliciesCommand and asserts that the result message matches the expected message.
     *
     * @param clientIndex The index of the client in the list.
     * @param hasPolicies Indicates whether the client is expected to have policies.
     * @throws CommandException if command execution fails.
     */
    private void executeAndAssertSuccess(Index clientIndex, boolean hasPolicies) throws CommandException {
        ListPoliciesCommand command = new ListPoliciesCommand(clientIndex);
        Client client = model.getFilteredClientList().get(clientIndex.getZeroBased());

        String expectedMessage = hasPolicies ? getExpectedPoliciesMessage(client) : getNoPoliciesMessage(client);
        CommandResult result = command.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    /**
     * Generates the expected success message for a client with policies.
     *
     * @param client The client whose policies are being listed.
     * @return The expected success message.
     */
    private String getExpectedPoliciesMessage(Client client) {
        List<Policy> policyList = client.getPolicies().stream().collect(Collectors.toList());
        String formattedPolicies = policyList.stream()
                .map(Policy::toString)
                .collect(Collectors.joining("\n"));
        return String.format(ListPoliciesCommand.MESSAGE_SUCCESS, client.getName(), formattedPolicies);
    }

    /**
     * Generates the expected message for a client with no policies.
     *
     * @param client The client with no policies.
     * @return The expected message indicating no policies.
     */
    private String getNoPoliciesMessage(Client client) {
        return String.format(ListPoliciesCommand.MESSAGE_NO_POLICIES, client.getName());
    }
}
