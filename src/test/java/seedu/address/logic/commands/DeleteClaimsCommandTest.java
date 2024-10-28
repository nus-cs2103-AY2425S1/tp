package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLAIM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimList;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;

public class DeleteClaimsCommandTest {

    private Model model;
    private final PolicyType validPolicyType = PolicyType.HEALTH;
    private final Index validClaimIndex = Index.fromOneBased(1);

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        // set an out-of-bounds person index
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteClaimsCommand deleteClaimsCommand = new DeleteClaimsCommand(outOfBoundsIndex, validPolicyType,
                validClaimIndex);

        assertCommandFailure(deleteClaimsCommand, model, DeleteClaimsCommand.MESSAGE_INVALID_PERSON_INDEX);
    }

    @Test
    public void execute_noPolicyOfType_throwsCommandException() {
        DeleteClaimsCommand deleteClaimsCommand = new DeleteClaimsCommand(INDEX_FIRST_PERSON, PolicyType.LIFE,
                validClaimIndex);
        assertCommandFailure(deleteClaimsCommand, model, String.format(DeleteClaimsCommand.MESSAGE_NO_POLICY_OF_TYPE,
                PolicyType.LIFE, model.getFilteredPersonList().get(0).getName()));
    }

    @Test
    public void execute_claimIndexOutOfBounds_throwsCommandException() {
        DeleteClaimsCommand deleteClaimsCommand = new DeleteClaimsCommand(INDEX_SECOND_PERSON, validPolicyType,
                validClaimIndex);
        assertCommandFailure(deleteClaimsCommand, model, DeleteClaimsCommand.MESSAGE_NO_CLAIM_FOUND);
    }

    @Test
    public void execute_validClaimDeletion_updatesModelAndReturnsSuccessMessage() {
        // create a person with a health policy and a claim
        Person personWithClaim = createPersonWithHealthPolicyAndClaim(
                "John Doe", "98765432", "john@example.com", "123 Main St");

        // replace the person in the model with the person that includes a claim
        model.setPerson(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()), personWithClaim);

        DeleteClaimsCommand deleteClaimsCommand = new DeleteClaimsCommand(INDEX_SECOND_PERSON, PolicyType.HEALTH,
                INDEX_FIRST_CLAIM);

        String expectedMessage = String.format(DeleteClaimsCommand.MESSAGE_DELETE_CLAIM_SUCCESS, PolicyType.HEALTH,
                personWithClaim.getName(), ClaimStatus.PENDING, "Surgery claim");

        assertCommandSuccess(deleteClaimsCommand, model, expectedMessage, model);
    }



    @Test
    public void equals() {
        DeleteClaimsCommand deleteClaimCommand1 = new DeleteClaimsCommand(INDEX_FIRST_PERSON, validPolicyType,
                validClaimIndex);
        DeleteClaimsCommand deleteClaimCommand2 = new DeleteClaimsCommand(INDEX_FIRST_PERSON, validPolicyType,
                validClaimIndex);

        // same object -> returns true
        assert(deleteClaimCommand1.equals(deleteClaimCommand1));

        // same values -> returns true
        assert(deleteClaimCommand1.equals(deleteClaimCommand2));

        // different index -> returns false
        DeleteClaimsCommand deleteClaimDifferentIndex = new DeleteClaimsCommand(INDEX_SECOND_PERSON, validPolicyType,
                validClaimIndex);
        assert(!deleteClaimCommand1.equals(deleteClaimDifferentIndex));

        // different policy type -> returns false
        DeleteClaimsCommand deleteClaimDifferentPolicy = new DeleteClaimsCommand(INDEX_FIRST_PERSON, PolicyType.LIFE,
                validClaimIndex);
        assert(!deleteClaimCommand1.equals(deleteClaimDifferentPolicy));

        // different claim index -> returns false
        DeleteClaimsCommand deleteClaimDifferentClaimIndex = new DeleteClaimsCommand(INDEX_FIRST_PERSON,
                validPolicyType, Index.fromOneBased(2));
        assert(!deleteClaimCommand1.equals(deleteClaimDifferentClaimIndex));
    }

    public static Person createPersonWithHealthPolicyAndClaim(String name, String phone, String email, String address) {
        Claim claim = new Claim(ClaimStatus.PENDING, "Surgery claim");

        HealthPolicy healthPolicy = new HealthPolicy(null, null, null,
                new ClaimList());
        healthPolicy.addClaim(claim);
        PolicySet policySet = new PolicySet();
        policySet.add(healthPolicy);

        return new Person(new Name(name), new Phone(phone), new Email(email),
                new Address(address), Set.of(), policySet);
    }


}
