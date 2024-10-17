package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
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

    //    @Test
    //    public void execute_deletePolicyUnfilteredList_success() {
    //        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //
    //    }
    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        final Set<PolicyType> policyTypes = new HashSet<>();
        policyTypes.add(PolicyType.LIFE);
        assertThrows(NullPointerException.class, () -> new DeletePolicyCommand(null, policyTypes));
        assertThrows(NullPointerException.class, () -> new DeletePolicyCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void execute_throwsException() {
        final Set<PolicyType> policyTypes = new HashSet<>();
        policyTypes.add(PolicyType.LIFE);

        assertCommandFailure(new DeletePolicyCommand(INDEX_FIRST_PERSON, policyTypes), model, "Policy not found.");
    }
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        final Set<PolicyType> policyTypes = new HashSet<>();
        policyTypes.add(PolicyType.LIFE);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletePolicyCommand command = new DeletePolicyCommand(outOfBoundIndex, policyTypes);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        final Set<PolicyType> policyTypes = new HashSet<>();
        policyTypes.add(PolicyType.LIFE);
        policyTypes.add(PolicyType.EDUCATION);
        final Set<PolicyType> policyEdu = new HashSet<>();
        policyEdu.add(PolicyType.EDUCATION);

        final DeletePolicyCommand standardCommand = new DeletePolicyCommand(INDEX_FIRST_PERSON, policyTypes);
        final DeletePolicyCommand commandWithSameValues = new DeletePolicyCommand(INDEX_FIRST_PERSON, policyTypes);
        final DeletePolicyCommand differentIndexCommand = new DeletePolicyCommand(INDEX_SECOND_PERSON, policyTypes);
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

