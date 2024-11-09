package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

public class DeleteClaimCommandTest {

    private Model model;
    private final PolicyType validPolicyType = PolicyType.HEALTH;
    private final Index validClaimIndex = Index.fromOneBased(1);

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPrudy(), new UserPrefs());
    }

    @Test
    public void execute_invalidClientIndex_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        DeleteClaimCommand deleteClaimsCommand = createDeleteClaimsCommand(outOfBoundsIndex, validPolicyType,
                validClaimIndex);

        assertCommandFailure(deleteClaimsCommand, model, DeleteClaimCommand.MESSAGE_INVALID_CLIENT_INDEX);
    }

    @Test
    public void execute_noPolicyOfType_throwsCommandException() {
        DeleteClaimCommand deleteClaimCommand = createDeleteClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.LIFE,
                validClaimIndex);
        assertCommandFailure(deleteClaimCommand, model, getNoPolicyOfTypeMessage(PolicyType.LIFE,
                model.getFilteredClientList().get(0)));
    }

    @Test
    public void execute_claimIndexOutOfBounds_throwsCommandException() {
        DeleteClaimCommand deleteClaimCommand = createDeleteClaimsCommand(INDEX_SECOND_CLIENT, validPolicyType,
                validClaimIndex);
        assertCommandFailure(deleteClaimCommand, model, DeleteClaimCommand.MESSAGE_NO_CLAIM_FOUND);
    }

    @Test
    public void execute_validClaimDeletion_updatesModelAndReturnsSuccessMessage() {
        Client clientWithClaim = createClientWithHealthPolicyAndClaim("John Doe", "98765432",
                "john@example.com", "123 Main St");
        model.setClient(model.getFilteredClientList().get(INDEX_SECOND_CLIENT.getZeroBased()), clientWithClaim);

        DeleteClaimCommand deleteClaimCommand = createDeleteClaimsCommand(INDEX_SECOND_CLIENT, PolicyType.HEALTH,
                INDEX_FIRST_CLAIM);

        String expectedMessage = getDeleteClaimSuccessMessage(clientWithClaim, PolicyType.HEALTH, ClaimStatus.PENDING,
                "Surgery claim");

        assertCommandSuccess(deleteClaimCommand, model, expectedMessage, model);
    }

    @Test
    public void equals() {
        DeleteClaimCommand command1 = createDeleteClaimsCommand(INDEX_FIRST_CLIENT, validPolicyType, validClaimIndex);
        DeleteClaimCommand command2 = createDeleteClaimsCommand(INDEX_FIRST_CLIENT, validPolicyType, validClaimIndex);
        DeleteClaimCommand commandDifferentIndex = createDeleteClaimsCommand(INDEX_SECOND_CLIENT, validPolicyType,
                validClaimIndex);
        DeleteClaimCommand commandDifferentPolicy = createDeleteClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.LIFE,
                validClaimIndex);
        DeleteClaimCommand commandDifferentClaimIndex = createDeleteClaimsCommand(INDEX_FIRST_CLIENT, validPolicyType,
                Index.fromOneBased(2));

        assertEquals(command1, command1); // same object -> returns true
        assertEquals(command1, command2); // same values -> returns true
        assertNotEquals(command1, commandDifferentIndex); // different index -> returns false
        assertNotEquals(command1, commandDifferentPolicy); // different policy type -> returns false
        assertNotEquals(command1, commandDifferentClaimIndex); // different claim index -> returns false
        assertNotEquals(null, command1); // null -> returns false
        assertNotEquals(command1, new ClearCommand()); // different type -> returns false
    }

    private DeleteClaimCommand createDeleteClaimsCommand(Index clientIndex, PolicyType policyType, Index claimIndex) {
        return new DeleteClaimCommand(clientIndex, policyType, claimIndex);
    }

    private String getNoPolicyOfTypeMessage(PolicyType policyType, Client client) {
        return String.format(DeleteClaimCommand.MESSAGE_NO_POLICY_OF_TYPE, policyType, client.getName());
    }

    private String getDeleteClaimSuccessMessage(Client client, PolicyType policyType, ClaimStatus claimStatus,
                                                String claimDescription) {
        return String.format(DeleteClaimCommand.MESSAGE_DELETE_CLAIM_SUCCESS, policyType, client.getName(),
                claimStatus, claimDescription);
    }

    private Client createClientWithHealthPolicyAndClaim(String name, String phone, String email, String address) {
        Claim claim = new Claim(ClaimStatus.PENDING, "Surgery claim");

        HealthPolicy healthPolicy = new HealthPolicy(null, null, null,
                new ClaimList());
        PolicySet policySet = new PolicySet();
        policySet.add(healthPolicy.addClaim(claim));

        return new Client(new Name(name), new Phone(phone), new Email(email), new Address(address), Set.of(),
                policySet);
    }
}
