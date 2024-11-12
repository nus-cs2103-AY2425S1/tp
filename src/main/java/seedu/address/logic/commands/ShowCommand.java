package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Shows all students in the address book who are in the same group.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all students in the same group "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORDS\n"
            + "Example: " + "show group 1";


    private final Predicate<Person> predicate;

    public ShowCommand(Predicate<Person> predicate) {
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
        if (!(other instanceof ShowCommand)) {
            return false;
        }

        ShowCommand otherShowCommand = (ShowCommand) other;
        return predicate.equals(otherShowCommand.predicate);
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

