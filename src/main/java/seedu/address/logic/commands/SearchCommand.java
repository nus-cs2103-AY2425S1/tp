package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Searches all persons whose names or tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "At least one of the following parameters must be provided.\n"
            + "Parameters: " + "PREFIX/ KEYWORD [ANOTHER_PREFIX/ KEYWORD] \n"
            + "You can search by name, tag, or both."
            + " If both are provided, only persons matching both criteria will be shown.\n"
            + "Example 1: " + COMMAND_WORD + " n/alice\n"
            + "Example 2: " + COMMAND_WORD + " t/friend\n"
            + "Example 3: " + COMMAND_WORD + " n/alice t/friend\n"
            + "Example 4: " + COMMAND_WORD + " t/friend n/alice";

    private final Predicate<Person> predicate;

    public SearchCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchCommand)) {
            return false;
        }

        SearchCommand otherSearchCommand = (SearchCommand) other;
        return predicate.equals(otherSearchCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
