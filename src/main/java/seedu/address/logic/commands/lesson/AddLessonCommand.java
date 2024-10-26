package seedu.address.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.consultation.Date;
import seedu.address.model.consultation.Time;

/**
 * Adds a lesson to the address book.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addlesson";
    public static final CommandType COMMAND_TYPE = CommandType.ADDLESSON;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to TAHub. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2024-10-20 "
            + PREFIX_TIME + "14:00 ";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";

    private final Lesson newLesson;

    /**
     * Creates an AddLessonCommand to add the specified {@code Lesson}
     */
    public AddLessonCommand(Lesson newLesson) {
        requireNonNull(newLesson);
        this.newLesson = newLesson;
    }

    /**
     * Returns Command Type ADDLESSON
     *
     * @return Command Type ADDLESSON
     */
    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.addLesson(newLesson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(newLesson)),
                COMMAND_TYPE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddLessonCommand
                && newLesson.equals(((AddLessonCommand) other).newLesson));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("newLesson", newLesson)
                .toString();
    }
}
