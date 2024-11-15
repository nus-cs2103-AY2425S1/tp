package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalPrudy;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Prudy;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;
import seedu.address.testutil.ClientBuilder;


public class DeletePoliciesCommandTest {
    private Model model = new ModelManager(getTypicalPrudy(), new UserPrefs());

    @Test
    public void execute_deletePolicyUnfilteredList_success() {
        Client secondClient = model.getFilteredClientList().get(INDEX_SECOND_CLIENT.getZeroBased());
        Client editedClient = new ClientBuilder(secondClient).withPolicies().build();

        Set<PolicyType> policiesToDelete = new HashSet<>();
        policiesToDelete.add(PolicyType.HEALTH);
        DeletePoliciesCommand deletePoliciesCommand = new DeletePoliciesCommand(INDEX_SECOND_CLIENT, policiesToDelete);

        PolicySet expectedPolicies = new PolicySet(editedClient.getPolicies());
        String expectedMessage = String.format(DeletePoliciesCommand.MESSAGE_SUCCESS,
                Messages.formatPolicies(expectedPolicies));

        // Create a new expected model with the client having the "health" policy (i.e., the "life" policy is removed)
        Model expectedModel = new ModelManager(new Prudy(model.getPrudy()), new UserPrefs());
        expectedModel.setClient(secondClient, editedClient);

        // Act and Assert: Execute the command and compare results
        assertCommandSuccess(deletePoliciesCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        final Set<PolicyType> policyTypes = new HashSet<>();
        policyTypes.add(PolicyType.LIFE);
        assertThrows(NullPointerException.class, () -> new DeletePoliciesCommand(null, policyTypes));
        assertThrows(NullPointerException.class, () -> new DeletePoliciesCommand(INDEX_FIRST_CLIENT, null));
    }

    @Test
    public void execute_throwsException() {
        final Set<PolicyType> policyTypes = new HashSet<>();
        policyTypes.add(PolicyType.LIFE);

        assertCommandFailure(new DeletePoliciesCommand(INDEX_FIRST_CLIENT, policyTypes), model, "Policy not found.");
    }
    @Test
    public void execute_invalidClientIndexUnfilteredList_failure() {
        final Set<PolicyType> policyTypes = new HashSet<>();
        policyTypes.add(PolicyType.LIFE);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        DeletePoliciesCommand command = new DeletePoliciesCommand(outOfBoundIndex, policyTypes);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        final Set<PolicyType> policyTypes = new HashSet<>();
        policyTypes.add(PolicyType.LIFE);
        policyTypes.add(PolicyType.EDUCATION);
        final Set<PolicyType> policyEdu = new HashSet<>();
        policyEdu.add(PolicyType.EDUCATION);

        final DeletePoliciesCommand standardCommand = new DeletePoliciesCommand(INDEX_FIRST_CLIENT, policyTypes);
        final DeletePoliciesCommand commandWithSameValues = new DeletePoliciesCommand(INDEX_FIRST_CLIENT, policyTypes);
        final DeletePoliciesCommand differentIndexCommand = new DeletePoliciesCommand(INDEX_SECOND_CLIENT, policyTypes);
        final DeletePoliciesCommand differentPoliciesCommand = new DeletePoliciesCommand(INDEX_FIRST_CLIENT,
                policyEdu);

        // same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // entirely different command -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(differentIndexCommand));
        // different policies -> returns false
        assertFalse(standardCommand.equals(differentPoliciesCommand));
    }
}

