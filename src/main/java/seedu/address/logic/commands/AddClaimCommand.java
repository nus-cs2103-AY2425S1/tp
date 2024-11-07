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

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToEdit = lastShownList.get(index.getZeroBased());
        Set<Policy> policySet = clientToEdit.getPolicies();

        // find the policy based on the policyType
        Policy policy = policySet.stream()
                .filter(p -> p.getType().equals(policyType))
                .findFirst()
                .orElseThrow(() -> new CommandException(MESSAGE_POLICY_NOT_FOUND));

        Policy updatedPolicy;
        try {
            updatedPolicy = policy.addClaim(claim); // returns a new Policy
        } catch (DuplicateClaimException e) {
            throw new CommandException(MESSAGE_CLAIM_EXISTS);
        }

        // create new policy set with the updated policy to preserve immutability
        PolicySet updatedPolicySet = new PolicySet(policySet);
        updatedPolicySet.replace(updatedPolicy);

        // create a new client with the updated policy set
        Client editedClient = new Client(clientToEdit.getName(), clientToEdit.getPhone(),
                clientToEdit.getEmail(), clientToEdit.getAddress(), clientToEdit.getTags(), updatedPolicySet);

        model.setClient(clientToEdit, editedClient);
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_CLIENTS);
        String successMessage = formatSuccessMessage(clientToEdit, claim);
        return new CommandResult(successMessage);

    }

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
    private String formatSuccessMessage(Client client, Claim claim) {
        return String.format(MESSAGE_ADD_CLAIM_SUCCESS, policyType, client.getName(), claim.getStatus(),
                claim.getClaimDescription());
    }
}
