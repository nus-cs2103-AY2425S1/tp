package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_TYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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

    public static final String MESSAGE_USAGE = FindCommand.COMMAND_WORD + " " + PREFIX_CLIENT_TYPE
            + " or " + COMMAND_WORD
            + ": Finds all clients whose names contain any of "
            + "the specified CLIENT_TYPE and displays them as a list with index numbers.\n"
            + "Parameters: CLIENT_TYPE [MORE_CLIENT_TYPES]...\n"
            + "Examples: \n"
            + COMMAND_WORD + " Investment Plan\n"
            + COMMAND_WORD + " Investment Plan Healthcare\n"
            + FindCommand.COMMAND_WORD + " " + PREFIX_CLIENT_TYPE + "Investment Plan\n"
            + FindCommand.COMMAND_WORD + " " + PREFIX_CLIENT_TYPE + "Investment Plan Healthcare\n"
            + "Additional Info: \n"
            + "- CLIENT_TYPE is case-insensitive.\n"
            + "- CLIENT_TYPE should not be empty.\n"
            + "- Can specify multiple CLIENT_TYPE to have a more specific find.";


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
            // If noone found, show all persons (no change)
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(Messages.MESSAGE_CLIENT_TYPE_NOT_FOUND);
        }
        return new CommandResult(
                Messages.getMessagePersonsListedOverview(model.getDisplayPersons().size()));
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
