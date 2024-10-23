package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.CoverageAmount;
import seedu.address.model.policy.EditPolicyDescriptor;
import seedu.address.model.policy.ExpiryDate;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;
import seedu.address.model.policy.PremiumAmount;


/**
 * Contains tests for EditPolicyCommand.
 */
public class EditPolicyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final PolicyType validPolicyType = PolicyType.LIFE;
    private final PremiumAmount validPremiumAmount = new PremiumAmount("2000");
    private final CoverageAmount validCoverageAmount = new CoverageAmount("50000");
    private final ExpiryDate validExpiryDate = new ExpiryDate("12/31/2025");

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        EditPolicyDescriptor descriptor = new EditPolicyDescriptor(validPolicyType);

        assertThrows(NullPointerException.class, () -> new EditPolicyCommand(null, descriptor));
        assertThrows(NullPointerException.class, () ->
                new EditPolicyCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        EditPolicyDescriptor descriptor = new EditPolicyDescriptor(validPolicyType);
        descriptor.setPremiumAmount(validPremiumAmount);

        // Index larger than size of person list
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editPolicyCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_nonExistentPolicy_failure() {
        // Set up a person without any policies
        Person personWithoutPolicies = new Person(new Name("Bob"), new Phone("87654321"),
                new Email("bob@example.com"), new Address("456 Another St"), Set.of(), new PolicySet());

        model.addPerson(personWithoutPolicies);

        // Attempt to edit a policy that does not exist
        EditPolicyDescriptor descriptor = new EditPolicyDescriptor(PolicyType.LIFE);
        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(editPolicyCommand, model, EditPolicyCommand.MESSAGE_POLICY_NOT_FOUND);
    }

    @Test
    public void execute_validEditCommand_policyUpdated() throws Exception {
        // Create a policy for the person
        Policy existingPolicy = Policy.makePolicy(PolicyType.LIFE, new PremiumAmount(2500),
                new CoverageAmount(60000), new ExpiryDate("12/31/2025"));
        PolicySet policy = new PolicySet();
        policy.add(existingPolicy);

        // Create the person with the existing policy
        Person personWithPolicy = new Person(new Name("Alice"), new Phone("98765432"),
                new Email("alice@example.com"), new Address("123 Main St"),
                Set.of(), policy);

        // Prepare the model with the person
        Model model = new ModelManager();
        model.addPerson(personWithPolicy);
        Index index = Index.fromOneBased(1); // Index for the person is 1

        // Create an EditPolicyDescriptor with new values
        PolicyType newPolicyType = PolicyType.LIFE; // Same policy type
        PremiumAmount newPremiumAmount = new PremiumAmount(3000); // New premium amount
        CoverageAmount newCoverageAmount = new CoverageAmount(80000); // New coverage amount
        ExpiryDate newExpiryDate = new ExpiryDate("12/31/2030"); // New expiry date

        EditPolicyDescriptor editPolicyDescriptor = new EditPolicyDescriptor(newPolicyType);
        editPolicyDescriptor.setPremiumAmount(newPremiumAmount);
        editPolicyDescriptor.setCoverageAmount(newCoverageAmount);
        editPolicyDescriptor.setExpiryDate(newExpiryDate);

        // Create the EditPolicyCommand
        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(index, editPolicyDescriptor);

        // Execute the command
        CommandResult result = editPolicyCommand.execute(model);

        // Debugging: Print the updated person and policies after command execution
        Person updatedPerson = model.getFilteredPersonList().get(0);

        // Create the expected edited policy
        Policy updatedPolicy = Policy.makePolicy(newPolicyType, newPremiumAmount,
                newCoverageAmount, newExpiryDate);

        // Check the command result message
        String expectedMessage = String.format("Updated policy\n\n%s policy for %s has been changed to:\n%s ",
                newPolicyType, personWithPolicy.getName(), updatedPolicy);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void equals_differentObjects_false() {
        EditPolicyDescriptor descriptor = new EditPolicyDescriptor(validPolicyType);
        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(INDEX_FIRST_PERSON, descriptor);

        // Different objects, same values
        assertFalse(editPolicyCommand.equals(new Object()));

        // Same command but different index
        assertFalse(editPolicyCommand.equals(new EditPolicyCommand(INDEX_SECOND_PERSON, descriptor)));
    }

    @Test
    public void execute_throwsException() {
        // Create a person with a specific policy type (e.g., HEALTH)
        PolicyType existingPolicyType = PolicyType.HEALTH; // Existing policy type
        Policy existingPolicy = Policy.makePolicy(existingPolicyType, new PremiumAmount(1500),
                new CoverageAmount(10000.50), new ExpiryDate("09/14/2024"));
        PolicySet policies = new PolicySet();
        policies.add(existingPolicy);
        Person personWithPolicy = new Person(new Name("John Doe"), new Phone("12345678"), new Email("john@example.com"),
                new Address("123 Main St"), Set.of(), policies);

        // Add the person to the model
        model.addPerson(personWithPolicy);

        // Now create an EditPolicyDescriptor that attempts to edit a different, non-existent type (e.g., LIFE)
        PolicyType nonExistentPolicyType = PolicyType.LIFE; // This type does not exist for the person
        EditPolicyDescriptor descriptor = new EditPolicyDescriptor(nonExistentPolicyType);

        // Now assert that the command fails as expected
        assertCommandFailure(new EditPolicyCommand(INDEX_FIRST_PERSON, descriptor), model,
                EditPolicyCommand.MESSAGE_POLICY_NOT_FOUND);
    }

    @Test
    public void execute_invalidPolicyType_failure() {
        PolicyType existingPolicyType = PolicyType.HEALTH;
        Policy existingPolicy = Policy.makePolicy(existingPolicyType, new PremiumAmount(1500),
                new CoverageAmount(10000.50), new ExpiryDate("09/14/2024"));
        PolicySet policies = new PolicySet();
        policies.add(existingPolicy);
        Person personWithPolicy = new Person(new Name("Jane Doe"), new Phone("98765432"),
                new Email("jane@example.com"), new Address("456 Another St"), Set.of(), policies);

        model.addPerson(personWithPolicy);

        // Attempt to edit a non-existent policy type
        PolicyType nonExistentPolicyType = PolicyType.LIFE; // This policy type does not exist for the person
        EditPolicyDescriptor descriptor = new EditPolicyDescriptor(nonExistentPolicyType);

        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(editPolicyCommand, model, EditPolicyCommand.MESSAGE_POLICY_NOT_FOUND);
    }




    @Test
    public void equals() {
        EditPolicyDescriptor descriptor = new EditPolicyDescriptor(validPolicyType);
        descriptor.setPremiumAmount(validPremiumAmount);
        descriptor.setCoverageAmount(validCoverageAmount);
        descriptor.setExpiryDate(validExpiryDate);

        EditPolicyDescriptor otherDescriptor = new EditPolicyDescriptor(validPolicyType);
        otherDescriptor.setPremiumAmount(new PremiumAmount("3000"));
        otherDescriptor.setCoverageAmount(new CoverageAmount("60000"));
        otherDescriptor.setExpiryDate(new ExpiryDate("12/31/2026"));

        final EditPolicyCommand standardCommand = new EditPolicyCommand(INDEX_FIRST_PERSON, descriptor);
        final EditPolicyCommand commandWithSameValues = new EditPolicyCommand(INDEX_FIRST_PERSON, descriptor);
        final EditPolicyCommand differentIndexCommand = new EditPolicyCommand(INDEX_SECOND_PERSON, descriptor);
        final EditPolicyCommand differentDescriptorCommand = new EditPolicyCommand(INDEX_FIRST_PERSON, otherDescriptor);

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
        // different descriptor -> returns false
        assertFalse(standardCommand.equals(differentDescriptorCommand));
    }
}
