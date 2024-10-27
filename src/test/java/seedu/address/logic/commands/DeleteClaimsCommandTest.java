package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.person.Person;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.PolicyType;
import seedu.address.testutil.PersonBuilder;


public class DeleteClaimsCommandTest {

    private Model model;
    private final Claim validClaim = new Claim(ClaimStatus.PENDING, "Surgery");
    private final PolicyType validPolicyType = PolicyType.HEALTH;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new DeleteClaimsCommand(null, validPolicyType, ClaimStatus.PENDING,
                        "Surgery"));
    }

    @Test
    public void constructor_nullPolicyType_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new DeleteClaimsCommand(INDEX_FIRST_PERSON, null, ClaimStatus.PENDING,
                        "Surgery"));
    }

    @Test
    public void constructor_nullClaimStatus_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new DeleteClaimsCommand(INDEX_FIRST_PERSON, validPolicyType, null, "Surgery"));
    }

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new DeleteClaimsCommand(INDEX_FIRST_PERSON, validPolicyType, ClaimStatus.PENDING, null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        DeleteClaimsCommand command = new DeleteClaimsCommand(INDEX_FIRST_PERSON, validPolicyType, ClaimStatus.PENDING,
                "Surgery");
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteClaimsCommand deleteClaimsCommand = new DeleteClaimsCommand(outOfBoundIndex, validPolicyType,
                ClaimStatus.PENDING,
                "Surgery");
        assertCommandFailure(deleteClaimsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noPolicyType_throwsCommandException() {
        Person person = createPersonWithoutPolicies();
        model.addPerson(person);

        DeleteClaimsCommand command = new DeleteClaimsCommand(INDEX_FIRST_PERSON, PolicyType.LIFE, ClaimStatus.PENDING,
                "Surgery");
        assertCommandFailure(command, model, String.format(DeleteClaimsCommand.MESSAGE_NO_POLICY_OF_TYPE,
                PolicyType.LIFE, person.getName()));
    }

    @Test
    public void execute_noMatchingClaim_throwsCommandException() {
        Person person = createPersonWithPolicy();
        model.addPerson(person);

        DeleteClaimsCommand command = new DeleteClaimsCommand(INDEX_FIRST_PERSON, validPolicyType, ClaimStatus.APPROVED,
                "Nonexistent claim");
        assertCommandFailure(command, model, DeleteClaimsCommand.MESSAGE_NO_CLAIM_FOUND);
    }

    //    @Test
    //    public void execute_deleteClaim_success() {
    //        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    //
    //        // Get Benson Meier, who is the second person in the typical list
    //        Person originalPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
    //        PolicyType policyType = PolicyType.HEALTH;
    //
    //        // Add a claim to Benson's Health policy
    //        Claim claimToDelete = new Claim(ClaimStatus.PENDING, "Surgery");
    //        Policy healthPolicy = originalPerson.getPolicies().stream()
    //                .filter(policy -> policy.getType().equals(policyType))
    //                .findFirst()
    //                .orElseThrow(() -> new AssertionError("Health policy not found"));
    //
    //        // Add the claim to the policy
    //        healthPolicy.addClaim(claimToDelete);
    //
    //        // Update the PolicySet for the person
    //        PolicySet updatedPolicySet = new PolicySet();
    //        updatedPolicySet.addAll(originalPerson.getPolicies());
    //
    //        // Create a new Person object with the updated policies
    //        Person updatedPerson = new Person(
    //                originalPerson.getName(),
    //                originalPerson.getPhone(),
    //                originalPerson.getEmail(),
    //                originalPerson.getAddress(),
    //                originalPerson.getTags(),
    //                updatedPolicySet
    //        );
    //
    //        // Update the model with the modified person
    //        model.setPerson(originalPerson, updatedPerson);
    //
    //        // Ensure the claim was added
    //        assertTrue(healthPolicy.getClaimList().contains(claimToDelete), "The claim should have been added to the
    //        policy");
    //
    //        // Create the DeleteClaimsCommand
    //        DeleteClaimsCommand command = new DeleteClaimsCommand(INDEX_SECOND_PERSON, policyType,
    //        ClaimStatus.PENDING,
    //        "Surgery");
    //
    //        // Expected success message
    //        String expectedMessage = String.format(DeleteClaimsCommand.MESSAGE_DELETE_CLAIM_SUCCESS, policyType,
    //                updatedPerson.getName(), ClaimStatus.PENDING, "Surgery");
    //
    //        // Create the expected model state after deletion
    //        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    //        healthPolicy.removeClaim(claimToDelete);
    //        expectedModel.setPerson(updatedPerson, updatedPerson);
    //
    //        // Execute the command and verify the result
    //        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //    }

    @Test
    public void equals() {
        DeleteClaimsCommand deleteFirstCommand = new DeleteClaimsCommand(INDEX_FIRST_PERSON, validPolicyType,
                ClaimStatus.PENDING, "Surgery");
        DeleteClaimsCommand deleteSecondCommand = new DeleteClaimsCommand(INDEX_FIRST_PERSON, validPolicyType,
                ClaimStatus.APPROVED, "Dental");

        // same object -> returns true
        Assertions.assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteClaimsCommand deleteFirstCommandCopy = new DeleteClaimsCommand(INDEX_FIRST_PERSON, validPolicyType,
                ClaimStatus.PENDING, "Surgery");
        Assertions.assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        Assertions.assertFalse(deleteFirstCommand.equals(5));

        // null -> returns false
        Assertions.assertFalse(deleteFirstCommand.equals(null));

        // different claim -> returns false
        Assertions.assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    private Person createPersonWithPolicy() {
        // initialize a person with a health policy (without claims)
        HealthPolicy policy = new HealthPolicy();
        return new PersonBuilder().withPolicy(policy).build();
    }

    private Person createPersonWithoutPolicies() {
        // create a person without any policies
        return new PersonBuilder().build();
    }
}
