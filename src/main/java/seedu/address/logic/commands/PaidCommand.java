package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;

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
            + ": Marks the policy identified by the policy index as paid and updates the next payment date.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_POLICY
            + "POLICY_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_POLICY + "1";

    public static final String MESSAGE_SUCCESS = "Policy %1$s for %2$s is marked as paid. Next payment date updated.";
    public static final String MESSAGE_FULLY_PAID = "The policy %1$s for %2$s will be fully paid after this payment.";
    public static final String MESSAGE_INVALID_POLICY = "The policy associated with index %1$s does not exist for "
            + "%2$s.";
    public static final String MESSAGE_INVALID_PAYDATE = "The policy %1$s for %2$s is fully paid.";

    private final Index targetIndex;
    private final Index policyIndex;

    /**
     * Creates a PaidCommand to mark the specified policy as paid.
     */
    public PaidCommand(Index targetIndex, Index policyIndex) {
        requireNonNull(targetIndex);
        requireNonNull(policyIndex);
        this.targetIndex = targetIndex;
        this.policyIndex = policyIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUpdate = lastShownList.get(targetIndex.getZeroBased());

        if (policyIndex.getZeroBased() >= personToUpdate.getPolicies().size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_POLICY, policyIndex.getOneBased(),
                    personToUpdate.getName()));
        }

        Policy policyToUpdate = personToUpdate.getPolicies().get(policyIndex.getZeroBased());

        if (policyToUpdate.isFullyPaid()) {
            throw new CommandException(String.format(MESSAGE_INVALID_PAYDATE, policyToUpdate.getPolicyName(),
                    personToUpdate.getName()));
        }

        if (policyToUpdate.isExpiringSoon()) {
            policyToUpdate.updateNextPaymentDate();
            return new CommandResult(String.format(MESSAGE_FULLY_PAID, policyToUpdate.getPolicyName(),
                    personToUpdate.getName()));
        }

        policyToUpdate.updateNextPaymentDate();

        return new CommandResult(String.format(MESSAGE_SUCCESS, policyToUpdate.getPolicyName(),
                personToUpdate.getName()));
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
                && policyIndex.equals(otherPaidCommand.policyIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("policyIndex", policyIndex)
                .toString();
    }
}
