package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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

public class AddClaimCommandTest {

    private Model model;
    private final Claim validClaim = new Claim(ClaimStatus.PENDING, "Surgery claim");
    private final PolicyType validPolicyType = PolicyType.HEALTH;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new AddClaimCommand(null, validClaim, validPolicyType));
    }

    @Test
    public void constructor_nullClaim_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new AddClaimCommand(INDEX_FIRST_PERSON, null, validPolicyType));
    }

    @Test
    public void constructor_nullPolicyType_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new AddClaimCommand(INDEX_FIRST_PERSON, validClaim, null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        AddClaimCommand command = new AddClaimCommand(INDEX_FIRST_PERSON, validClaim, validPolicyType);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddClaimCommand addClaimCommand = new AddClaimCommand(outOfBoundIndex, validClaim, validPolicyType);
        assertCommandFailure(addClaimCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addClaim_success() {
        // create a person with an existing policy
        Person person = createPersonWithPolicy();
        model.addPerson(person);

        // add the Health Policy claim
        Claim claim = new Claim(ClaimStatus.PENDING, "Surgery");
        AddClaimCommand command = new AddClaimCommand(INDEX_FIRST_PERSON, claim, person.getPolicies()
                .iterator().next().getType());

        // expected success message
        String expectedMessage = String.format(AddClaimCommand.MESSAGE_ADD_CLAIM_SUCCESS, claim);

        // ensure the command is successful
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_duplicateClaim_throwsCommandException() {
        // create a person with an existing policy and a claim
        Person person = createPersonWithPolicyAndClaim();
        model.addPerson(person);

        // attempt to add the same claim again
        Claim duplicateClaim = new Claim(ClaimStatus.PENDING, "Surgery");
        AddClaimCommand command = new AddClaimCommand(INDEX_FIRST_PERSON, duplicateClaim,
                person.getPolicies().iterator().next().getType());

        // expect failure due to the duplicate claim
        assertCommandFailure(command, model, AddClaimCommand.MESSAGE_CLAIM_EXISTS);
    }

    @Test
    public void equals() {
        Claim claim1 = new Claim(ClaimStatus.PENDING, "Surgery claim");
        Claim claim2 = new Claim(ClaimStatus.APPROVED, "Dental claim");
        PolicyType healthPolicy = PolicyType.HEALTH;
        PolicyType lifePolicy = PolicyType.LIFE;

        AddClaimCommand addClaimFirstCommand = new AddClaimCommand(INDEX_FIRST_PERSON, claim1, healthPolicy);
        AddClaimCommand addClaimSecondCommand = new AddClaimCommand(INDEX_FIRST_PERSON, claim2, lifePolicy);

        // same object -> returns true
        Assertions.assertEquals(addClaimFirstCommand, addClaimFirstCommand);

        // same values -> returns true
        AddClaimCommand addClaimFirstCommandCopy = new AddClaimCommand(INDEX_FIRST_PERSON, claim1, healthPolicy);
        Assertions.assertEquals(addClaimFirstCommand, addClaimFirstCommandCopy);

        // different types -> returns false
        Assertions.assertFalse(addClaimFirstCommand.equals(5));

        // null -> returns false
        Assertions.assertFalse(addClaimFirstCommand.equals(null));

        // different claim -> returns false
        Assertions.assertFalse(addClaimFirstCommand.equals(addClaimSecondCommand));
    }

    private Person createPersonWithPolicy() {
        // initialize a person with a health policy (without claims)
        HealthPolicy policy = new HealthPolicy();
        return new PersonBuilder().withPolicy(policy).build();
    }


    private Person createPersonWithPolicyAndClaim() {
        // initialize a person with a health policy and a claim
        HealthPolicy policy = new HealthPolicy();
        Claim claim = new Claim(ClaimStatus.PENDING, "Surgery");

        policy.getClaimSet().add(claim);

        return new PersonBuilder().withPolicy(policy).build();
    }

}
