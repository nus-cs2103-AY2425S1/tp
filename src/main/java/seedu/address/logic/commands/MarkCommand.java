package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME;
import static seedu.address.logic.Messages.MESSAGE_MARK_ALREADY_SUCCESS;
import static seedu.address.logic.Messages.MESSAGE_MARK_SUCCESS;
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
 * Marks a specific student as present for that particular week.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the name as present for that particular "
            + "week. The max week number is 13.\n "
            + "Parameters: " + PREFIX_NAME + "NAME " + PREFIX_WEEK + "WEEK NUMBER "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_WEEK + "1";

    public final int week;
    private final Name name;

    /**
     * Constuctor for MarkCommand.
     *
     * @param name the name of the student to be marked
     * @param week the week number in which the student is being marked as present
     */
    public MarkCommand(Name name, int week) {
        this.name = name;
        this.week = week;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Person> optionalPersonToMark = model.getPerson(name);
        if (!optionalPersonToMark.isPresent()) {
            throw new CommandException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_NAME, name));
        }

        Person personToMark = optionalPersonToMark.get();
        Set<Integer> updatedWeeksPresent = new HashSet<>(personToMark.getWeeksPresent());

        if (!updatedWeeksPresent.add(week)) {
            return new CommandResult(String.format(MESSAGE_MARK_ALREADY_SUCCESS, name, week));
        }

        Person updatedPerson = new Person(
                personToMark.getName(),
                personToMark.getPhone(),
                personToMark.getAddress(),
                personToMark.getEmail(),
                personToMark.getTelegram(),
                personToMark.getGithub(),
                personToMark.getAssignment(),
                updatedWeeksPresent, // Updated weeks present
                personToMark.getTags());

        model.setPerson(personToMark, updatedPerson);

        return new CommandResult(String.format(MESSAGE_MARK_SUCCESS, name, week));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand otherMarkCommand = (MarkCommand) other;
        return name.equals(otherMarkCommand.name) && week == otherMarkCommand.week;
    }

    @Override
    public String toString() {
        return String.format("MarkCommand{name=%s, week=%d}", name, week);
    }
}
