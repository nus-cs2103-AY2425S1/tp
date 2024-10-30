package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ClientTypeContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose client_type contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindClientTypeCommand extends Command {

    public static final String COMMAND_WORD = "fc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: CLIENT_TYPE [MORE_CLIENT_TYPES]...\n"
            + "Example: " + COMMAND_WORD + "Investment Plan 1";


    private final ClientTypeContainsKeywordsPredicate predicate;

    public FindClientTypeCommand(ClientTypeContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        // Check if there is anyone in the filtered list
        if (model.getDisplayPersons().isEmpty()) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_NO_PERSON_FOUND_FOR_VIEW)
            );
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getDisplayPersons().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindClientTypeCommand)) {
            return false;
        }

        FindClientTypeCommand otherFindCommand = (FindClientTypeCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
