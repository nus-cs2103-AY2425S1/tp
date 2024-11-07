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
    public void constructor_nullIndex_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new AddClaimCommand(null, validClaim, validPolicyType));
    }

    @Test
    public void constructor_nullClaim_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new AddClaimCommand(INDEX_FIRST_CLIENT, null, validPolicyType));
    }

    @Test
    public void constructor_nullPolicyType_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new AddClaimCommand(INDEX_FIRST_CLIENT, validClaim, null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        AddClaimCommand command = new AddClaimCommand(INDEX_FIRST_CLIENT, validClaim, validPolicyType);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalPrudy(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        AddClaimCommand addClaimCommand = new AddClaimCommand(outOfBoundIndex, validClaim, validPolicyType);
        assertCommandFailure(addClaimCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addClaim_success() {
        // create a client with an existing policy
        Client client = createClientWithPolicy();
        model.addClient(client);

        // add the Health Policy claim
        Claim claim = new Claim(ClaimStatus.PENDING, "Surgery");
        AddClaimCommand command = new AddClaimCommand(INDEX_FIRST_CLIENT, claim, client.getPolicies()
                .iterator().next().getType());

        // expected success message
        String expectedMessage = String.format(AddClaimCommand.MESSAGE_ADD_CLAIM_SUCCESS, PolicyType.HEALTH,
                client.getName(), ClaimStatus.PENDING, "Surgery");

        // ensure the command is successful
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_duplicateClaim_throwsCommandException() {
        // create a client with an existing policy and a claim
        Client client = createClientWithPolicyAndClaim();
        model.addClient(client);

        // attempt to add the same claim again
        Claim duplicateClaim = new Claim(validClaim.getStatus(), validClaim.getClaimDescription());
        AddClaimCommand command = new AddClaimCommand(INDEX_FIRST_CLIENT, duplicateClaim,
                client.getPolicies().iterator().next().getType());

        // expect failure due to the duplicate claim
        assertCommandFailure(command, model, AddClaimCommand.MESSAGE_CLAIM_EXISTS);
    }

    @Test
    public void equals() {
        Claim claim1 = new Claim(ClaimStatus.PENDING, "Surgery claim");
        Claim claim2 = new Claim(ClaimStatus.APPROVED, "Dental claim");
        PolicyType healthPolicy = PolicyType.HEALTH;
        PolicyType lifePolicy = PolicyType.LIFE;

        AddClaimCommand addClaimFirstCommand = new AddClaimCommand(INDEX_FIRST_CLIENT, claim1, healthPolicy);
        AddClaimCommand addClaimSecondCommand = new AddClaimCommand(INDEX_FIRST_CLIENT, claim2, lifePolicy);

        // same object -> returns true
        Assertions.assertEquals(addClaimFirstCommand, addClaimFirstCommand);

        // same values -> returns true
        AddClaimCommand addClaimFirstCommandCopy = new AddClaimCommand(INDEX_FIRST_CLIENT, claim1, healthPolicy);
        Assertions.assertEquals(addClaimFirstCommand, addClaimFirstCommandCopy);

        // different types -> returns false
        Assertions.assertFalse(addClaimFirstCommand.equals(5));

        // null -> returns false
        Assertions.assertFalse(addClaimFirstCommand.equals(null));

        // different claim -> returns false
        Assertions.assertFalse(addClaimFirstCommand.equals(addClaimSecondCommand));
    }

    private Client createClientWithPolicy() {
        // initialize a client with a health policy (without claims)
        HealthPolicy policy = new HealthPolicy();
        return new ClientBuilder().withPolicy(policy).build();
    }


    private Client createClientWithPolicyAndClaim() {
        // initialize a client with a health policy and a claim
        ClaimList claimList = new ClaimList();
        claimList.add(validClaim);
        HealthPolicy policy = new HealthPolicy(null, null, null, claimList);

        return new ClientBuilder().withPolicy(policy).build();
    }

}
