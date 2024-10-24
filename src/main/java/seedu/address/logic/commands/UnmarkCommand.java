package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME;
import static seedu.address.logic.Messages.MESSAGE_UNMARK_ALREADY_SUCCESS;
import static seedu.address.logic.Messages.MESSAGE_UNMARK_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Unmarks a specific student as present for that particular week.
 * Marks the student as absent.
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
     * Constuctor for UnmarkCommand.
     *
     * @param name the name of the student to be unmarked
     * @param week the week number
     */
    public UnmarkCommand(Name name, int week) {
        this.name = name;
        this.week = week;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Person> optionalPersonToUnmark = model.getPerson(name);
        if (!optionalPersonToUnmark.isPresent()) {
            throw new CommandException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_NAME, name));
        }

        Person personToUnmark = optionalPersonToUnmark.get();
        Set<Integer> updatedWeeksPresent = new HashSet<>(personToUnmark.getWeeksPresent());

        if (!updatedWeeksPresent.remove(week)) {
            return new CommandResult(String.format(MESSAGE_UNMARK_ALREADY_SUCCESS, name, week));
        }

        Person updatedPerson = new Person(
                personToUnmark.getName(),
                personToUnmark.getPhone(),
                personToUnmark.getAddress(),
                personToUnmark.getEmail(),
                personToUnmark.getTelegram(),
                personToUnmark.getGithub(),
                personToUnmark.getAssignment(),
                updatedWeeksPresent, // Updated weeks present
                personToUnmark.getTags());

        model.setPerson(personToUnmark, updatedPerson);

        return new CommandResult(String.format(MESSAGE_UNMARK_SUCCESS, name, week));
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
        return String.format("UnmarkCommand{name=%s, week=%d}", name, week);
    }
}
