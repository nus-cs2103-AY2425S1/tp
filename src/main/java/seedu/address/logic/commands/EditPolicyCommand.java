package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_COVERAGE_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_PREMIUM_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.claim.ClaimList;
import seedu.address.model.client.Client;
import seedu.address.model.policy.CoverageAmount;
import seedu.address.model.policy.EditPolicyDescriptor;
import seedu.address.model.policy.ExpiryDate;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;
import seedu.address.model.policy.PremiumAmount;

/**
 * Edits an existing policy for a client in Prudy.
 */
public class EditPolicyCommand extends Command {
    public static final String COMMAND_WORD = "edit-policy";
    public static final String MESSAGE_NOT_EDITED = "At least one field needs to be updated.";
    public static final String MESSAGE_POLICY_NOT_FOUND = "Policy of specified type does not exist for client.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the specified policy for the client identified "
            + "by the index number used in the last client listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_POLICY_TYPE + "POLICY_TYPE "
            + "[" + PREFIX_POLICY_PREMIUM_AMOUNT + "PREMIUM_AMOUNT] "
            + "[" + PREFIX_POLICY_COVERAGE_AMOUNT + "COVERAGE_AMOUNT] "
            + "[" + PREFIX_POLICY_EXPIRY_DATE + "EXPIRY_DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_POLICY_TYPE + "health "
            + PREFIX_POLICY_PREMIUM_AMOUNT + "1500 "
            + PREFIX_POLICY_COVERAGE_AMOUNT + "10000.50 "
            + PREFIX_POLICY_EXPIRY_DATE + "09/14/2024 "
            + " (The last 3 fields are optional but one of them needs to be edited at all times)\n ";

    public static final String MESSAGE_SUCCESS = "%1$s policy for %2$s has been changed to:\n\n%3$s";
    private final Index index;
    private final EditPolicyDescriptor editPolicyDescriptor;

    /**
     * Creates an UpdatePolicyCommand to update the specified {@code PolicyMap} of a client.
     *
     * @param index   Index of the client in the filtered client list to edit the policy.
     * @param editPolicyDescriptor The new optional fields in policy to be set.
     */
    public EditPolicyCommand(Index index, EditPolicyDescriptor editPolicyDescriptor) {
        requireAllNonNull(index, editPolicyDescriptor);

        this.index = index;
        this.editPolicyDescriptor = editPolicyDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownList = model.getFilteredClientList();

        if (index.getZeroBased() >= model.getFilteredClientList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToEdit = lastShownList.get(index.getZeroBased());
        PolicySet clientPolicies = new PolicySet(clientToEdit.getPolicies());

        PolicyType policyTypeToEdit = editPolicyDescriptor.getPolicyType();
        Policy policyToRemove = findPolicyByType(clientPolicies, policyTypeToEdit);

        if (policyToRemove == null) {
            throw new CommandException(MESSAGE_POLICY_NOT_FOUND);
        }

        clientPolicies.remove(policyToRemove.getType());
        Policy editedPolicy = createEditedPolicy(policyToRemove, editPolicyDescriptor);
        clientPolicies.add(editedPolicy);

        Client editedClient = new Client(clientToEdit.getName(), clientToEdit.getPhone(), clientToEdit.getEmail(),
                clientToEdit.getAddress(), clientToEdit.getTags(), clientPolicies);

        model.setClient(clientToEdit, editedClient);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                policyTypeToEdit, clientToEdit.getName(), editedPolicy));

    }

    /**
     * Finds a policy by the given type in the provided set of policies.
     */
    private Policy findPolicyByType(PolicySet policySet, PolicyType policyType) {
        for (Policy policy : policySet) {
            if (policy.getType().equals(policyType)) {
                return policy;
            }
        }
        return null;
    }

    /**
     * Creates a new Policy by copying the old policy and applying the edited fields.
     */
    private Policy createEditedPolicy(Policy policyToEdit, EditPolicyDescriptor descriptor) {
        PremiumAmount updatedPremiumAmount = descriptor.getPremiumAmount().orElse(policyToEdit.getPremiumAmount());
        CoverageAmount updatedCoverageAmount = descriptor.getCoverageAmount().orElse(policyToEdit.getCoverageAmount());
        ExpiryDate updatedExpiryDate = descriptor.getExpiryDate().orElse(policyToEdit.getExpiryDate());

        // claims cannot be edited using this command
        ClaimList claimList = new ClaimList();
        claimList.addAll(policyToEdit.getClaimList());

        return Policy.makePolicy(descriptor.getPolicyType(), updatedPremiumAmount,
                updatedCoverageAmount, updatedExpiryDate, claimList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditPolicyCommand)) {
            return false;
        }

        EditPolicyCommand otherCommand = (EditPolicyCommand) other;
        return index.equals(otherCommand.index)
                && editPolicyDescriptor.equals(otherCommand.editPolicyDescriptor);
    }
}
