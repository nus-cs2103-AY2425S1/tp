package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Person;



/**
 * Schedules a meeting with another person from the address book.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedules a meeting with another "
            + "person from the address book. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_TIME + "END_TIME "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LOCATION + "The Terrace "
            + PREFIX_START_TIME + "09-10-2024 09:00 "
            + PREFIX_END_TIME + "09-10-2024 10:00 ";

    public static final String MESSAGE_SUCCESS = "New meeting added: %1$s";

    private final Meeting toAdd;
    private final Index index;

    /**
     * Creates a ScheduleCommand to add the specified {@code Meeting}
     * @param index of the person in the filtered person list to schedule a meeting with
     */
    public ScheduleCommand(Index index, Meeting meeting) {
        requireNonNull(index);
        requireNonNull(meeting);

        this.index = index;
        this.toAdd = meeting;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToScheduleMeetingWith = lastShownList.get(index.getZeroBased());

        model.addMeeting(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}

