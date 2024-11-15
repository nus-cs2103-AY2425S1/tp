package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalPrudy;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.LifePolicy;

public class AddPolicyCommandTest {
    private final LifePolicy life = new LifePolicy();
    private final HealthPolicy health = new HealthPolicy();

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPolicyCommand(null, health));
        assertThrows(NullPointerException.class, () -> new AddPolicyCommand(INDEX_FIRST_CLIENT, null));
    }

    @Test
    public void execute_addPolicy_success() throws Exception {
        Model model = new ModelManager(getTypicalPrudy(), new UserPrefs());

        // Client at index has no health policies
        AddPolicyCommand command = new AddPolicyCommand(INDEX_FIRST_CLIENT, health);

        Client client = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        String expectedMessage = String.format(AddPolicyCommand.MESSAGE_SUCCESS, client.getName(), health);

        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        AddPolicyCommand command = new AddPolicyCommand(INDEX_FIRST_CLIENT, health);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_duplicatePolicy_throwsCommandException() {
        Model model = new ModelManager(getTypicalPrudy(), new UserPrefs());

        // Client at second index already has health policy
        AddPolicyCommand command = new AddPolicyCommand(INDEX_SECOND_CLIENT, health);
        String expectedMessage = String.format(AddPolicyCommand.MESSAGE_DUPLICATES, health.getType());

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalPrudy(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(outOfBoundIndex, life);
        assertCommandFailure(addPolicyCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddPolicyCommand standardCommand = new AddPolicyCommand(INDEX_FIRST_CLIENT, health);
        final AddPolicyCommand commandWithSameValues = new AddPolicyCommand(INDEX_FIRST_CLIENT, health);
        final AddPolicyCommand differentIndexCommand = new AddPolicyCommand(INDEX_SECOND_CLIENT, health);
        final AddPolicyCommand differentPoliciesCommand = new AddPolicyCommand(INDEX_FIRST_CLIENT, life);

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
