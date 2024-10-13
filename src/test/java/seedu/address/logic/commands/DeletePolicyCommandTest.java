package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.EducationPolicy;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.PolicyType;

public class DeletePolicyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final LifePolicy life = new LifePolicy();
    private final HealthPolicy health = new HealthPolicy();
    private final EducationPolicy education = new EducationPolicy();

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        final PolicyType policyType = PolicyType.LIFE;
        assertThrows(NullPointerException.class, () -> new DeletePolicyCommand(null, policyType));
        assertThrows(NullPointerException.class, () -> new DeletePolicyCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void execute_throwsException() {
        final PolicyType policyType = PolicyType.LIFE;

        assertCommandFailure(new DeletePolicyCommand(INDEX_FIRST_PERSON, policyType), model, "Policy not found.");
    }

    @Test
    public void equals() {
        final PolicyType policyLife = PolicyType.LIFE;
        final PolicyType policyEdu = PolicyType.EDUCATION;

        final DeletePolicyCommand standardCommand = new DeletePolicyCommand(INDEX_FIRST_PERSON, policyLife);
        final DeletePolicyCommand commandWithSameValues = new DeletePolicyCommand(INDEX_FIRST_PERSON, policyLife);
        final DeletePolicyCommand differentIndexCommand = new DeletePolicyCommand(INDEX_SECOND_PERSON, policyLife);
        final DeletePolicyCommand differentPoliciesCommand = new DeletePolicyCommand(INDEX_FIRST_PERSON,
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

