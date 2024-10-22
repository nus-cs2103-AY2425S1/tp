package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME;
import static seedu.address.logic.Messages.MESSAGE_UNMARK_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Marks a specific student as present for that particular week.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks the name as present for that particular "
            + "week.\n "
            + "Parameters: " + PREFIX_NAME + "NAME" + PREFIX_WEEK + "WEEK NUMBER"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_WEEK + "1";

    public final int week;
    private final Name name;

    /**
     * Constuctor for MarkCommand
     * @param name the name of the student
     * @param week the week number
     */
    public UnmarkCommand(Name name, int week) {
        this.name = name;
        this.week = week;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Person> personToUnmark = model.getPerson(name);
        if (!personToUnmark.isPresent()) {
            throw new CommandException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_NAME, name));
        }

        Person person = personToUnmark.get();
        person.unmarkWeekPresent(week);

        return new CommandResult(String.format(MESSAGE_UNMARK_SUCCESS, person.getName(), week));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmarkCommand)) {
            return false;
        }

        UnmarkCommand otherUnmarkCommand = (UnmarkCommand) other;
        return name.equals(otherUnmarkCommand.name) && week == otherUnmarkCommand.week;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("week", week)
                .toString();
    }
}
