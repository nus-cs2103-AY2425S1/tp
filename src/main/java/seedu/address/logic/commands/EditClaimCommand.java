package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.ClaimList;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.claim.EditClaimDescriptor;
import seedu.address.model.client.Client;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;

/**
 * Represents a command to edit a claim for a specific policy of a client in Prudy.
 */
public class EditClaimCommand extends Command {

    public static final String COMMAND_WORD = "edit-claim";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a claim from the client identified by the index number used in the displayed client list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_POLICY_TYPE + "POLICY_TYPE "
            + PREFIX_CLAIM_INDEX + "CLAIM_INDEX (must be a positive integer) "
            + "[" + PREFIX_CLAIM_STATUS + "NEW_STATUS] "
            + "[" + PREFIX_CLAIM_DESC + "NEW_DESCRIPTION]\n"
            + "Note: Use the 'list-claims' command to find the appropriate claim index "
            + "for the specified policy type.\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_POLICY_TYPE + "health "
            + PREFIX_CLAIM_INDEX + "1 "
            + PREFIX_CLAIM_STATUS + "approved "
            + PREFIX_CLAIM_DESC + "Updated claim details "
            + "(The last 2 fields are optional but one of them needs to be edited at all times)\n ";

    public static final String MESSAGE_NOT_EDITED = "At least one field needs to be updated";
    public static final String MESSAGE_EDIT_CLAIM_SUCCESS = "Claim edited for policy type '%1$s' of client: %2$s\n\n"
            + "Updated Claim Details:\nStatus: %3$s | Description: %4$s";
    public static final String MESSAGE_NO_CLAIM_FOUND = "No claim found at the specified index to edit.";
    public static final String MESSAGE_INVALID_CLIENT_INDEX = "The client index provided is invalid.";
    public static final String MESSAGE_NO_POLICY_OF_TYPE = "No policy of type '%1$s' found for client: %2$s";
    public static final String MESSAGE_DUPLICATE_CLAIM = "This claim already exists in the policy.";
    public static final String MESSAGE_NOT_CHANGED = "This claim has not been changed";

    private final Index clientIndex;
    private final PolicyType policyType;
    private final Index claimIndex;
    private final EditClaimDescriptor editClaimDescriptor;

    /**
     * Creates an EditClaimCommand to edit the specified claim for a client.
     *
     * @param clientIndex       The index of the client whose claim is to be edited.
     * @param policyType        The type of policy associated with the claim.
     * @param claimIndex        The index of the claim to be edited.
     * @param editClaimDescriptor The descriptor containing the details to edit the claim.
     */
    public EditClaimCommand(Index clientIndex, PolicyType policyType, Index claimIndex,
                            EditClaimDescriptor editClaimDescriptor) {
        requireAllNonNull(clientIndex, policyType, claimIndex, editClaimDescriptor);
        this.clientIndex = clientIndex;
        this.policyType = policyType;
        this.claimIndex = claimIndex;
        this.editClaimDescriptor = editClaimDescriptor;
    }

