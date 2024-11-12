package seedu.edulog.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_START_DAY;
import static seedu.edulog.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;
import seedu.edulog.model.calendar.Lesson;

/**
 * Adds a student to the edulog book.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the calendar. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_START_DAY + "DAY OF THE WEEK "
            + PREFIX_START_TIME + "START TIME (24H FORMAT) "
            + PREFIX_END_TIME + "END TIME (24H FORMAT) "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Sec 4 Math Class "
            + PREFIX_START_DAY + "Monday "
            + PREFIX_START_TIME + "2230 "
            + PREFIX_END_TIME + "0030 ";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the calendar";

    private final Lesson toAdd;

    /**
     * Creates an AddLessonCommand to add the specified {@code Lesson}
     */
    public AddLessonCommand(Lesson lesson) {
        requireNonNull(lesson);
        toAdd = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasLesson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        model.addLesson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLessonCommand)) {
            return false;
        }

        AddLessonCommand otherAddLessonCommand = (AddLessonCommand) other;
        return toAdd.equals(otherAddLessonCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
