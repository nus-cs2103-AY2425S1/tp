package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalPrudy;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLAIM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

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
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;

public class DeleteClaimsCommandTest {

    private Model model;
    private final PolicyType validPolicyType = PolicyType.HEALTH;
    private final Index validClaimIndex = Index.fromOneBased(1);

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPrudy(), new UserPrefs());
    }

    @Test
    public void execute_invalidClientIndex_throwsCommandException() {
        // set an out-of-bounds client index
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        DeleteClaimsCommand deleteClaimsCommand = new DeleteClaimsCommand(outOfBoundsIndex, validPolicyType,
                validClaimIndex);

        assertCommandFailure(deleteClaimsCommand, model, DeleteClaimsCommand.MESSAGE_INVALID_CLIENT_INDEX);
    }

    @Test
    public void execute_noPolicyOfType_throwsCommandException() {
        DeleteClaimsCommand deleteClaimsCommand = new DeleteClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.LIFE,
                validClaimIndex);
        assertCommandFailure(deleteClaimsCommand, model, String.format(DeleteClaimsCommand.MESSAGE_NO_POLICY_OF_TYPE,
                PolicyType.LIFE, model.getFilteredClientList().get(0).getName()));
    }

    @Test
    public void execute_claimIndexOutOfBounds_throwsCommandException() {
        DeleteClaimsCommand deleteClaimsCommand = new DeleteClaimsCommand(INDEX_SECOND_CLIENT, validPolicyType,
                validClaimIndex);
        assertCommandFailure(deleteClaimsCommand, model, DeleteClaimsCommand.MESSAGE_NO_CLAIM_FOUND);
    }

    @Test
    public void execute_validClaimDeletion_updatesModelAndReturnsSuccessMessage() {
        // create a client with a health policy and a claim
        Client clientWithClaim = createClientWithHealthPolicyAndClaim(
                "John Doe", "98765432", "john@example.com", "123 Main St");

        // replace the client in the model with the client that includes a claim
        model.setClient(model.getFilteredClientList().get(INDEX_SECOND_CLIENT.getZeroBased()), clientWithClaim);

        DeleteClaimsCommand deleteClaimsCommand = new DeleteClaimsCommand(INDEX_SECOND_CLIENT, PolicyType.HEALTH,
                INDEX_FIRST_CLAIM);

        String expectedMessage = String.format(DeleteClaimsCommand.MESSAGE_DELETE_CLAIM_SUCCESS, PolicyType.HEALTH,
                clientWithClaim.getName(), ClaimStatus.PENDING, "Surgery claim");

        assertCommandSuccess(deleteClaimsCommand, model, expectedMessage, model);
    }



    @Test
    public void equals() {
        DeleteClaimsCommand deleteClaimCommand1 = new DeleteClaimsCommand(INDEX_FIRST_CLIENT, validPolicyType,
                validClaimIndex);
        DeleteClaimsCommand deleteClaimCommand2 = new DeleteClaimsCommand(INDEX_FIRST_CLIENT, validPolicyType,
                validClaimIndex);

        // same object -> returns true
        assert(deleteClaimCommand1.equals(deleteClaimCommand1));

        // same values -> returns true
        assert(deleteClaimCommand1.equals(deleteClaimCommand2));

        // different index -> returns false
        DeleteClaimsCommand deleteClaimDifferentIndex = new DeleteClaimsCommand(INDEX_SECOND_CLIENT, validPolicyType,
                validClaimIndex);
        assert(!deleteClaimCommand1.equals(deleteClaimDifferentIndex));

        // different policy type -> returns false
        DeleteClaimsCommand deleteClaimDifferentPolicy = new DeleteClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.LIFE,
                validClaimIndex);
        assert(!deleteClaimCommand1.equals(deleteClaimDifferentPolicy));

        // different claim index -> returns false
        DeleteClaimsCommand deleteClaimDifferentClaimIndex = new DeleteClaimsCommand(INDEX_FIRST_CLIENT,
                validPolicyType, Index.fromOneBased(2));
        assert(!deleteClaimCommand1.equals(deleteClaimDifferentClaimIndex));
    }

    public static Client createClientWithHealthPolicyAndClaim(String name, String phone, String email, String address) {
        Claim claim = new Claim(ClaimStatus.PENDING, "Surgery claim");

        HealthPolicy healthPolicy = new HealthPolicy(null, null, null,
                new ClaimList());
        PolicySet policySet = new PolicySet();
        policySet.add(healthPolicy.addClaim(claim));

        return new Client(new Name(name), new Phone(phone), new Email(email),
                new Address(address), Set.of(), policySet);
    }


}
