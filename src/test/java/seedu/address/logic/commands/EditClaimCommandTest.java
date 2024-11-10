package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.getTypicalPrudy;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLAIM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.EditClaimCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimList;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.claim.EditClaimDescriptor;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.policy.HealthPolicy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;
import seedu.address.testutil.EditClaimDescriptorBuilder;

public class EditClaimCommandTest {

    private Model model;
    private final PolicyType validPolicyType = PolicyType.HEALTH;
    private final Index validClaimIndex = Index.fromOneBased(1);
    private final EditClaimCommandParser parser = new EditClaimCommandParser();

    private final EditClaimDescriptor descriptor = new EditClaimDescriptorBuilder()
            .withStatus(ClaimStatus.PENDING)
            .withDescription("Surgery claim")
            .build();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPrudy(), new UserPrefs());

    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new EditClaimCommand(null, validPolicyType, validClaimIndex, descriptor));
    }

    @Test
    public void constructor_nullPolicyType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new EditClaimCommand(INDEX_FIRST_CLIENT, null, validClaimIndex, descriptor));
    }

    @Test
    public void constructor_nullClaimIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new EditClaimCommand(INDEX_FIRST_CLIENT, validPolicyType, null, descriptor));
    }

    @Test
    public void execute_invalidClientIndex_throwsCommandException() {
        // Test with an out-of-bounds index for the client list
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        EditClaimCommand editClaimCommand = new EditClaimCommand(outOfBoundsIndex, validPolicyType,
                validClaimIndex, descriptor);
        assertCommandFailure(editClaimCommand, model, EditClaimCommand.MESSAGE_INVALID_CLIENT_INDEX);
    }

    @Test
    public void parse_validArgs_returnsEditClaimCommand() throws Exception {
        // Example user input for editing a claim
        String userInput = "1 " + PREFIX_POLICY_TYPE + "health " + PREFIX_CLAIM_INDEX + "1 "
                + PREFIX_CLAIM_STATUS + "approved " + PREFIX_CLAIM_DESC + "Updated claim details";

        // Expected command to be returned by the parser
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        editClaimDescriptor.setStatus(ClaimStatus.APPROVED);
        editClaimDescriptor.setDescription("Updated claim details");

        EditClaimCommand expectedCommand = new EditClaimCommand(
                INDEX_FIRST_CLIENT, PolicyType.HEALTH, INDEX_FIRST_CLAIM, editClaimDescriptor);

        // Parse the user input and get the actual command
        EditClaimCommand actualCommand = parser.parse(userInput);

        // Assert that the actual command matches the expected command
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void execute_validClaimEdit_updatesModelAndReturnsSuccessMessage() {
        Client clientWithClaim = createClientWithHealthPolicyAndClaim(
                "John Doe", "98765432", "john@example.com", "123 Main St");

        // replace the client in the model with the client that includes a claim
        model.setClient(model.getFilteredClientList().get(INDEX_SECOND_CLIENT.getZeroBased()), clientWithClaim);

        EditClaimCommand editClaimsCommand = new EditClaimCommand(INDEX_SECOND_CLIENT, PolicyType.HEALTH,
                INDEX_FIRST_CLAIM, descriptor);

        String expectedMessage = String.format(EditClaimCommand.MESSAGE_EDIT_CLAIM_SUCCESS, PolicyType.HEALTH,
                clientWithClaim.getName(), ClaimStatus.PENDING, "Surgery claim");

        assertCommandSuccess(editClaimsCommand, model, expectedMessage, model);
    }


    @Test
    public void equals() {
        EditClaimCommand editClaimCommand1 = new EditClaimCommand(INDEX_FIRST_CLIENT, validPolicyType,
                validClaimIndex, descriptor);
        EditClaimCommand editClaimCommand2 = new EditClaimCommand(INDEX_FIRST_CLIENT, validPolicyType,
                validClaimIndex, descriptor);

        // same object -> returns true
        assert(editClaimCommand1.equals(editClaimCommand1));

        // same values -> returns true
        assert(editClaimCommand1.equals(editClaimCommand2));

        // different index -> returns false
        EditClaimCommand editClaimDifferentIndex = new EditClaimCommand(Index.fromOneBased(2),
                validPolicyType, validClaimIndex, descriptor);
        assert(!editClaimCommand1.equals(editClaimDifferentIndex));

        // different policy type -> returns false
        EditClaimCommand editClaimDifferentPolicy = new EditClaimCommand(INDEX_FIRST_CLIENT, PolicyType.LIFE,
                validClaimIndex, descriptor);
        assert(!editClaimCommand1.equals(editClaimDifferentPolicy));

        // different claim index -> returns false
        EditClaimCommand editClaimDifferentClaimIndex = new EditClaimCommand(INDEX_FIRST_CLIENT, validPolicyType,
                Index.fromOneBased(2), descriptor);
        assert(!editClaimCommand1.equals(editClaimDifferentClaimIndex));
    }

    public static Client createClientWithHealthPolicyAndClaim(String name, String phone, String email, String address) {
        Claim claim = new Claim(ClaimStatus.PENDING, "Surgery claim needed");

        HealthPolicy healthPolicy = new HealthPolicy(null, null, null,
                new ClaimList());
        PolicySet policySet = new PolicySet();
        policySet.add(healthPolicy.addClaim(claim));

        return new Client(new Name(name), new Phone(phone), new Email(email),
                new Address(address), Set.of(), policySet);
    }
}
