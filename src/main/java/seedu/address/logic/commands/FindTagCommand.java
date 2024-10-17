package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose tags match the specified tags.
 * Tag matching is case-insensitive.
 */
// Solution structure inspired by ChatGPT
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags match any of the "
            + "specified tag names (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " friend family colleague";
    public static final String HELP_FINDTAG_COMMAND = "Findtag Command\n"
            + "- Format: findtag TAG [MORE_TAGS]\n"
            + "- Example: findtag HDB Condo\n"
            + "- Tags are case insensitive.\n"
            + "- Items in the [square brackets] are optional. Persons with at least one matching tag will be returned.";

    //@@author tayxuenye-reused
    //Suggested by ChatGPT as a way of checking whether tag exists in the Person
    private final Predicate<Person> predicate;

    public FindTagCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }
    //@@author

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindTagCommand)) {
            return false;
        }

        FindTagCommand otherFindTagCommand = (FindTagCommand) other;
        return predicate.equals(otherFindTagCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
