package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.policy.PolicyMap;

/**
 * Add Policy to an existing client in Prudy.
 */
public class AddPolicyCommand extends Command {
    public static final String COMMAND_WORD = "add-policy";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Policy added:\n%2$s";
    public static final String MESSAGE_DUPLICATES = "Duplicate policies found.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add the specified policy to the person identified "
            + "by the index number used in the last person listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "pt/[POLICY_TYPE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "pt/life";

    private final Index index;
    private final PolicyMap policies;

    /**
     * Creates an AddPolicyCommand to add the specified {@code PolicyMap} to the client.
     *
     * @param index of the client in the filtered client list to add policy.
     * @param policies the set of policies to be added.
     */
    public AddPolicyCommand(Index index, PolicyMap policies) {
        requireAllNonNull(index, policies);

        this.index = index;
        this.policies = policies;
    }

    @Override
    public CommandResult execute(Model mode) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), policies.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPolicyCommand)) {
            return false;
        }

        AddPolicyCommand apc = (AddPolicyCommand) other;
        return index.equals(apc.index)
                && policies.equals(apc.policies);
    }
}
