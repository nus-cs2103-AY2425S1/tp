package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book
 * whose name contains any of the name argument keywords
 * and role matches the role argument keyword.
 * Keyword matching is case-insensitive.
 */
public class FindPersonCommand extends Command {

    public static final String COMMAND_WORD = "findp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified names (case-insensitive) and role matches the specified role (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_ROLE + "ROLE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "alice bob charlie "
            + PREFIX_ROLE + "organiser";

    private final Predicate<Person> predicate;

    /**
     * Creates a FindPersonCommand to find all persons who satisfy the {@code predicate}.
     */
    public FindPersonCommand(Predicate<Person> predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

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
        if (!(other instanceof FindPersonCommand)) {
            return false;
        }

        FindPersonCommand otherFindPersonCommand = (FindPersonCommand) other;
        return predicate.equals(otherFindPersonCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
