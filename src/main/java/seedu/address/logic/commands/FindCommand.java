package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all students in the address book who match the specified search criteria.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students matching the specified "
            + "criteria and displays them as a list with index numbers.\n"
            + "Parameters: [/n NAME] [/id STUDENT_ID]\n"
            + "At least one parameter must be provided.\n"
            + "Example: " + COMMAND_WORD + " /n John Doe /id A1234567E";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        int matchedStudents = model.getFilteredPersonList().size();

        if (matchedStudents == 0) {
            return new CommandResult(Messages.MESSAGE_NO_STUDENTS_FOUND);
        }
        return new CommandResult(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, matchedStudents));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public int hashCode() {
        return predicate.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
