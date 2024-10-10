package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;
import keycontacts.model.lesson.Time;

/**
 * Cancels a currently schedule lesson.
 */
public class CancelLessonCommand extends Command {

    public static final String COMMAND_WORD = "cancel";
    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_INVALID_LESSON = "Invalid lesson: %1$s";

    private final Time startTime;
    private final LocalDate date;

    /**
     * @param date of the lesson to be cancelled
     * @param startTime of the lesson to be cancelled
     */
    public CancelLessonCommand(LocalDate date, Time startTime) {
        requireNonNull(startTime);
        requireNonNull(date);

        this.startTime = startTime;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(""); //placeholder
    }

}
