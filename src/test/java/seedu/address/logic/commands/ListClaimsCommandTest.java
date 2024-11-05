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

/**
 * Contains test cases for the ListClaimsCommand class.
 */
public class ListClaimsCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // reset model before each test to ensure isolation between tests
        model = new ModelManager(getTypicalPrudy(), new UserPrefs());
    }

    private ReadOnlyPrudy getTypicalPrudyWithClaims() {
        // create a new model to prevent side effects across tests
        Model tempModel = new ModelManager(getTypicalPrudy(), new UserPrefs());
        Client client = tempModel.getFilteredClientList().get(INDEX_SECOND_CLIENT.getZeroBased());
        PolicySet policies = new PolicySet();

        Policy healthPolicy = new HealthPolicy();
        policies.add(healthPolicy.addClaim(new Claim(ClaimStatus.PENDING, "Hospitalization")));

        Client updatedClient = new Client(client.getName(), client.getPhone(), client.getEmail(),
                client.getAddress(), client.getTags(), policies);
        tempModel.setClient(client, updatedClient);

        return tempModel.getPrudy();
    }

    private ReadOnlyPrudy getTypicalPrudyWithNoClaims() {
        // create a new model to prevent side effects across tests
        Model tempModel = new ModelManager(getTypicalPrudy(), new UserPrefs());
        Client client = tempModel.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());
        PolicySet policies = new PolicySet();

        // add health policy but no claims
        Policy healthPolicy = new HealthPolicy();
        policies.add(healthPolicy);

        Client updatedClient = new Client(client.getName(), client.getPhone(), client.getEmail(),
                client.getAddress(), client.getTags(), policies);
        tempModel.setClient(client, updatedClient);

        return tempModel.getPrudy();
    }

    @Test
    public void execute_validIndexAndPolicyTypeWithClaims_success() {

        Model modelWithClaims = new ModelManager(getTypicalPrudyWithClaims(), new UserPrefs());

        ListClaimsCommand command = new ListClaimsCommand(INDEX_SECOND_CLIENT, PolicyType.HEALTH);
        Client client = modelWithClaims.getFilteredClientList().get(INDEX_SECOND_CLIENT.getZeroBased());

        String expectedMessage = String.format(ListClaimsCommand.MESSAGE_LIST_CLAIMS_SUCCESS,
                PolicyType.HEALTH, client.getName(), "1. Claim Status: Pending | Claim Description: Hospitalization");

        assertCommandSuccess(command, modelWithClaims, expectedMessage, modelWithClaims);
    }

    @Test
    public void execute_validIndexAndPolicyTypeNoClaims_success() {
        // model with a client who has no claims for the specified policy type
        Model modelWithNoClaims = new ModelManager(getTypicalPrudyWithNoClaims(), new UserPrefs());

        ListClaimsCommand command = new ListClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.HEALTH);
        Client client = modelWithNoClaims.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());

        String expectedMessage = String.format(ListClaimsCommand.MESSAGE_NO_CLAIMS, PolicyType.HEALTH,
                client.getName());

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
        // no policy of the specified type
        ListClaimsCommand command = new ListClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.HEALTH);

        assertCommandFailure(command, model, String.format(ListClaimsCommand.MESSAGE_NO_POLICY_OF_TYPE,
                PolicyType.HEALTH, model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased()).getName()));
    }

    @Test
    public void equals() {
        ListClaimsCommand command1 = new ListClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.HEALTH);
        ListClaimsCommand command2 = new ListClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.HEALTH);
        ListClaimsCommand command3 = new ListClaimsCommand(INDEX_SECOND_CLIENT, PolicyType.HEALTH);
        ListClaimsCommand command4 = new ListClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.LIFE);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command2));

        // different index -> returns false
        assertFalse(command1.equals(command3));

        // different policy type -> returns false
        assertFalse(command1.equals(command4));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different type -> returns false
        assertFalse(command1.equals(new ClearCommand()));
    }

}
