package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.claim.Claim;
import seedu.address.model.client.Client;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;
import seedu.address.model.policy.exceptions.DuplicateClaimException;

/**
 * Adds a claim to a client in Prudy.
 */
public class AddClaimCommand extends Command {

    public static final String COMMAND_WORD = "add-claim";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a claim to the client identified "
            + "by the index number used in the displayed client list. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_POLICY_TYPE + "POLICY_TYPE " + PREFIX_CLAIM_STATUS + "CLAIM_STATUS "
            + PREFIX_CLAIM_DESC + "CLAIM_DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_POLICY_TYPE + "health "
            + PREFIX_CLAIM_STATUS + "pending "
            + PREFIX_CLAIM_DESC + "stomach surgery";

    public static final String MESSAGE_ADD_CLAIM_SUCCESS = "Claim added for policy type "
            + "'%1$s' of client: %2$s\n\n"
            + "Added Claim Details:\nStatus: %3$s | Description: %4$s\n";
    public static final String MESSAGE_POLICY_NOT_FOUND = "The policy for the specified type was not found.";
    public static final String MESSAGE_CLAIM_EXISTS = "A similar claim already exists in the policy.";

    private final Index index;
    private final Claim claim;
    private final PolicyType policyType;

    /**
     * Constructs an {@code AddClaimCommand} to add the specified {@code Claim} to a client.
     *
     * @param index      The index of the client in the filtered client list to whom the claim will be added.
     * @param claim      The claim to add to the client.
     * @param policyType The type of the policy to which the claim will be added.
     */
    public AddClaimCommand(Index index, Claim claim, PolicyType policyType) {
        requireNonNull(index);
        requireNonNull(claim);
        requireNonNull(policyType);
        this.index = index;
        this.claim = claim;
        this.policyType = policyType;
    }

    /**
     * Executes the command to add a claim to the specified client and returns the result.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} containing the success message.
     * @throws CommandException if the client index is invalid, the policy is not found,
     *                          or a duplicate claim already exists.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();
        Client clientToEdit = getClientToEdit(lastShownList);

        Policy policy = findPolicy(clientToEdit.getPolicies());
        Policy updatedPolicy = addClaimToPolicy(policy);

        Client editedClient = createUpdatedClient(clientToEdit, updatedPolicy);
        model.setClient(clientToEdit, editedClient);
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_CLIENTS);

        String successMessage = formatSuccessMessage(clientToEdit, claim);
        return new CommandResult(successMessage);
    }

    /**
     * Retrieves the client at the specified index in the filtered client list.
     *
     * @param lastShownList The current list of clients being displayed.
     * @return The {@code Client} at the specified index.
     * @throws CommandException if the index is out of bounds.
     */
    private Client getClientToEdit(List<Client> lastShownList) throws CommandException {
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }
        return lastShownList.get(index.getZeroBased());
    }

    /**
     * Finds the policy within the client's policy set that matches the specified policy type.
     *
     * @param policySet The set of policies belonging to the client.
     * @return The matching {@code Policy} object.
     * @throws CommandException if no policy with the specified type is found.
     */
    private Policy findPolicy(Set<Policy> policySet) throws CommandException {
        return policySet.stream()
                .filter(p -> p.getType().equals(policyType))
                .findFirst()
                .orElseThrow(() -> new CommandException(MESSAGE_POLICY_NOT_FOUND));
    }

    /**
     * Attempts to add the claim to the specified policy and returns the updated policy.
     *
     * @param policy The policy to which the claim will be added.
     * @return A new {@code Policy} object containing the added claim.
     * @throws CommandException if a duplicate claim is detected in the policy.
     */
    private Policy addClaimToPolicy(Policy policy) throws CommandException {
        try {
            return policy.addClaim(claim); // returns a new Policy with the added claim
        } catch (DuplicateClaimException e) {
            throw new CommandException(MESSAGE_CLAIM_EXISTS);
        }
    }

    /**
     * Creates a new {@code Client} object with the updated policy set containing the new claim.
     *
     * @param clientToEdit The original client to be updated.
     * @param updatedPolicy The updated policy containing the new claim.
     * @return A new {@code Client} object with the updated policy set.
     */
    private Client createUpdatedClient(Client clientToEdit, Policy updatedPolicy) {
        // create new policy set with the updated policy to preserve immutability
        PolicySet updatedPolicySet = new PolicySet(clientToEdit.getPolicies());
        updatedPolicySet.replace(updatedPolicy);

        return new Client(clientToEdit.getName(), clientToEdit.getPhone(),
                clientToEdit.getEmail(), clientToEdit.getAddress(), clientToEdit.getTags(), updatedPolicySet);
    }

    /**
     * Formats the success message to be displayed after the claim is successfully added.
     *
     * @param client The client to whom the claim was added.
     * @param claim The claim that was added.
     * @return A formatted success message string.
     */
    private String formatSuccessMessage(Client client, Claim claim) {
        return String.format(MESSAGE_ADD_CLAIM_SUCCESS, policyType, client.getName(), claim.getStatus(),
                claim.getClaimDescription());
    }

    /**
     * Checks if another object is equal to this {@code AddClaimCommand}.
     *
     * @param other The other object to compare to.
     * @return True if both objects are equivalent; otherwise, false.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddClaimCommand)) {
            return false;
        }
        AddClaimCommand otherCommand = (AddClaimCommand) other;
        return index.equals(otherCommand.index)
                && policyType.equals(otherCommand.policyType)
                && claim.equals(otherCommand.claim);
    }
}
