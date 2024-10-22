package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_COVERAGE_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_PREMIUM_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;

/**
 * Adds Policy to an existing client in Prudy.
 */
public class AddPolicyCommand extends Command {
    public static final String COMMAND_WORD = "add-policy";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Policy added:\n%2$s";
    public static final String MESSAGE_DUPLICATES = "Duplicate policies found.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add the specified policy to the person identified "
            + "by the index number used in the last person listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_POLICY_TYPE + "POLICY_TYPE "
            + "[" + PREFIX_POLICY_PREMIUM_AMOUNT + "PREMIUM_AMOUNT] "
            + "[" + PREFIX_POLICY_COVERAGE_AMOUNT + "COVERAGE_AMOUNT] "
            + "[" + PREFIX_POLICY_EXPIRY_DATE + "EXPIRY_DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_POLICY_TYPE + "life "
            + PREFIX_POLICY_PREMIUM_AMOUNT + "400.00 "
            + PREFIX_POLICY_COVERAGE_AMOUNT + "4000.00 "
            + PREFIX_POLICY_EXPIRY_DATE + "12/23/2024";
    public static final String MESSAGE_SUCCESS = "Added Policy:\n\n%1$s";

    private final Index index;
    private final Policy policy;

    /**
     * Creates an AddPolicyCommand to add the specified {@code PolicySet} to the client.
     *
     * @param index of the client in the filtered client list to add policy.
     * @param policies the set of policies to be added.
     */
    public AddPolicyCommand(Index index, Policy policy) {
        requireAllNonNull(index, policy);
        this.index = index;
        this.policy = policy;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= model.getFilteredPersonList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        PolicySet personPolicies = new PolicySet();
        personPolicies.addAll(personToEdit.getPolicies());

        if (!personPolicies.add(policy)) {
            throw new CommandException(MESSAGE_DUPLICATES);
        }

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personPolicies);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, policy.toString()));
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
                && policy.equals(apc.policy);
    }
}
