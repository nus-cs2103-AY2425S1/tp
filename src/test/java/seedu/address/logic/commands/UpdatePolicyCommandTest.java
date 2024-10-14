package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.UpdatePolicyCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.PolicyMap;

public class UpdatePolicyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final LifePolicy life = new LifePolicy();
    private final HealthPolicy health = new HealthPolicy();

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        final PolicyMap policies = new PolicyMap();
        assertThrows(NullPointerException.class, () -> new UpdatePolicyCommand(null, policies));
        assertThrows(NullPointerException.class, () -> new UpdatePolicyCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void execute_throwsException() {
        final PolicyMap policies = new PolicyMap();
        policies.add(health);

        assertCommandFailure(new UpdatePolicyCommand(INDEX_FIRST_PERSON, policies), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), policies));
    }

    @Test
    public void equals() {
        final PolicyMap lifePolicies = new PolicyMap();
        lifePolicies.add(life);
        final PolicyMap healthPolicies = new PolicyMap();
        healthPolicies.add(health);

        final UpdatePolicyCommand standardCommand = new UpdatePolicyCommand(INDEX_FIRST_PERSON, lifePolicies);
        final UpdatePolicyCommand commandWithSameValues = new UpdatePolicyCommand(INDEX_FIRST_PERSON, lifePolicies);
        final UpdatePolicyCommand differentIndexCommand = new UpdatePolicyCommand(INDEX_SECOND_PERSON, lifePolicies);
        final UpdatePolicyCommand differentPoliciesCommand = new UpdatePolicyCommand(INDEX_FIRST_PERSON,
                healthPolicies);
        // same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different type -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(differentIndexCommand));
        // different policies -> returns false
        assertFalse(standardCommand.equals(differentPoliciesCommand));
    }
}

