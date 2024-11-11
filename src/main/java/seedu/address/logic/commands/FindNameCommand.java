package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindNameCommand extends Command {

    public static final String COMMAND_WORD = "fn";

    public static final String MESSAGE_USAGE = FindCommand.COMMAND_WORD + " " + PREFIX_NAME
            + " or " + COMMAND_WORD
            + ": Finds all clients whose names contain all of "
            + "the prefix of the specified NAME and displays them as a list with index numbers.\n"
            + "Parameters: NAME (String & must be non-empty)\n"
            + "Example:\n"
            + "- " + COMMAND_WORD + " Alice\n"
            + "- " + FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "Alice\n"
            + "Additional Info: \n"
            + "- NAME is case-insensitive.\n"
            + "- It should contain letters, spaces, parenthesis or slashes only.\n"
            + "- They cannot be empty or have only spaces.";

    private final NameContainsKeywordsPredicate predicate;

    public FindNameCommand(NameContainsKeywordsPredicate predicate) {
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
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
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
        if (!(other instanceof FindNameCommand)) {
            return false;
        }

        FindNameCommand otherFindNameCommand = (FindNameCommand) other;
        return predicate.equals(otherFindNameCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
