package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AddressContainsKeywordsPredicate;

/**
 * Finds and lists all persons in ClientHub whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindAddressCommand extends Command {

    public static final String COMMAND_WORD = "fa";

    public static final String MESSAGE_USAGE = FindCommand.COMMAND_WORD + " " + PREFIX_ADDRESS
            + " or " + COMMAND_WORD
            + ": Finds all clients whose address contain any of "
            + "the specified ADDRESS and displays them as a list with index numbers.\n"
            + "Parameters: ADDRESS\n"
            + "Example:\n"
            + "- " + COMMAND_WORD + " tampines\n"
            + "- " + FindCommand.COMMAND_WORD + " " + PREFIX_ADDRESS + "tampines\n"
            + "Additional Info: \n"
            + "- ADDRESS is case-insensitive.\n"
            + "- ADDRESS should not be empty.";

    private final AddressContainsKeywordsPredicate predicate;

    public FindAddressCommand(AddressContainsKeywordsPredicate predicate) {
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
            throw new CommandException(Messages.MESSAGE_ADDRESS_NOT_FOUND);
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
        if (!(other instanceof FindAddressCommand)) {
            return false;
        }

        FindAddressCommand otherFindAddressCommand = (FindAddressCommand) other;
        return predicate.equals(otherFindAddressCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
