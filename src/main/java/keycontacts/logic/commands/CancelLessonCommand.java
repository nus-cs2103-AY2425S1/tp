package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DATE;
import static keycontacts.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.List;

import keycontacts.commons.core.index.Index;
import keycontacts.commons.util.ToStringBuilder;
import keycontacts.logic.Messages;
import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.model.Model;
import keycontacts.model.lesson.CancelledLesson;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.lesson.Time;
import keycontacts.model.student.Student;

/**
 * Cancels a currently scheduled lesson.
 */
public class CancelLessonCommand extends Command {

    public static final String COMMAND_WORD = "cancel";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Cancels the lesson identified by the date and start time.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_START_TIME + "START_TIME\n"
            + "Example: " + COMMAND_WORD + PREFIX_DATE + "06-07-2022 " + PREFIX_START_TIME + "12:00";
    public static final String MESSAGE_SUCCESS = "Cancelled lesson on %1s$ for student: %2s$";
    public static final String MESSAGE_LESSON_NOT_FOUND = "No lesson with the "
            + "specified parameters found for student %1s$. "
            + "Check if you inputted the right timing and student!";
    private final Date date;
    private final Time startTime;
    private final Index index;

    /**
     * @param index Index of the student attached to the lesson to be cancelled
     * @param date Date of the lesson to be cancelled
     * @param startTime Start time of the lesson to be cancelled
     */
    public CancelLessonCommand(Index index, Date date, Time startTime) {
        requireNonNull(index);
        requireNonNull(date);
        requireNonNull(startTime);

        this.index = index;
        this.date = date;
        this.startTime = startTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        RegularLesson lessonToCancel = null;
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToUpdate = lastShownList.get(index.getZeroBased());
        boolean matchesLesson = studentToUpdate.matchesLesson(date, startTime);

        if (matchesLesson) {
            lessonToCancel = studentToUpdate.getRegularLesson().get();
        } else {
            throw new CommandException(String.format(MESSAGE_LESSON_NOT_FOUND, Messages.format(studentToUpdate)));
        }

        CancelledLesson cancelledLesson = new CancelledLesson(date);
        Student updatedStudent = studentToUpdate.withAddedCancelledLesson(cancelledLesson);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(lessonToCancel), Messages.format(updatedStudent)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CancelLessonCommand)) {
            return false;
        }

        CancelLessonCommand otherCancelLessonCommand = (CancelLessonCommand) other;
        return date.equals(otherCancelLessonCommand.date) && startTime.equals(otherCancelLessonCommand.startTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("date", date)
                .add("startTime", startTime)
                .add("index", index)
                .toString();
    }

}
