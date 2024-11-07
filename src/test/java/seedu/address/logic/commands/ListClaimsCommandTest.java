package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalPrudy;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyPrudy;
import seedu.address.model.UserPrefs;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.client.Client;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;

public class ListClaimsCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPrudy(), new UserPrefs());
    }

    private ReadOnlyPrudy getModelWithClaims() {
        return setUpPrudyWithClaims(INDEX_SECOND_CLIENT, PolicyType.HEALTH, new Claim(ClaimStatus.PENDING, "Hospitalization"));
    }

    private ReadOnlyPrudy getModelWithNoClaims() {
        return setUpPrudyWithClaims(INDEX_FIRST_CLIENT, PolicyType.HEALTH, null);
    }

    private ReadOnlyPrudy setUpPrudyWithClaims(Index clientIndex, PolicyType policyType, Claim claim) {
        Model tempModel = new ModelManager(getTypicalPrudy(), new UserPrefs());
        Client client = tempModel.getFilteredClientList().get(clientIndex.getZeroBased());
        PolicySet policies = new PolicySet();

        Policy policy = new HealthPolicy();
        if (claim != null) {
            policy = policy.addClaim(claim);
        }
        policies.add(policy);

        Client updatedClient = new Client(client.getName(), client.getPhone(), client.getEmail(),
                client.getAddress(), client.getTags(), policies);
        tempModel.setClient(client, updatedClient);

        return tempModel.getPrudy();
    }

    @Test
    public void execute_validIndexAndPolicyTypeWithClaims_success() {
        Model modelWithClaims = new ModelManager(getModelWithClaims(), new UserPrefs());
        ListClaimsCommand command = new ListClaimsCommand(INDEX_SECOND_CLIENT, PolicyType.HEALTH);
        Client client = modelWithClaims.getFilteredClientList().get(INDEX_SECOND_CLIENT.getZeroBased());

        String expectedMessage = getExpectedMessageWithClaims(client, PolicyType.HEALTH,
                "1. Claim Status: Pending | Claim Description: Hospitalization");
        assertCommandSuccess(command, modelWithClaims, expectedMessage, modelWithClaims);
    }

    @Test
    public void execute_validIndexAndPolicyTypeNoClaims_success() {
        Model modelWithNoClaims = new ModelManager(getModelWithNoClaims(), new UserPrefs());
        ListClaimsCommand command = new ListClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.HEALTH);
        Client client = modelWithNoClaims.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());

        String expectedMessage = getExpectedMessageNoClaims(client, PolicyType.HEALTH);
        assertCommandSuccess(command, modelWithNoClaims, expectedMessage, modelWithNoClaims);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        ListClaimsCommand command = new ListClaimsCommand(outOfBoundIndex, PolicyType.HEALTH);
        assertCommandFailure(command, model, ListClaimsCommand.MESSAGE_INVALID_CLIENT_INDEX);
    }

    @Test
    public void execute_policyTypeNotFound_throwsCommandException() {
        ListClaimsCommand command = new ListClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.HEALTH);
        String expectedMessage = String.format(ListClaimsCommand.MESSAGE_NO_POLICY_OF_TYPE, PolicyType.HEALTH,
                model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased()).getName());
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() {
        ListClaimsCommand command1 = new ListClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.HEALTH);
        ListClaimsCommand command2 = new ListClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.HEALTH);
        ListClaimsCommand command3 = new ListClaimsCommand(INDEX_SECOND_CLIENT, PolicyType.HEALTH);
        ListClaimsCommand command4 = new ListClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.LIFE);

        assertTrue(command1.equals(command1)); // same object -> returns true
        assertTrue(command1.equals(command2)); // same values -> returns true
        assertFalse(command1.equals(command3)); // different index -> returns false
        assertFalse(command1.equals(command4)); // different policy type -> returns false
        assertFalse(command1.equals(null)); // null -> returns false
        assertFalse(command1.equals(new ClearCommand())); // different type -> returns false
    }

    /**
     * Generates the expected success message for a client with claims.
     */
    private String getExpectedMessageWithClaims(Client client, PolicyType policyType, String claimsDetails) {
        return String.format(ListClaimsCommand.MESSAGE_LIST_CLAIMS_SUCCESS, policyType, client.getName(), claimsDetails);
    }

    /**
     * Generates the expected success message for a client with no claims.
     */
    private String getExpectedMessageNoClaims(Client client, PolicyType policyType) {
        return String.format(ListClaimsCommand.MESSAGE_NO_CLAIMS, policyType, client.getName());
    }
}
