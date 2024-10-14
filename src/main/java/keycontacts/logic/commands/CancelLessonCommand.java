package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DATE;
import static keycontacts.logic.parser.CliSyntax.PREFIX_START_TIME;

import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.Time;

/**
 * Cancels a currently schedule lesson.
 */
public class CancelLessonCommand extends Command {

    public static final String COMMAND_WORD = "cancel";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Cancels the lesson identified by the date and start time.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_START_TIME + "START_TIME\n"
            + "Example: " + COMMAND_WORD + PREFIX_DATE + "06-07-2022 " + PREFIX_START_TIME + "12:00";

    private final Time startTime;
    private final Date date;

    /**
     * @param date of the lesson to be cancelled
     * @param startTime of the lesson to be cancelled
     */
    public CancelLessonCommand(Date date, Time startTime) {
        requireNonNull(date);
        requireNonNull(startTime);

        this.date = date;
        this.startTime = startTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(""); //placeholder
    }

}
