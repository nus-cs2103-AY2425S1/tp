package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PhoneBeginsWithKeywordPredicate;

/**
 * Finds and lists all persons in address book whose phone number begins with the argument keywords.
 * Keyword matching has to be all numbers.
 */
public class FindPhoneCommand extends Command {

    public static final String COMMAND_WORD = "fp";

    public static final String MESSAGE_USAGE = FindCommand.COMMAND_WORD + " " + PREFIX_PHONE
            + " or " + COMMAND_WORD
            + ": Finds all clients whose phone number begins with "
            + "the specified PHONE_NUMBER and displays them as a list with index numbers.\n"
            + "Parameters: PHONE_NUMBER (Contains only 8 digits)\n"
            + "Example:\n"
            + "- " + COMMAND_WORD + " 91234567\n"
            + "- " + FindCommand.COMMAND_WORD + " " + PREFIX_PHONE + "91234567\n"
            + "Additional Info: \n"
            + "- PHONE_NUMBER is a sequence of integers with no spacing.";


    private final PhoneBeginsWithKeywordPredicate predicate;

    public FindPhoneCommand(PhoneBeginsWithKeywordPredicate predicate) {
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
            throw new CommandException(Messages.MESSAGE_PHONE_NOT_FOUND);
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
        if (!(other instanceof FindPhoneCommand)) {
            return false;
        }

        FindPhoneCommand otherFindCommand = (FindPhoneCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
