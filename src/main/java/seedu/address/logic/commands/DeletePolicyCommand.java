package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicySet;
import seedu.address.model.policy.PolicyType;

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
    public static final String POLICY_DELETE_PERSON_SUCCESS = "Policies Left: %1$s";
    private final Index index;
    private final Set<PolicyType> policyTypes;

    /**
     * Creates a DeletePolicyCommand to delete the specified {@code PolicyMap} from the client.
     *
     * @param index of the client in the filtered client list to delete policy.
     * @param policyTypes the set of policies to be deleted.
     */
    public DeletePolicyCommand(Index index, Set<PolicyType> policyTypes) {
        requireAllNonNull(index, policyTypes);

        this.index = index;
        this.policyTypes = policyTypes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        PolicySet editedPolicy = removePolicies(personToEdit.getPolicies());

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), editedPolicy);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(POLICY_DELETE_PERSON_SUCCESS, Messages.formatPolicies(editedPolicy)));
    }

    private PolicySet removePolicies(Set<Policy> policies) throws CommandException {
        PolicySet updatedPolicies = new PolicySet();
        updatedPolicies.addAll(policies);
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
        if (!(other instanceof DeletePolicyCommand)) {
            return false;
        }

        DeletePolicyCommand dpc = (DeletePolicyCommand) other;
        return index.equals(dpc.index)
                && policyTypes.equals(dpc.policyTypes);
    }
}
