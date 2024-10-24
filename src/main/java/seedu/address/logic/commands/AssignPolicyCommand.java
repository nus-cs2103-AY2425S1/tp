package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_PAYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_START_DATE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Policy;


/**
 * Command that assigns a policy to a person.
 */
public class AssignPolicyCommand extends Command {
    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a policy to a person"
            + " by the index number used in the displayed person list. "
            + "Parameters: "
            + PREFIX_POLICY_NAME + "PolicyName "
            + PREFIX_POLICY_START_DATE + "PolicyStartDate "
            + PREFIX_POLICY_END_DATE + "PolicyEndDate "
            + PREFIX_NEXT_PAYMENT_DATE + "PolicyNextPaymentDate "
            + PREFIX_PAYMENT_AMOUNT + "AmountDue "
            + "\nExample: " + COMMAND_WORD + " 1 "
            + PREFIX_POLICY_NAME + "PolicyOne "
            + PREFIX_POLICY_START_DATE + "2022-12-12 "
            + PREFIX_POLICY_END_DATE + "2023-12-12 "
            + PREFIX_NEXT_PAYMENT_DATE + "2023-11-01 "
            + PREFIX_PAYMENT_AMOUNT + "300.00 ";

    public static final String MESSAGE_SUCCESS = "Policy successfully assigned to %1$s";
    public static final String MESSAGE_DUPLICATE_POLICY = "This policy already exists";

    private final Index index;
    private final Policy policy;
    private Person personBeforeEdit;

    /**
     * Creates an AssignPolicyCommand to assign the specified {@code policy} to a person
     * at the specified {@code index}.
     *
     * @param index The index of the person to assign the policy to.
     * @param policy The policy to be assigned to the person.
     * @throws NullPointerException if {@code index} or {@code policy} is null.
     */
    public AssignPolicyCommand(Index index, Policy policy) {
        requireNonNull(index);
        requireNonNull(policy);

        this.index = index;
        this.policy = policy;
    }

    /**
     * Executes the command to assign the policy to the person at the specified index.
     *
     * @param model The model containing the person list to be modified.
     * @return The result of the command execution containing the success message.
     * @throws CommandException if the index is invalid or if the policy already exists for the person.
     * @throws NullPointerException if {@code model} is null.
     */
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(index.getZeroBased());
        boolean editSuccess = personToEdit.assignPolicy(policy);
        if (!editSuccess) {
            throw new CommandException(MESSAGE_DUPLICATE_POLICY);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personToEdit)));
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if the same object
        if (other == this) {
            return true;
        }
        // Instance of handles nulls and type check
        if (!(other instanceof AssignPolicyCommand)) {
            return false;
        }
        // Cast and compare the policyName attribute
        AssignPolicyCommand otherCommand = (AssignPolicyCommand) other;
        return this.policy.equals(otherCommand.policy);
    }

}

