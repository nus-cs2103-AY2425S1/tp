package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.getTypicalPrudy;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimList;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.client.Client;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.PolicyType;
import seedu.address.testutil.ClientBuilder;

public class AddClaimCommandTest {

    private Model model;
    private final Claim validClaim = new Claim(ClaimStatus.PENDING, "Surgery claim");
    private final PolicyType validPolicyType = PolicyType.HEALTH;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddClaimCommand(null, validClaim, validPolicyType));
        assertThrows(NullPointerException.class, () -> new AddClaimCommand(INDEX_FIRST_CLIENT, null, validPolicyType));
        assertThrows(NullPointerException.class, () -> new AddClaimCommand(INDEX_FIRST_CLIENT, validClaim, null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        AddClaimCommand command = new AddClaimCommand(INDEX_FIRST_CLIENT, validClaim, validPolicyType);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        setupModelWithTypicalData();
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        AddClaimCommand command = new AddClaimCommand(outOfBoundIndex, validClaim, validPolicyType);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addClaim_success() {
        Client clientWithPolicy = createClientWithPolicy();
        model.addClient(clientWithPolicy);

        AddClaimCommand command = new AddClaimCommand(INDEX_FIRST_CLIENT, validClaim, validPolicyType);
        String expectedMessage = getSuccessMessage(clientWithPolicy, validClaim);
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_duplicateClaim_throwsCommandException() {
        Client clientWithPolicyAndClaim = createClientWithPolicyAndClaim();
        model.addClient(clientWithPolicyAndClaim);

        AddClaimCommand command = new AddClaimCommand(INDEX_FIRST_CLIENT, validClaim, validPolicyType);
        assertCommandFailure(command, model, AddClaimCommand.MESSAGE_CLAIM_EXISTS);
    }

    @Test
    public void equals() {
        Claim claim1 = new Claim(ClaimStatus.PENDING, "Surgery claim");
        Claim claim2 = new Claim(ClaimStatus.APPROVED, "Dental claim");

        AddClaimCommand command1 = new AddClaimCommand(INDEX_FIRST_CLIENT, claim1, PolicyType.HEALTH);
        AddClaimCommand command2 = new AddClaimCommand(INDEX_FIRST_CLIENT, claim1, PolicyType.HEALTH);
        AddClaimCommand commandDifferentClaim = new AddClaimCommand(INDEX_FIRST_CLIENT, claim2, PolicyType.HEALTH);
        AddClaimCommand commandDifferentPolicy = new AddClaimCommand(INDEX_FIRST_CLIENT, claim1, PolicyType.LIFE);

        Assertions.assertEquals(command1, command1); // same object
        Assertions.assertEquals(command1, command2); // same values
        Assertions.assertNotEquals(command1, commandDifferentClaim); // different claim
        Assertions.assertNotEquals(command1, commandDifferentPolicy); // different policy
        Assertions.assertNotEquals(command1, null); // null
        Assertions.assertNotEquals(command1, new ClearCommand()); // different type
    }

    private Client createClientWithPolicy() {
        HealthPolicy policy = new HealthPolicy();
        return new ClientBuilder().withPolicy(policy).build();
    }

    private Client createClientWithPolicyAndClaim() {
        ClaimList claimList = new ClaimList();
        claimList.add(validClaim);
        HealthPolicy policy = new HealthPolicy(null, null, null, claimList);
        return new ClientBuilder().withPolicy(policy).build();
    }

    private String getSuccessMessage(Client client, Claim claim) {
        return String.format(AddClaimCommand.MESSAGE_ADD_CLAIM_SUCCESS, validPolicyType,
                client.getName(), claim.getStatus(), claim.getClaimDescription());
    }

    private void setupModelWithTypicalData() {
        model = new ModelManager(getTypicalPrudy(), new UserPrefs());
    }
}
