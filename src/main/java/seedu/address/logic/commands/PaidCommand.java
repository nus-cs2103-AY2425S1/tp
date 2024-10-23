package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Policy;

/**
 * Marks a policy as paid and updates the next payment date.
 */
public class PaidCommand extends Command {
    public static final String COMMAND_WORD = "paid";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the policy identified by the policy name as paid and updates the next payment date.\n"
            + "Parameters: INDEX (must be a positive integer) POLICY_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 Health Insurance";

    public static final String MESSAGE_SUCCESS = "Policy %1$s for %2$s is marked as paid. Next payment date updated.";
    public static final String MESSAGE_INVALID_POLICY = "The policy %1$s does not exist for %2$s.";

    public static final String MESSAGE_INVALID_PAYDATE = "The coverage of policy %1$s for %2$s ends after this year.";

    private final Index targetIndex;
    private final String policyName;

    /**
     * Creates a PaidCommand to mark the specified policy as paid.
     */
    public PaidCommand(Index targetIndex, String policyName) {
        requireNonNull(targetIndex);
        requireNonNull(policyName);
        this.targetIndex = targetIndex;
        this.policyName = policyName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUpdate = lastShownList.get(targetIndex.getZeroBased());
        Policy policyToUpdate = personToUpdate.getPolicyByName(policyName);

        if (policyToUpdate == null) {
            throw new CommandException(String.format(MESSAGE_INVALID_POLICY, policyName, personToUpdate.getName()));
        }

        if (policyToUpdate.isExpiringSoon()) {
            throw new CommandException(String.format(MESSAGE_INVALID_PAYDATE, policyName, personToUpdate.getName()));
        }

        policyToUpdate.updateNextPaymentDate();

        return new CommandResult(String.format(MESSAGE_SUCCESS, policyName, personToUpdate.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PaidCommand)) {
            return false;
        }

        PaidCommand otherPaidCommand = (PaidCommand) other;
        return targetIndex.equals(otherPaidCommand.targetIndex)
                && policyName.equals(otherPaidCommand.policyName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("policyName", policyName)
                .toString();
    }
}
