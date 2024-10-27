package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

/**
 * Lists all policies of a specified person in the address book.
 */
public class ListPoliciesCommand extends Command {

    public static final String COMMAND_WORD = "list-policies";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all policies of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LIST_POLICIES_SUCCESS = "Policies listed for person: %1$s\n%2$s";
    public static final String MESSAGE_NO_POLICIES = "No policies found for person: %1$s";
    public static final String MESSAGE_INVALID_PERSON_INDEX = "The person index provided is invalid.";

    private final Index personIndex;

    public ListPoliciesCommand(Index personIndex) {
        this.personIndex = personIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_INDEX);
        }

        Person person = lastShownList.get(personIndex.getZeroBased());
        List<Policy> policyList = person.getPolicies().stream().collect(Collectors.toList());

        if (policyList.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_POLICIES, person.getName()));
        }

        String policies = policyList.stream()
                .map(Policy::toString)
                .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(MESSAGE_LIST_POLICIES_SUCCESS, person.getName(), policies));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListPoliciesCommand // instanceof handles nulls
                && personIndex == ((ListPoliciesCommand) other).personIndex);
    }
}
