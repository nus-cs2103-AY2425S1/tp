package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.EditPolicyCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.PolicySet;

public class EditPolicyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final LifePolicy life = new LifePolicy();
    private final HealthPolicy health = new HealthPolicy();

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        final PolicySet policies = new PolicySet();
        assertThrows(NullPointerException.class, () -> new EditPolicyCommand(null, policies));
        assertThrows(NullPointerException.class, () -> new EditPolicyCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void execute_throwsException() {
        final PolicySet policies = new PolicySet();
        policies.add(health);

        assertCommandFailure(new EditPolicyCommand(INDEX_FIRST_PERSON, policies), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), policies));
    }

    @Test
    public void equals() {
        final PolicySet lifePolicies = new PolicySet();
        lifePolicies.add(life);
        final PolicySet healthPolicies = new PolicySet();
        healthPolicies.add(health);

        final EditPolicyCommand standardCommand = new EditPolicyCommand(INDEX_FIRST_PERSON, lifePolicies);
        final EditPolicyCommand commandWithSameValues = new EditPolicyCommand(INDEX_FIRST_PERSON, lifePolicies);
        final EditPolicyCommand differentIndexCommand = new EditPolicyCommand(INDEX_SECOND_PERSON, lifePolicies);
        final EditPolicyCommand differentPoliciesCommand = new EditPolicyCommand(INDEX_FIRST_PERSON,
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