    /**
     * Executes the command to edit a claim in the model.
     *
     * @param model The model where the command will be executed.
     * @return A CommandResult indicating the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Client clientToEdit = getClientFromList(model, clientIndex);
        Set<Policy> policySet = clientToEdit.getPolicies();

        Policy policy = getPolicyFromClient(clientToEdit, policyType);

        Claim oldClaim = getOldClaim(policy, claimIndex);
        Claim editedClaim = createEditedClaim(oldClaim, editClaimDescriptor);

        PolicySet updatedPolicySet = new PolicySet(policySet);

        Policy editedPolicy = createEditedPolicy(policy, oldClaim, editedClaim);
        updatedPolicySet.replace(editedPolicy);

        Client editedClient = new Client(clientToEdit.getName(), clientToEdit.getPhone(), clientToEdit.getEmail(),
                clientToEdit.getAddress(), clientToEdit.getTags(), updatedPolicySet);

        model.setClient(clientToEdit, editedClient);

        return new CommandResult(String.format(MESSAGE_EDIT_CLAIM_SUCCESS, policyType, clientToEdit.getName(),
                editedClaim.getStatus(), editedClaim.getClaimDescription()));
    }

    /**
     * Retrieves a client from the model's filtered client list using the specified index.
     *
     * @param model       The model containing the list of clients.
     * @param clientIndex The index of the client to retrieve.
     * @return The client corresponding to the specified index.
     * @throws CommandException If the provided index is invalid.
     */
    private Client getClientFromList(Model model, Index clientIndex) throws CommandException {
        List<Client> lastShownList = model.getFilteredClientList();
        if (clientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_CLIENT_INDEX);
        }
        return lastShownList.get(clientIndex.getZeroBased());
    }

    /**
     * Retrieves the policy of the specified type from the client.
     *
     * @param client     The client whose policies are to be checked.
     * @param policyType The type of policy to look for.
     * @return The policy of the specified type.
     * @throws CommandException If no policy of the specified type is found.
     */
    private Policy getPolicyFromClient(Client client, PolicyType policyType) throws CommandException {
        return client.getPolicies().stream()
                .filter(x -> x.getType().equals(policyType))
                .findFirst()
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_NO_POLICY_OF_TYPE, policyType, client.getName())));
    }

    /**
     * Retrieves the old claim at the specified index from the given policy.
     *
     * @param policy     The policy containing the claims.
     * @param claimIndex The index of the claim to retrieve.
     * @return The claim at the specified index.
     * @throws CommandException If no claim is found at the specified index.
     */
    public Claim getOldClaim(Policy policy, Index claimIndex) throws CommandException {
        try {
            return policy.getClaimList().get(claimIndex.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_NO_CLAIM_FOUND);
        }
    }

    /**
     * Creates an edited policy with the updated claim.
     *
     * @param policy     The original policy.
     * @param oldClaim   The old claim that is being edited.
     * @param newClaim   The new claim to replace the old claim.
     * @return The edited policy with the updated claims.
     * @throws CommandException If the claim has not been changed or if there is a duplicate claim.
     */
    public Policy createEditedPolicy(Policy policy, Claim oldClaim, Claim newClaim) throws CommandException {
        ClaimList updatedClaims = new ClaimList(policy.getClaimList());

        if (newClaim.equals(oldClaim)) {
            throw new CommandException(MESSAGE_NOT_CHANGED);
        }

        for (Claim claim : updatedClaims) {
            if (claim.equals(newClaim) && !claim.equals(oldClaim)) {
                throw new CommandException(MESSAGE_DUPLICATE_CLAIM);
            }
        }

        int oldClaimIndex = updatedClaims.indexOf(oldClaim);
        if (oldClaimIndex != -1) {
            // Replace the old claim with the new claim
            updatedClaims.set(oldClaimIndex, newClaim);
        } else {
            throw new CommandException(MESSAGE_NO_CLAIM_FOUND);
        }

        return Policy.makePolicy(
                policy.getType(), policy.getPremiumAmount(), policy.getCoverageAmount(),
                policy.getExpiryDate(), updatedClaims);
    }

    /**
     * Creates a new claim with the updated details based on the given descriptor.
     *
     * @param claim    The original claim to be edited.
     * @param descriptor The descriptor containing the new claim details.
     * @return The newly created claim with updated details.
     */
    public Claim createEditedClaim(Claim claim, EditClaimDescriptor descriptor) {
        ClaimStatus updatedStatus = descriptor.getStatus().orElse(claim.getStatus());
        String updatedDesc = descriptor.getDescription().orElse(claim.getClaimDescription());

        return new Claim(updatedStatus, updatedDesc);
    }

    /**
     * Checks if this command is equal to another command.
     *
     * @param other The object to compare this command against.
     * @return boolean if it is the same object
     */
    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof EditClaimCommand
                && clientIndex.equals(((EditClaimCommand) other).clientIndex)
                && policyType.equals(((EditClaimCommand) other).policyType)
                && claimIndex.equals(((EditClaimCommand) other).claimIndex)
                && editClaimDescriptor.equals(((EditClaimCommand) other).editClaimDescriptor));
    }
}
