package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLIENTS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;

/**
 * Deletes Policy from an existing client in Prudy.
 */
public class DeletePoliciesCommand extends Command {
    public static final String COMMAND_WORD = "delete-policy";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Policy deleted:\n%2$s";
    public static final String MESSAGE_DUPLICATES = "Cannot delete same policies.";
    public static final String MESSAGE_POLICY_NOT_FOUND = "Policy not found.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified policy from the client identified "
            + "by the index number used in the last client listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_POLICY_TYPE + "POLICY_TYPE...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_POLICY_TYPE + "life";
    public static final String MESSAGE_SUCCESS = "Policies Left: %1$s";
    private final Index index;
    private final Set<PolicyType> policyTypes;

    /**
     * Creates a DeletePoliciesCommand to delete the specified {@code PolicyMap} from the client.
     *
     * @param index of the client in the filtered client list to delete policy.
     * @param policyTypes the set of policies to be deleted.
     */
    public DeletePoliciesCommand(Index index, Set<PolicyType> policyTypes) {
        requireAllNonNull(index, policyTypes);

        this.index = index;
        this.policyTypes = policyTypes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToEdit = lastShownList.get(index.getZeroBased());
        PolicySet editedPolicy = removePolicies(clientToEdit.getPolicies());

        Client editedClient = new Client(clientToEdit.getName(), clientToEdit.getPhone(), clientToEdit.getEmail(),
                clientToEdit.getAddress(), clientToEdit.getTags(), editedPolicy);

        model.setClient(clientToEdit, editedClient);
        model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatPolicies(editedPolicy)));
    }

    private PolicySet removePolicies(Set<Policy> policies) throws CommandException {
        PolicySet updatedPolicies = new PolicySet(policies);
        for (PolicyType type : policyTypes) {
            if (!updatedPolicies.remove(type)) {
                throw new CommandException(MESSAGE_POLICY_NOT_FOUND);
            }
        }
        return updatedPolicies;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePoliciesCommand)) {
            return false;
        }

        DeletePoliciesCommand dpc = (DeletePoliciesCommand) other;
        return index.equals(dpc.index)
                && policyTypes.equals(dpc.policyTypes);
    }
}
