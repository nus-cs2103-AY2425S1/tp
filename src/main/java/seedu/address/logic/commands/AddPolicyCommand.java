package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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
            + "pt/[POLICY_TYPE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "pt/life";
    public static final String POLICY_ADD_PERSON_SUCCESS = "Added Policy: %1$s";

    private final Index index;
    private final PolicySet policies;

    /**
     * Creates an AddPolicyCommand to add the specified {@code PolicySet} to the client.
     *
     * @param index of the client in the filtered client list to add policy.
     * @param policies the set of policies to be added.
     */
    public AddPolicyCommand(Index index, PolicySet policies) {
        requireNonNull(index);
        requireNonNull(policies);

        this.index = index;
        this.policies = policies;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();


        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());


        PolicySet editedPolicySet = addPoliciesToPerson(policies, personToEdit.getPolicySet());


        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), editedPolicySet);


        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);


        return new CommandResult(String.format(POLICY_ADD_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Adds policies to the person's policy set. Throws a CommandException if any of the policies already exist.
     *
     * @param policiesToAdd The set of policies to add.
     * @param existingPolicies The person's current policy set.
     * @return A new PolicySet with the added policies.
     * @throws CommandException if there are duplicate policies.
     */
    private PolicySet addPoliciesToPerson(PolicySet policiesToAdd, PolicySet existingPolicies) throws CommandException {
        for (Policy policy : policiesToAdd) {
            if (existingPolicies.contains(policy.getType())) { // Checking for duplicates
                throw new CommandException(MESSAGE_DUPLICATES);
            }
            existingPolicies.add(policy);
        }

        return existingPolicies;
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
