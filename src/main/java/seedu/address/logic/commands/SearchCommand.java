package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEGIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;

import java.time.LocalDateTime;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;
/**
 * Searches for schedules within a specified time period.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Search for schedules within the provided datetime period\n"
            + "Either begin datetime or end datetime or both has to be provided.\n"
            + "Begin datetime and end datetime must be in format yyyy-MM-dd HH:mm.\n"
            + "If begin datetime is not provided, it will search for all schedule before end datetime.\n"
            + "If end datetime is not provided, it will search for all schedule after begin datetime.\n"
            + "If no matching result, an empty list will be provided.\n"
            + "Parameters: "
            + PREFIX_BEGIN + "start datetime "
            + PREFIX_END + "end datetime\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_BEGIN + "2024-10-10 00:00 " + PREFIX_END + "2024-10-12 00:00";

    public static final String MESSAGE_SUCCESS = "Search successful";
    private LocalDateTime begin;
    private LocalDateTime end;
    /**
     * Constructs a SearchCommand with the specified begin and end times.
     *
     * @param begin The beginning of the time range to search.
     * @param end The end of the time range to search.
     */
    public SearchCommand(LocalDateTime begin, LocalDateTime end) {
        this.begin = begin;
        this.end = end;
    }
    /**
     * Executes the search command on the given model.
     * Filters the person list based on the presence and values of begin and end times.
     *
     * @param model The model in which the command is executed.
     * @return The result of the command execution.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (begin == null) {
            model.updateFilteredPersonList(this::hasScheduleBeforeEnd);
        } else if (end == null) {
            model.updateFilteredPersonList(this::hasScheduleAfterBegin);
        } else {
            model.updateFilteredPersonList(this::hasScheduleBetweenBeginAndEnd);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
    private boolean hasScheduleBeforeEnd(Person person) {
        Schedule schedule = person.getSchedule();
        return schedule.getDateTime() != null && !schedule.getDateTime().isAfter(end);
    }

    private boolean hasScheduleAfterBegin(Person person) {
        Schedule schedule = person.getSchedule();
        return schedule.getDateTime() != null && !schedule.getDateTime().isBefore(begin);
    }

    private boolean hasScheduleBetweenBeginAndEnd(Person person) {
        Schedule schedule = person.getSchedule();
        return schedule.getDateTime() != null
                && !schedule.getDateTime().isBefore(begin) && !schedule.getDateTime().isAfter(end);
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
        boolean isBeginEqual = (begin == null && otherSearchCommand.begin == null)
                || (begin != null && begin.equals(otherSearchCommand.begin));
        boolean isEndEqual = (end == null && otherSearchCommand.end == null)
                || (end != null && end.equals(otherSearchCommand.end));

        return isBeginEqual && isEndEqual;
    }
}
