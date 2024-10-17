package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AddPolicyCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.AddPolicyCommand.MESSAGE_DUPLICATES;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import seedu.address.model.tag.Tag;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.EducationPolicy;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.PolicySet;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.person.Person;

public class AddPolicyCommandTest {

    private final LifePolicy life = new LifePolicy();
    private final HealthPolicy health = new HealthPolicy();
    private final EducationPolicy education = new EducationPolicy();

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        final PolicySet policies = new PolicySet();
        assertThrows(NullPointerException.class, () -> new AddPolicyCommand(null, policies));
        assertThrows(NullPointerException.class, () -> new AddPolicyCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        final PolicySet policies = new PolicySet();
        policies.add(life);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(outOfBoundIndex, policies);

        assertCommandFailure(addPolicyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addMultiplePolicies_success() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        final PolicySet policies = new PolicySet();
        policies.add(life);
        policies.add(health);

        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(INDEX_FIRST_PERSON, policies);

        Person personToAddPolicy = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(AddPolicyCommand.POLICY_ADD_PERSON_SUCCESS,
                String.format("%s; Phone: %s; Email: %s; Address: %s; Tags: %s",
                        personToAddPolicy.getName(),
                        personToAddPolicy.getPhone(),
                        personToAddPolicy.getEmail(),
                        personToAddPolicy.getAddress(),
                        personToAddPolicy.getTags().stream()
                                .map(Tag::toString) // Convert each Tag to its string representation
                                .collect(Collectors.joining(", ")) // Join them with a comma and space
                ));

        assertCommandSuccess(addPolicyCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_duplicatePolicy_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        PolicySet policies = new PolicySet();
        policies.add(life);  
        policies.add(life);

        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(INDEX_FIRST_PERSON, policies);

        assertCommandFailure(addPolicyCommand, model, MESSAGE_DUPLICATES);
    }

    @Test
    public void execute_emptyPolicySet_success() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        final PolicySet policies = new PolicySet();  // Empty set

        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(INDEX_FIRST_PERSON, policies);

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = String.format(AddPolicyCommand.POLICY_ADD_PERSON_SUCCESS,
                String.format("%s; Phone: %s; Email: %s; Address: %s; Tags: %s",
                        personToEdit.getName(),
                        personToEdit.getPhone(),
                        personToEdit.getEmail(),
                        personToEdit.getAddress(),
                        personToEdit.getTags().stream()
                                .map(Tag::toString) // Convert each Tag to its string representation
                                .collect(Collectors.joining(", ")) // Join them with a comma and space
                ));

        assertCommandSuccess(addPolicyCommand, model, expectedMessage, model);
    }

    @Test
    public void equals() {
        final PolicySet lifePolicies = new PolicySet();
        lifePolicies.add(life);
        final PolicySet educationPolicies = new PolicySet();
        educationPolicies.add(education);

        final AddPolicyCommand standardCommand = new AddPolicyCommand(INDEX_FIRST_PERSON, lifePolicies);
        final AddPolicyCommand commandWithSameValues = new AddPolicyCommand(INDEX_FIRST_PERSON, lifePolicies);
        final AddPolicyCommand differentIndexCommand = new AddPolicyCommand(INDEX_SECOND_PERSON, lifePolicies);
        final AddPolicyCommand differentPoliciesCommand = new AddPolicyCommand(INDEX_FIRST_PERSON, educationPolicies);

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

    @Test
    public void equals_differentPolicies_returnsFalse() {
        final PolicySet firstPolicySet = new PolicySet();
        firstPolicySet.add(life);

        final PolicySet secondPolicySet = new PolicySet();
        secondPolicySet.add(education);

        AddPolicyCommand firstCommand = new AddPolicyCommand(INDEX_FIRST_PERSON, firstPolicySet);
        AddPolicyCommand secondCommand = new AddPolicyCommand(INDEX_FIRST_PERSON, secondPolicySet);

        // Different policies, same index -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }
}
