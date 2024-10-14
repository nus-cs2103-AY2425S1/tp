package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.policy.PolicyMap;

/**
 * Deletes Policy from an existing client in Prudy.
 */
public class DeletePolicyCommand extends Command {
    public static final String COMMAND_WORD = "delete-policy";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Policy deleted:\n%2$s";
    public static final String MESSAGE_POLICY_NOT_FOUND = "Policy not found.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified policy from the person identified "
            + "by the index number used in the last person listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "pt/[POLICY_TYPE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "pt/life";

    private final Index index;
    private final PolicyMap policies;

    /**
     * Creates a DeletePolicyCommand to delete the specified {@code PolicyMap} from the client.
     *
     * @param index of the client in the filtered client list to delete policy.
     * @param policies the set of policies to be deleted.
     */
    public DeletePolicyCommand(Index index, PolicyMap policies) {
        requireAllNonNull(index, policies);

        this.index = index;
        this.policies = policies;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Logic to find client and delete policy
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), policies.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePolicyCommand)) {
            return false;
        }

        DeletePolicyCommand dpc = (DeletePolicyCommand) other;
        return index.equals(dpc.index)
                && policies.equals(dpc.policies);
    }
}
